package com.movie.core.data.repository

import com.movie.core.network.api.ApiService
import com.movie.core.network.dto.MovieDetailDto
import com.movie.core.network.dto.SearchMoviesResponse
import com.movie.core.network.dto.TrendingMoviesResponse

class MockTmdbApiService : ApiService {
    var trendingMoviesResponse: TrendingMoviesResponse = TrendingMoviesResponse(1, emptyList(), 1, 0)
    var searchMoviesResponse: SearchMoviesResponse = SearchMoviesResponse(1, emptyList(), 1, 0)
    var movieDetailResponse: MovieDetailDto? = null
    var shouldThrowException = false
    
    var getTrendingMoviesCallCount = 0
    var searchMoviesCallCount = 0
    var getMovieDetailsCallCount = 0
    
    override suspend fun getTrendingMovies(): TrendingMoviesResponse {
        getTrendingMoviesCallCount++
        if (shouldThrowException) {
            throw RuntimeException("Network error")
        }
        return trendingMoviesResponse
    }
    
    override suspend fun searchMovies(query: String): SearchMoviesResponse {
        searchMoviesCallCount++
        if (shouldThrowException) {
            throw RuntimeException("Network error")
        }
        return searchMoviesResponse
    }
    
    override suspend fun getMovieDetails(movieId: Int): MovieDetailDto {
        getMovieDetailsCallCount++
        if (shouldThrowException) {
            throw RuntimeException("Network error")
        }
        return movieDetailResponse ?: throw RuntimeException("No movie detail response set")
    }
}