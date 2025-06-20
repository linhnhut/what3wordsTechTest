package com.movie.core.network.api

import com.movie.core.network.dto.MovieDetailDto
import com.movie.core.network.dto.SearchMoviesResponse
import com.movie.core.network.dto.TrendingMoviesResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class TmdbApiService(
    private val httpClient: HttpClient
) : ApiService {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3"
        private const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NGIwMTkyNmZhYTg2ODRlMmJiY2ZkZTI4ZTgwNTZkOSIsIm5iZiI6MTc0OTcxNjc2OC4xNDksInN1YiI6IjY4NGE4ZjIwNzBlODY4Yjg4MGIwZDNiNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7DDv7c1TnQJn7KI9gAbBK-8R37JOBt7eQmB9-nqlePM"
    }

    override suspend fun getTrendingMovies(): TrendingMoviesResponse {
        return httpClient.get("$BASE_URL/trending/movie/day") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $API_KEY")
                append(HttpHeaders.Accept, ContentType.Application.Json.toString())
            }
        }.body()
    }

    override suspend fun searchMovies(query: String): SearchMoviesResponse {
        return httpClient.get("$BASE_URL/search/movie") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $API_KEY")
                append(HttpHeaders.Accept, ContentType.Application.Json.toString())
            }
            parameter("query", query)
        }.body()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailDto {
        return httpClient.get("$BASE_URL/movie/$movieId") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $API_KEY")
                append(HttpHeaders.Accept, ContentType.Application.Json.toString())
            }
        }.body()
    }
}