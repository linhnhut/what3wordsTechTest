package com.movie

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.movie.core.design.theme.MovieTheme
import com.movie.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Track dark theme state for edge-to-edge styling
        var darkTheme by mutableStateOf(false)

        setContent {
            darkTheme = isSystemInDarkTheme()
            
            // Configure edge-to-edge based on theme
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(
                    lightScrim = android.graphics.Color.TRANSPARENT,
                    darkScrim = android.graphics.Color.TRANSPARENT,
                ) { darkTheme },
                navigationBarStyle = SystemBarStyle.auto(
                    lightScrim = lightScrim,
                    darkScrim = darkScrim,
                ) { darkTheme },
            )

            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    MovieTheme {
        App()
    }
}

private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)