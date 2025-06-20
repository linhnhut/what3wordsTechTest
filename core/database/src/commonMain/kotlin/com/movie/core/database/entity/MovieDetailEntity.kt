package com.movie.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock

@Entity(tableName = "movie_details")
data class MovieDetailEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val runtime: Int?,
    val genres: String, // JSON string
    val homepage: String?,
    val budget: Long,
    val revenue: Long,
    val status: String,
    val tagline: String?,
    val productionCompanies: String, // JSON string
    val productionCountries: String, // JSON string
    val spokenLanguages: String, // JSON string
    val imdbId: String?,
    val originalTitle: String?,
    val originCountry: String, // JSON string
    val adult: Boolean,
    val popularity: Double,
    val video: Boolean,
    val belongsToCollection: String?, // JSON string
    val cachedAt: Long = Clock.System.now().toEpochMilliseconds()
)