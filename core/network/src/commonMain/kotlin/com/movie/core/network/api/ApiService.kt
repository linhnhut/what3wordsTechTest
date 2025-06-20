package com.movie.core.network.api

import com.movie.core.network.dto.MovieDetailDto
import com.movie.core.network.dto.SearchMoviesResponse
import com.movie.core.network.dto.TrendingMoviesResponse

interface ApiService {
    suspend fun getTrendingMovies(): TrendingMoviesResponse
    suspend fun searchMovies(query: String): SearchMoviesResponse
    suspend fun getMovieDetails(movieId: Int): MovieDetailDto
}