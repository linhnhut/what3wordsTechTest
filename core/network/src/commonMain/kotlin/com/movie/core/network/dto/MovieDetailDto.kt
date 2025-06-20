package com.movie.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val runtime: Int?,
    val genres: List<GenreDto>,
    val homepage: String?,
    val budget: Long,
    val revenue: Long,
    val status: String,
    val tagline: String?,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryDto>?,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDto>?,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("original_title")
    val originalTitle: String?,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("origin_country")
    val originCountry: List<String>?,
    val adult: Boolean?,
    val popularity: Double?,
    val video: Boolean?,
    @SerialName("belongs_to_collection")
    val belongsToCollection: CollectionDto?
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)

@Serializable
data class ProductionCompanyDto(
    val id: Int,
    val name: String,
    @SerialName("logo_path")
    val logoPath: String?,
    @SerialName("origin_country")
    val originCountry: String
)

@Serializable
data class ProductionCountryDto(
    @SerialName("iso_3166_1")
    val iso: String,
    val name: String
)

@Serializable
data class SpokenLanguageDto(
    @SerialName("english_name")
    val englishName: String,
    @SerialName("iso_639_1")
    val iso: String,
    val name: String
)

@Serializable
data class CollectionDto(
    val id: Int,
    val name: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?
)