package com.movie.core.model

import kotlinx.serialization.Serializable

/**
 * Comprehensive data model representing detailed movie information from TMDB API.
 * Contains all available movie metadata including production details, financial information,
 * and extended attributes beyond the basic Movie model.
 * 
 * @property id Unique identifier for the movie
 * @property title The movie's title
 * @property overview Brief description/plot summary of the movie
 * @property posterPath Relative path to the movie poster image
 * @property backdropPath Relative path to the backdrop image
 * @property releaseDate Full release date in YYYY-MM-DD format
 * @property voteAverage Average user rating (0.0 to 10.0)
 * @property voteCount Total number of user votes
 * @property runtime Movie duration in minutes
 * @property genres List of movie genres
 * @property homepage Official movie website URL
 * @property budget Movie production budget in USD
 * @property revenue Total box office revenue in USD
 * @property status Current release status (e.g., "Released", "In Production")
 * @property tagline Movie tagline/slogan
 * @property productionCompanies List of companies that produced the movie
 * @property productionCountries List of countries where the movie was produced
 * @property spokenLanguages List of languages spoken in the movie
 * @property imdbId IMDb identifier for external linking
 * @property originalTitle Original title in the source language
 * @property originCountry List of origin countries
 * @property adult Whether the movie is rated for adults only
 * @property popularity TMDB popularity score
 * @property video Whether this is a video/TV movie
 * @property belongsToCollection Collection information if movie is part of a series
 */
@Serializable
data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val runtime: Int?,
    val genres: List<Genre>,
    val homepage: String?,
    val budget: Long,
    val revenue: Long,
    val status: String,
    val tagline: String?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val spokenLanguages: List<SpokenLanguage>,
    val imdbId: String?,
    val originalTitle: String?,
    val originCountry: List<String>,
    val adult: Boolean,
    val popularity: Double,
    val video: Boolean,
    val belongsToCollection: Collection?
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
    
    /**
     * Formats the runtime into a human-readable string.
     * @return Formatted runtime as "Xh Ym" or null if runtime is not available
     */
    val formattedRuntime: String?
        get() = runtime?.let { 
            val hours = it / 60
            val minutes = it % 60
            "${hours}h ${minutes}m"
        }
}

/**
 * Represents a movie genre from TMDB.
 * 
 * @property id Unique identifier for the genre
 * @property name Human-readable genre name (e.g., "Action", "Comedy")
 */
@Serializable
data class Genre(
    val id: Int,
    val name: String
)

/**
 * Represents a production company involved in making the movie.
 * 
 * @property id Unique identifier for the company
 * @property name Company name
 * @property logoPath Relative path to company logo image
 * @property originCountry Country where the company is based
 */
@Serializable
data class ProductionCompany(
    val id: Int,
    val name: String,
    val logoPath: String?,
    val originCountry: String
)

/**
 * Represents a country where the movie was produced.
 * 
 * @property iso ISO country code (e.g., "US", "GB")
 * @property name Full country name
 */
@Serializable
data class ProductionCountry(
    val iso: String,
    val name: String
)

/**
 * Represents a language spoken in the movie.
 * 
 * @property englishName Language name in English
 * @property iso ISO language code
 * @property name Native language name
 */
@Serializable
data class SpokenLanguage(
    val englishName: String,
    val iso: String,
    val name: String
)

/**
 * Represents a movie collection/series that this movie belongs to.
 * 
 * @property id Unique identifier for the collection
 * @property name Collection name (e.g., "The Matrix Collection")
 * @property posterPath Relative path to collection poster image
 * @property backdropPath Relative path to collection backdrop image
 */
@Serializable
data class Collection(
    val id: Int,
    val name: String,
    val posterPath: String?,
    val backdropPath: String?
)