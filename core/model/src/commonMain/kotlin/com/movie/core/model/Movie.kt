package com.movie.core.model

import kotlinx.serialization.Serializable

/**
 * Data model representing a movie from TMDB API.
 * This is the core domain model used throughout the application.
 * 
 * @property id Unique identifier for the movie
 * @property title The movie's title
 * @property overview Brief description/plot summary of the movie
 * @property posterPath Relative path to the movie poster image (can be null)
 * @property backdropPath Relative path to the backdrop image (can be null)
 * @property releaseDate Full release date in YYYY-MM-DD format
 * @property voteAverage Average user rating (0.0 to 10.0)
 * @property voteCount Total number of user votes
 */
@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int
) {
    /**
     * Extracts the year from the release date.
     * @return The year as a string, or empty string if parsing fails
     */
    val year: String
        get() = releaseDate.split("-").firstOrNull() ?: ""
    
    /**
     * Constructs the full URL for the movie poster image.
     * Uses TMDB's w500 size for optimal mobile display.
     * @return Complete image URL or null if posterPath is null
     */
    val fullPosterUrl: String?
        get() = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
    
    /**
     * Constructs the full URL for the backdrop image.
     * Uses TMDB's w780 size for high-quality backgrounds.
     * @return Complete image URL or null if backdropPath is null
     */
    val fullBackdropUrl: String?
        get() = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" }
}