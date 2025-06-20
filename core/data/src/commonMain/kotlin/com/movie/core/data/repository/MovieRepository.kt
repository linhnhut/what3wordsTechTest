package com.movie.core.data.repository

import com.movie.core.model.Movie
import com.movie.core.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    
    /**
     * Retrieves trending movies for today from TMDB API.
     * Implements caching with 5-minute expiration for offline support.
     * 
     * @param forceRefresh If true, bypasses cache and fetches fresh data from network
     * @return Flow emitting list of trending movies
     */
    fun getTrendingMovies(forceRefresh: Boolean = false): Flow<List<Movie>>
    
    /**
     * Searches for movies by query string using TMDB search API.
     * This operation is online-only and does not use caching.
     * 
     * @param query Search term to find movies
     * @return Flow emitting list of movies matching the search query
     */
    fun searchMovies(query: String): Flow<List<Movie>>
    
    /**
     * Retrieves detailed information for a specific movie.
     * Implements caching for offline access to previously viewed movies.
     * 
     * @param movieId TMDB movie identifier
     * @param forceRefresh If true, bypasses cache and fetches fresh data from network
     * @return Flow emitting detailed movie information
     */
    fun getMovieDetails(movieId: Int, forceRefresh: Boolean = false): Flow<MovieDetail>
}