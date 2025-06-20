package com.movie.feature.movies.di

import com.movie.feature.movies.MoviesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
    viewModel { MoviesViewModel(get()) }
}