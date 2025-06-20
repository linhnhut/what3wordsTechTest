package com.movie.core.data.mapper

import com.movie.core.database.entity.MovieEntity
import com.movie.core.database.entity.MovieDetailEntity
import com.movie.core.model.Movie
import com.movie.core.model.MovieDetail
import com.movie.core.model.Genre
import com.movie.core.model.ProductionCompany
import com.movie.core.model.ProductionCountry
import com.movie.core.model.SpokenLanguage
import com.movie.core.model.Collection
import com.movie.core.network.dto.MovieDto
import com.movie.core.network.dto.MovieDetailDto
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

private val json = Json { ignoreUnknownKeys = true }

/**
 * Convert MovieEntity to domain Movie model
 */
fun MovieEntity.toDomainModel(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

/**
 * Convert domain Movie model to MovieEntity
 */
fun Movie.toEntity(isTrending: Boolean = false): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        isTrending = isTrending,
        cachedAt = Clock.System.now().toEpochMilliseconds()
    )
}

/**
 * Convert MovieDetailEntity to domain MovieDetail model
 */
fun MovieDetailEntity.toDomainModel(): MovieDetail {
    val parsedGenres = try {
        json.decodeFromString<List<Genre>>(genres)
    } catch (e: Exception) {
        emptyList<Genre>()
    }
    
    val parsedProductionCompanies = try {
        json.decodeFromString<List<ProductionCompany>>(productionCompanies)
    } catch (e: Exception) {
        emptyList<ProductionCompany>()
    }
    
    val parsedProductionCountries = try {
        json.decodeFromString<List<ProductionCountry>>(productionCountries)
    } catch (e: Exception) {
        emptyList<ProductionCountry>()
    }
    
    val parsedSpokenLanguages = try {
        json.decodeFromString<List<SpokenLanguage>>(spokenLanguages)
    } catch (e: Exception) {
        emptyList<SpokenLanguage>()
    }
    
    val parsedOriginCountry = try {
        json.decodeFromString<List<String>>(originCountry)
    } catch (e: Exception) {
        emptyList<String>()
    }
    
    val parsedCollection = try {
        belongsToCollection?.let { json.decodeFromString<Collection>(it) }
    } catch (e: Exception) {
        null
    }
    
    return MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime,
        genres = parsedGenres,
        homepage = homepage,
        budget = budget,
        revenue = revenue,
        status = status,
        tagline = tagline,
        productionCompanies = parsedProductionCompanies,
        productionCountries = parsedProductionCountries,
        spokenLanguages = parsedSpokenLanguages,
        imdbId = imdbId,
        originalTitle = originalTitle,
        originCountry = parsedOriginCountry,
        adult = adult,
        popularity = popularity,
        video = video,
        belongsToCollection = parsedCollection
    )
}

/**
 * Convert domain MovieDetail model to MovieDetailEntity
 */
fun MovieDetail.toEntity(): MovieDetailEntity {
    return MovieDetailEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime,
        genres = json.encodeToString(genres),
        homepage = homepage,
        budget = budget,
        revenue = revenue,
        status = status,
        tagline = tagline,
        productionCompanies = json.encodeToString(productionCompanies),
        productionCountries = json.encodeToString(productionCountries),
        spokenLanguages = json.encodeToString(spokenLanguages),
        imdbId = imdbId,
        originalTitle = originalTitle,
        originCountry = json.encodeToString(originCountry),
        adult = adult,
        popularity = popularity,
        video = video,
        belongsToCollection = belongsToCollection?.let { json.encodeToString(it) },
        cachedAt = Clock.System.now().toEpochMilliseconds()
    )
}

/**
 * Convert network MovieDto to domain Movie model
 */
fun MovieDto.toDomainModel(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview ?: "",
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0
    )
}

/**
 * Convert network MovieDetailDto to domain MovieDetail model
 */
fun MovieDetailDto.toDomainModel(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime,
        genres = genres.map { Genre(it.id, it.name) },
        homepage = homepage,
        budget = budget,
        revenue = revenue,
        status = status,
        tagline = tagline,
        productionCompanies = productionCompanies.map { 
            ProductionCompany(
                id = it.id,
                name = it.name,
                logoPath = it.logoPath,
                originCountry = it.originCountry
            )
        },
        productionCountries = productionCountries?.map {
            ProductionCountry(
                iso = it.iso,
                name = it.name
            )
        } ?: emptyList(),
        spokenLanguages = spokenLanguages?.map {
            SpokenLanguage(
                englishName = it.englishName,
                iso = it.iso,
                name = it.name
            )
        } ?: emptyList(),
        imdbId = imdbId,
        originalTitle = originalTitle,
        originCountry = originCountry ?: emptyList(),
        adult = adult ?: false,
        popularity = popularity ?: 0.0,
        video = video ?: false,
        belongsToCollection = belongsToCollection?.let {
            Collection(
                id = it.id,
                name = it.name,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath
            )
        }
    )
}