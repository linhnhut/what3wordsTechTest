package com.movie.core.network.client

import io.ktor.client.*
import io.ktor.client.engine.darwin.*

actual fun createHttpClient(): HttpClient = HttpClient(Darwin)