package com.movie.presentation.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.movie.core.design.component.MovieIconButton
import com.movie.core.design.component.MovieLoadingWheel
import com.movie.core.design.component.MovieOutlinedButton
import com.movie.core.design.component.MovieTextButton
import com.movie.core.design.icon.MovieIcons
import com.movie.core.design.theme.MovieTheme
import com.movie.core.design.theme.gradientColors
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ThemePreviewScreen() {
    MovieBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            Text(
                text = "Movie Design System",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            
            // Color Palette Section
            ColorPaletteSection()
            
            // Typography Section
            TypographySection()
            
            // Component Section
            ComponentSection()
            
            // Loading States
            LoadingSection()
            
            // Icon showcase
            IconSection()
        }
    }
}

@Composable
private fun ColorPaletteSection() {
    MovieCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Color Palette",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            // Primary Colors
            ColorRow(
                label = "Primary",
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.onPrimary,
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            
            // Secondary Colors
            ColorRow(
                label = "Secondary",
                colors = listOf(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.onSecondary,
                    MaterialTheme.colorScheme.secondaryContainer,
                    MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
            
            // Tertiary Colors
            ColorRow(
                label = "Tertiary",
                colors = listOf(
                    MaterialTheme.colorScheme.tertiary,
                    MaterialTheme.colorScheme.onTertiary,
                    MaterialTheme.colorScheme.tertiaryContainer,
                    MaterialTheme.colorScheme.onTertiaryContainer
                )
            )
            
            // Surface Colors
            ColorRow(
                label = "Surface",
                colors = listOf(
                    MaterialTheme.colorScheme.surface,
                    MaterialTheme.colorScheme.onSurface,
                    MaterialTheme.colorScheme.surfaceVariant,
                    MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
private fun ColorRow(
    label: String,
    colors: List<androidx.compose.ui.graphics.Color>
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            colors.forEach { color ->
                Surface(
                    modifier = Modifier.size(32.dp),
                    color = color,
                    shape = MaterialTheme.shapes.small
                ) {}
            }
        }
    }
}

@Composable
private fun TypographySection() {
    MovieCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Typography Scale",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Display Large",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = "Headline Large",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "Title Large",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Body Large - The quick brown fox jumps over the lazy dog",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Body Medium - Perfect for content text",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Label Large - For buttons and actions",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun ComponentSection() {
    MovieCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Component Showcase",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            // Buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MovieButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cinema Red Primary Button")
                }
                
                MovieOutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Hollywood Gold Outlined Button")
                }
                
                MovieTextButton(
                    onClick = { }
                ) {
                    Text("Night Sky Text Button")
                }
            }
            
            // Nested Card Example
            MovieCard(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = "Nested Movie Card",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Perfect for movie posters and details",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingSection() {
    MovieCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Loading States",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovieLoadingWheel(
                    color = MaterialTheme.colorScheme.primary,
                    size = 24.dp
                )
                
                MovieLoadingWheel(
                    color = MaterialTheme.colorScheme.secondary,
                    size = 32.dp
                )
                
                MovieLoadingWheel(
                    color = MaterialTheme.colorScheme.tertiary,
                    size = 40.dp
                )
            }
        }
    }
}

@Composable
private fun IconSection() {
    MovieCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Icon System",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MovieIconButton(
                    onClick = { },
                    icon = MovieIcons.Home,
                    contentDescription = "Home"
                )
                
                MovieIconButton(
                    onClick = { },
                    icon = MovieIcons.Search,
                    contentDescription = "Search"
                )
                
                MovieIconButton(
                    onClick = { },
                    icon = MovieIcons.Bookmark,
                    contentDescription = "Bookmark"
                )
                
                MovieIconButton(
                    onClick = { },
                    icon = MovieIcons.Person,
                    contentDescription = "Profile"
                )
                
                MovieIconButton(
                    onClick = { },
                    icon = MovieIcons.Star,
                    contentDescription = "Favorite"
                )
                
                MovieIconButton(
                    onClick = { },
                    icon = MovieIcons.Play,
                    contentDescription = "Play"
                )
            }
        }
    }
}

@Preview
@Composable
fun ThemePreviewScreenLight() {
    MovieTheme(darkTheme = false) {
        ThemePreviewScreen()
    }
}

@Preview
@Composable
fun ThemePreviewScreenDark() {
    MovieTheme(darkTheme = true) {
        ThemePreviewScreen()
    }
}