package com.movie.core.common.di

import com.movie.core.common.dispatcher.Dispatcher
import com.movie.core.common.dispatcher.IosDispatcher
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<Dispatcher> { IosDispatcher() }
}