package com.movie.core.database.di

import com.movie.core.database.MovieDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { MovieDatabase.get() }
    single { get<MovieDatabase>().movieDao() }
}