package com.movie.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.movie.feature.moviedetail.navigation.movieDetailScreen
import com.movie.feature.moviedetail.navigation.navigateToMovieDetail
import com.movie.feature.movies.navigation.MoviesRoute
import com.movie.feature.movies.navigation.moviesScreen

/**
 * Main navigation graph for the movie app
 */
@Composable
fun MovieNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MoviesRoute
    ) {
        moviesScreen(
            onMovieClick = { movieId ->
                navController.navigateToMovieDetail(movieId)
            }
        )
        
        movieDetailScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}