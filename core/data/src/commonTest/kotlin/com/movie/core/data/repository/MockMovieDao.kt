package com.movie.core.data.repository

import com.movie.core.database.dao.MovieDao
import com.movie.core.database.entity.MovieDetailEntity
import com.movie.core.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockMovieDao : MovieDao {
    var trendingMovies = mutableListOf<MovieEntity>()
    var movieDetails = mutableMapOf<Int, MovieDetailEntity>()
    var trendingCacheTime: Long? = null
    var movieDetailCacheTimes = mutableMapOf<Int, Long>()
    
    var replaceTrendingMoviesCalled = false
    var insertMovieDetailCalled = false
    
    override fun getTrendingMovies(): Flow<List<MovieEntity>> {
        return flowOf(trendingMovies.toList())
    }
    
    override suspend fun getTrendingMoviesSync(): List<MovieEntity> {
        return trendingMovies.toList()
    }
    
    override suspend fun insertMovies(movies: List<MovieEntity>) {
        trendingMovies.addAll(movies)
    }
    
    override suspend fun deleteTrendingMovies() {
        trendingMovies.clear()
    }
    
    override suspend fun replaceTrendingMovies(movies: List<MovieEntity>) {
        replaceTrendingMoviesCalled = true
        deleteTrendingMovies()
        insertMovies(movies)
    }
    
    override suspend fun getMovieDetail(movieId: Int): MovieDetailEntity? {
        return movieDetails[movieId]
    }
    
    override suspend fun insertMovieDetail(movieDetail: MovieDetailEntity) {
        insertMovieDetailCalled = true
        movieDetails[movieDetail.id] = movieDetail
    }
    
    override suspend fun getTrendingCacheTime(): Long? {
        return trendingCacheTime
    }
    
    override suspend fun getMovieDetailCacheTime(movieId: Int): Long? {
        return movieDetailCacheTimes[movieId]
    }
}