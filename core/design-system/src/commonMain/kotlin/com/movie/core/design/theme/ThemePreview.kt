package com.movie.core.design.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.movie.core.design.component.MovieBackground
import com.movie.core.design.component.MovieButton
import com.movie.core.design.component.MovieCard
import com.movie.core.design.component.MovieGradientBackground
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Theme preview screen that shows the current theme configuration.
 */
@Composable
fun MovieThemePreviewScreen(
    themeTitle: String = "Movie Theme"
) {
    MovieBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Theme Title
            Text(
                text = themeTitle,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            // Color Showcase
            ColorShowcase()
            
            // Typography Showcase
            TypographyShowcase()
            
            // Component Showcase
            ComponentShowcase()
        }
    }
}

@Composable
private fun ColorShowcase() {
    MovieCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Color Scheme",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            // Primary Colors
            ColorItem(
                name = "Primary",
                color = MaterialTheme.colorScheme.primary,
                onColor = MaterialTheme.colorScheme.onPrimary
            )
            
            // Secondary Colors
            ColorItem(
                name = "Secondary",
                color = MaterialTheme.colorScheme.secondary,
                onColor = MaterialTheme.colorScheme.onSecondary
            )
            
            // Tertiary Colors
            ColorItem(
                name = "Tertiary",
                color = MaterialTheme.colorScheme.tertiary,
                onColor = MaterialTheme.colorScheme.onTertiary
            )
            
            // Surface Colors
            ColorItem(
                name = "Surface",
                color = MaterialTheme.colorScheme.surface,
                onColor = MaterialTheme.colorScheme.onSurface
            )
            
            // Background Colors
            ColorItem(
                name = "Background",
                color = MaterialTheme.colorScheme.background,
                onColor = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun ColorItem(
    name: String,
    color: androidx.compose.ui.graphics.Color,
    onColor: androidx.compose.ui.graphics.Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium
        )
        
        Surface(
            color = color,
            contentColor = onColor,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = "Aa",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TypographyShowcase() {
    MovieCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Typography",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Display Large",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = "Headline Medium",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Title Large",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Body Large",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Label Medium",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun ComponentShowcase() {
    MovieCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Components",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            MovieButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Primary Button")
            }
            
            MovieCard(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = "Nested Card",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

/**
 * Individual preview functions for each theme variation
 */
@Preview
@Composable
fun LightCinemaThemePreview() {
    MovieTheme(
        darkTheme = false,
        androidTheme = false,
    ) {
        MovieThemePreviewScreen("Light Cinema Theme")
    }
}

@Preview
@Composable
fun DarkCinemaThemePreview() {
    MovieTheme(
        darkTheme = true,
        androidTheme = false,
    ) {
        MovieThemePreviewScreen("Dark Cinema Theme")
    }
}

@Preview
@Composable
fun LightAndroidThemePreview() {
    MovieTheme(
        darkTheme = false,
        androidTheme = true,
    ) {
        MovieThemePreviewScreen("Light Android Theme")
    }
}

@Preview
@Composable
fun DarkAndroidThemePreview() {
    MovieTheme(
        darkTheme = true,
        androidTheme = true,
    ) {
        MovieThemePreviewScreen("Dark Android Theme")
    }
}

/**
 * Background gradient preview
 */
@Preview
@Composable
fun GradientBackgroundPreview() {
    MovieTheme {
        MovieGradientBackground(
            topColor = LocalGradientColors.current.top,
            bottomColor = LocalGradientColors.current.bottom,
        ) {
            MovieCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Gradient Background",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "This demonstrates the theme's gradient colors",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}