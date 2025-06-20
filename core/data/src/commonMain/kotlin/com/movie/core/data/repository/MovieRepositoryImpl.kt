package com.movie.core.data.repository

import com.movie.core.common.error.MovieError
import com.movie.core.common.logging.Logger
import com.movie.core.common.network.INetworkConnectivity
import com.movie.core.common.network.NetworkConnectivity
import com.movie.core.data.mapper.toDomainModel
import com.movie.core.data.mapper.toEntity
import com.movie.core.database.dao.MovieDao
import com.movie.core.model.Movie
import com.movie.core.model.MovieDetail
import com.movie.core.network.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.emitAll
import kotlinx.datetime.Clock

class MovieRepositoryImpl(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val networkConnectivity: INetworkConnectivity
) : MovieRepository {
    
    companion object {
        private const val CACHE_TIMEOUT_MS = 5 * 60 * 1000L // 5 minutes
        private const val TAG = "MovieRepository"
    }
    
    override fun getTrendingMovies(forceRefresh: Boolean): Flow<List<Movie>> = flow {
        Logger.info(TAG, "getTrendingMovies called with forceRefresh=$forceRefresh")
        
        // First, emit from database
        val databaseFlow = movieDao.getTrendingMovies()
            .map { entities -> 
                Logger.debug(TAG, "Retrieved ${entities.size} trending movies from database")
                entities.map { it.toDomainModel() } 
            }
        
        // Check if we need to refresh
        val cacheTime = movieDao.getTrendingCacheTime()
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val shouldRefresh = forceRefresh || cacheTime == null || 
            (currentTime - cacheTime) >= CACHE_TIMEOUT_MS
        
        Logger.debug(TAG, "Cache check - shouldRefresh=$shouldRefresh, cacheTime=$cacheTime, currentTime=$currentTime")
        
        if (shouldRefresh) {
            try {
                Logger.info(TAG, "Fetching trending movies from API")
                // Fetch from network
                val response = apiService.getTrendingMovies()
                val movies = response.results.map { it.toDomainModel() }
                
                Logger.info(TAG, "Fetched ${movies.size} trending movies from API")
                
                // Cache the results
                val movieEntities = movies.map { it.toEntity(isTrending = true) }
                movieDao.replaceTrendingMovies(movieEntities)
                
                Logger.debug(TAG, "Cached ${movieEntities.size} trending movies to database")
            } catch (e: Exception) {
                Logger.error(TAG, "Failed to fetch trending movies from API", e)
                
                // Check network connectivity to provide better error message
                val isConnected = networkConnectivity.isConnected()
                Logger.debug(TAG, "Network connectivity check: isConnected=$isConnected")
                
                // If network fails, we'll still emit from database
                // Re-throw only if database is empty
                val cachedMovies = movieDao.getTrendingMoviesSync()
                if (cachedMovies.isEmpty()) {
                    Logger.error(TAG, "No cached movies available, re-throwing exception")
                    val movieError = if (!isConnected) {
                        MovieError.NetworkError("No internet connection and no cached data available")
                    } else {
                        MovieError.fromThrowable(e)
                    }
                    throw movieError
                } else {
                    Logger.info(TAG, "Using ${cachedMovies.size} cached trending movies after API failure")
                }
            }
        } else {
            Logger.info(TAG, "Using cached trending movies (cache is valid)")
        }
        
        // Emit all values from database (will include updates after network fetch)
        emitAll(databaseFlow)
    }
    
    override fun searchMovies(query: String): Flow<List<Movie>> = flow {
        Logger.info(TAG, "searchMovies called with query='$query'")
        
        try {
            val response = apiService.searchMovies(query)
            val movies = response.results.map { it.toDomainModel() }
            
            Logger.info(TAG, "Search returned ${movies.size} movies for query='$query'")
            emit(movies)
        } catch (e: Exception) {
            Logger.error(TAG, "Failed to search movies for query='$query'", e)
            
            // Check network connectivity for better error message
            val isConnected = networkConnectivity.isConnected()
            val movieError = if (!isConnected) {
                MovieError.NetworkError("No internet connection. Search requires an active internet connection.")
            } else {
                MovieError.fromThrowable(e)
            }
            throw movieError
        }
    }
    
    override fun getMovieDetails(movieId: Int, forceRefresh: Boolean): Flow<MovieDetail> = flow {
        Logger.info(TAG, "getMovieDetails called for movieId=$movieId, forceRefresh=$forceRefresh")
        
        // Check cache first if not forcing refresh
        if (!forceRefresh) {
            val cacheTime = movieDao.getMovieDetailCacheTime(movieId)
            val currentTime = Clock.System.now().toEpochMilliseconds()
            val isValidCache = cacheTime != null && 
                (currentTime - cacheTime) < CACHE_TIMEOUT_MS
            
            Logger.debug(TAG, "Cache check for movie $movieId - isValidCache=$isValidCache, cacheTime=$cacheTime")
            
            if (isValidCache) {
                val cachedDetail = movieDao.getMovieDetail(movieId)
                if (cachedDetail != null) {
                    Logger.info(TAG, "Using cached movie details for movie $movieId")
                    emit(cachedDetail.toDomainModel())
                    return@flow
                }
            }
        }
        
        // Fetch from network
        try {
            Logger.info(TAG, "Fetching movie details for movie $movieId from API")
            val movieDetail = apiService.getMovieDetails(movieId).toDomainModel()
            
            // Cache the result
            movieDao.insertMovieDetail(movieDetail.toEntity())
            
            Logger.info(TAG, "Fetched and cached movie details for movie $movieId: '${movieDetail.title}'")
            emit(movieDetail)
        } catch (e: Exception) {
            Logger.error(TAG, "Failed to fetch movie details for movie $movieId from API", e)
            
            // Check network connectivity for better error message
            val isConnected = networkConnectivity.isConnected()
            Logger.debug(TAG, "Network connectivity check: isConnected=$isConnected")
            
            // If network fails, try to emit from cache
            val cachedDetail = movieDao.getMovieDetail(movieId)
            if (cachedDetail != null) {
                Logger.info(TAG, "Using cached movie details for movie $movieId after API failure")
                emit(cachedDetail.toDomainModel())
            } else {
                Logger.error(TAG, "No cached movie details available for movie $movieId, re-throwing exception")
                val movieError = if (!isConnected) {
                    MovieError.NetworkError("No internet connection and no cached movie details available")
                } else {
                    MovieError.fromThrowable(e)
                }
                throw movieError
            }
        }
    }
}