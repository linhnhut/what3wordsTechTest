package com.movie.core.network.di

import com.movie.core.network.api.ApiService
import com.movie.core.network.api.TmdbApiService
import com.movie.core.network.client.createHttpClient
import org.koin.dsl.module

val networkModule = module {
    single { createHttpClient(enableLogging = true) }
    single<ApiService> { TmdbApiService(get()) }
}