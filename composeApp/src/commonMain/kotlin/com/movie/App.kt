package com.movie

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.movie.core.design.component.MovieBackground
import com.movie.core.design.component.MovieCard
import com.movie.core.design.theme.MovieTheme
import com.movie.navigation.MovieNavGraph
import com.movie.presentation.theme.ThemePreviewScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    MovieTheme {
        MovieBackground {
            MovieNavGraph()
        }
    }
}

/**
 * Previews for all theme variations
 */
@Preview
@Composable
private fun LightCinemaPreview() {
    MovieTheme(darkTheme = false, androidTheme = false) {
        MovieBackground { ThemePreviewScreen() }
    }
}

@Preview
@Composable
private fun DarkCinemaPreview() {
    MovieTheme(darkTheme = true, androidTheme = false) {
        MovieBackground { ThemePreviewScreen() }
    }
}

@Preview
@Composable
private fun LightAndroidPreview() {
    MovieTheme(darkTheme = false, androidTheme = true) {
        MovieBackground { ThemePreviewScreen() }
    }
}

@Preview
@Composable
private fun DarkAndroidPreview() {
    MovieTheme(darkTheme = true, androidTheme = true) {
        MovieBackground { ThemePreviewScreen() }
    }
}