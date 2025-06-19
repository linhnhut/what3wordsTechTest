package com.test.movie

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform