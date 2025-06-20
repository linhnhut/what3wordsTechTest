package com.movie.feature.movies.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.movie.feature.movies.MoviesScreen
import kotlinx.serialization.Serializable

@Serializable
data object MoviesRoute

fun NavController.navigateToMovies(navOptions: NavOptions? = null) =
    navigate(MoviesRoute, navOptions)

fun NavGraphBuilder.moviesScreen(
    onMovieClick: (Int) -> Unit,
) {
    composable<MoviesRoute> {
        MoviesScreen(
            onMovieClick = onMovieClick
        )
    }
}