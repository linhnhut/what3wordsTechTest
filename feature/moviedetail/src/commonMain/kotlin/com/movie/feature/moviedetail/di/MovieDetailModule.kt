package com.movie.feature.moviedetail.di

import com.movie.feature.moviedetail.MovieDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val movieDetailModule = module {
    viewModel { MovieDetailViewModel(get()) }
}