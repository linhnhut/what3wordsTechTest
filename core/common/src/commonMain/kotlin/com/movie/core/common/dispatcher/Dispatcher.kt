package com.movie.core.common.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface Dispatcher {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

enum class DispatcherType {
    Main,
    IO,
    Default,
    Unconfined
}