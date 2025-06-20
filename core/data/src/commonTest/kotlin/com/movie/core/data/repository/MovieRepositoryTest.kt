package com.movie.core.data.repository

import com.movie.core.data.mapper.toDomainModel
import com.movie.core.data.mapper.toEntity
import com.movie.core.database.dao.MovieDao
import com.movie.core.model.Movie
import com.movie.core.model.MovieDetail
import com.movie.core.network.api.TmdbApiService
import com.movie.core.network.dto.MovieDto
import com.movie.core.network.dto.TrendingMoviesResponse
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MovieRepositoryTest {
    
    private lateinit var mockApiService: MockTmdbApiService
    private lateinit var mockMovieDao: MockMovieDao
    private lateinit var repository: MovieRepositoryImpl
    
    @BeforeTest
    fun setup() {
        mockApiService = MockTmdbApiService()
        mockMovieDao = MockMovieDao()
        repository = MovieRepositoryImpl(mockApiService, mockMovieDao, MockNetworkConnectivity)
    }
    
    @Test
    fun `getTrendingMovies should return cached data when cache is valid`() = runTest {
        // Given
        val cachedMovies = listOf(
            createTestMovieEntity(1, "Cached Movie 1"),
            createTestMovieEntity(2, "Cached Movie 2")
        )
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val cacheTime = currentTime - (2 * 60 * 1000L) // 2 minutes ago (within 5-minute timeout)
        
        mockMovieDao.trendingMovies = cachedMovies.toMutableList()
        mockMovieDao.trendingCacheTime = cacheTime
        
        // When
        val result = repository.getTrendingMovies(forceRefresh = false).toList()
        
        // Then
        assertEquals(1, result.size)
        assertEquals(2, result[0].size)
        assertEquals("Cached Movie 1", result[0][0].title)
        assertEquals("Cached Movie 2", result[0][1].title)
        
        // Verify API was not called
        assertEquals(0, mockApiService.getTrendingMoviesCallCount)
    }
    
    @Test
    fun `getTrendingMovies should fetch from API when cache is expired`() = runTest {
        // Given
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val expiredCacheTime = currentTime - (6 * 60 * 1000L) // 6 minutes ago (beyond 5-minute timeout)
        
        mockMovieDao.trendingCacheTime = expiredCacheTime
        mockMovieDao.trendingMovies = mutableListOf() // Empty cache
        
        val apiMovies = listOf(
            createTestMovieDto(1, "API Movie 1"),
            createTestMovieDto(2, "API Movie 2")
        )
        mockApiService.trendingMoviesResponse = TrendingMoviesResponse(
            page = 1,
            results = apiMovies,
            totalPages = 1,
            totalResults = 2
        )
        
        // When
        val result = repository.getTrendingMovies(forceRefresh = false).toList()
        
        // Then
        assertEquals(1, mockApiService.getTrendingMoviesCallCount)
        assertTrue(mockMovieDao.replaceTrendingMoviesCalled)
        
        // Should return updated movies from database after API call
        val savedMovies = mockMovieDao.trendingMovies.map { it.toDomainModel() }
        assertEquals(2, savedMovies.size)
        assertEquals("API Movie 1", savedMovies[0].title)
        assertEquals("API Movie 2", savedMovies[1].title)
    }
    
    @Test
    fun `getTrendingMovies should force refresh when forceRefresh is true`() = runTest {
        // Given
        val cachedMovies = listOf(createTestMovieEntity(1, "Cached Movie"))
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val recentCacheTime = currentTime - (1 * 60 * 1000L) // 1 minute ago (valid cache)
        
        mockMovieDao.trendingMovies = cachedMovies.toMutableList()
        mockMovieDao.trendingCacheTime = recentCacheTime
        
        val apiMovies = listOf(createTestMovieDto(1, "Fresh API Movie"))
        mockApiService.trendingMoviesResponse = TrendingMoviesResponse(
            page = 1,
            results = apiMovies,
            totalPages = 1,
            totalResults = 1
        )
        
        // When
        val result = repository.getTrendingMovies(forceRefresh = true).toList()
        
        // Then
        assertEquals(1, mockApiService.getTrendingMoviesCallCount)
        assertTrue(mockMovieDao.replaceTrendingMoviesCalled)
    }
    
    @Test
    fun `getTrendingMovies should fallback to cache when API fails`() = runTest {
        // Given
        val cachedMovies = listOf(createTestMovieEntity(1, "Cached Movie"))
        val expiredCacheTime = Clock.System.now().toEpochMilliseconds() - (6 * 60 * 1000L)
        
        mockMovieDao.trendingMovies = cachedMovies.toMutableList()
        mockMovieDao.trendingCacheTime = expiredCacheTime
        mockApiService.shouldThrowException = true
        
        // When
        val result = repository.getTrendingMovies(forceRefresh = false).toList()
        
        // Then
        assertEquals(1, mockApiService.getTrendingMoviesCallCount)
        assertEquals(1, result.size)
        assertEquals(1, result[0].size)
        assertEquals("Cached Movie", result[0][0].title)
    }
    
    @Test
    fun `getMovieDetails should return cached data when cache is valid`() = runTest {
        // Given
        val movieId = 123
        val cachedDetail = createTestMovieDetailEntity(movieId, "Cached Detail")
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val cacheTime = currentTime - (2 * 60 * 1000L) // 2 minutes ago
        
        mockMovieDao.movieDetails[movieId] = cachedDetail
        mockMovieDao.movieDetailCacheTimes[movieId] = cacheTime
        
        // When
        val result = repository.getMovieDetails(movieId, forceRefresh = false).toList()
        
        // Then
        assertEquals(1, result.size)
        assertEquals("Cached Detail", result[0].title)
        assertEquals(0, mockApiService.getMovieDetailsCallCount)
    }
    
    @Test
    fun `getMovieDetails should fetch from API when cache is expired`() = runTest {
        // Given
        val movieId = 123
        val expiredCacheTime = Clock.System.now().toEpochMilliseconds() - (6 * 60 * 1000L)
        
        mockMovieDao.movieDetailCacheTimes[movieId] = expiredCacheTime
        mockApiService.movieDetailResponse = createTestMovieDetailDto(movieId, "API Detail")
        
        // When
        val result = repository.getMovieDetails(movieId, forceRefresh = false).toList()
        
        // Then
        assertEquals(1, mockApiService.getMovieDetailsCallCount)
        assertTrue(mockMovieDao.insertMovieDetailCalled)
        assertEquals(1, result.size)
        assertEquals("API Detail", result[0].title)
    }
    
    private fun createTestMovieDto(id: Int, title: String) = MovieDto(
        id = id,
        title = title,
        overview = "Test overview",
        posterPath = "/test.jpg",
        backdropPath = "/backdrop.jpg",
        releaseDate = "2023-01-01",
        voteAverage = 8.5,
        voteCount = 1000
    )
    
    private fun createTestMovieEntity(id: Int, title: String) = 
        createTestMovieDto(id, title).toDomainModel().toEntity(isTrending = true)
    
    private fun createTestMovieDetailDto(id: Int, title: String) = 
        com.movie.core.network.dto.MovieDetailDto(
            id = id,
            title = title,
            overview = "Test overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            releaseDate = "2023-01-01",
            voteAverage = 8.5,
            voteCount = 1000,
            runtime = 120,
            genres = emptyList(),
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            spokenLanguages = emptyList(),
            status = "Released",
            tagline = "Test tagline",
            homepage = "https://test.com",
            imdbId = "tt123456",
            originalLanguage = "en",
            originalTitle = title,
            popularity = 100.0,
            budget = 1000000,
            revenue = 5000000,
            originCountry = listOf("US"),
            adult = false,
            video = false,
            belongsToCollection = null
        )
    
    private fun createTestMovieDetailEntity(id: Int, title: String) = 
        createTestMovieDetailDto(id, title).toDomainModel().toEntity()
}