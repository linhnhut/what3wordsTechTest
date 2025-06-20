package com.movie.feature.moviedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.movie.feature.moviedetail.MovieDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailRoute(val movieId: Int)

fun NavController.navigateToMovieDetail(movieId: Int, navOptions: NavOptionsBuilder.() -> Unit = {}) {
    navigate(route = MovieDetailRoute(movieId)) {
        navOptions()
    }
}

fun NavGraphBuilder.movieDetailScreen(
    onBackClick: () -> Unit,
) {
    composable<MovieDetailRoute> { entry ->
        val movieId = entry.toRoute<MovieDetailRoute>().movieId
        MovieDetailScreen(
            movieId = movieId,
            onBackClick = onBackClick
        )
    }
}