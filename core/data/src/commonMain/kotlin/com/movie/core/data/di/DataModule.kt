package com.movie.core.data.di

import com.movie.core.common.network.INetworkConnectivity
import com.movie.core.common.network.NetworkConnectivity
import com.movie.core.data.repository.MovieRepositoryImpl
import com.movie.core.data.repository.MovieRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::MovieRepositoryImpl) { bind<MovieRepository>() }
    single<INetworkConnectivity> { NetworkConnectivity }
}