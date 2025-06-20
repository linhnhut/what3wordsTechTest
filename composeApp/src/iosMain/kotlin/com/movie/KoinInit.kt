package com.movie

import com.movie.di.allModules
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(allModules)
    }
}