package com.movie

import androidx.compose.ui.window.ComposeUIViewController
import com.movie.core.design.theme.MovieTheme

fun MainViewController() = ComposeUIViewController { 
    MovieTheme {
        App() 
    }
}