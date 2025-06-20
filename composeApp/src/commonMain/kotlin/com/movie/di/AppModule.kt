package com.movie.di

import com.movie.core.common.di.commonModule
import com.movie.core.data.di.dataModule
import com.movie.core.database.di.databaseModule
import com.movie.core.network.di.networkModule
import com.movie.feature.moviedetail.di.movieDetailModule
import com.movie.feature.movies.di.moviesModule
import org.koin.dsl.module

/**
 * Main application module that aggregates all feature modules
 */
val appModule = module {
    // Add any app-level dependencies here
}

/**
 * All modules to be loaded by Koin
 */
val allModules = listOf(
    appModule,
    commonModule,
    networkModule,
    databaseModule,
    dataModule,
    moviesModule,
    movieDetailModule,
)