package com.movie.core.design.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.movie.core.design.theme.LocalGradientColors
import com.movie.core.design.theme.MovieTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A showcase of the Movie theme system.
 * This composable demonstrates all theme variations and components.
 */
@Composable
fun MovieThemeShowcase() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Theme Header
        ThemeHeader()
        
        // Theme Variations Grid
        ThemeVariationsGrid()
        
        // Component Showcase
        ComponentShowcase()
        
        // Background Showcase
        BackgroundShowcase()
    }
}

@Composable
private fun ThemeHeader() {
    MovieCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Movie Design System",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Cinema-inspired themes with Material 3",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ThemeVariationsGrid() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Theme Variations",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ThemeVariationCard(
                title = "Light Cinema",
                darkTheme = false,
                androidTheme = false
            )
            ThemeVariationCard(
                title = "Dark Cinema",
                darkTheme = true,
                androidTheme = false
            )
            ThemeVariationCard(
                title = "Light Android",
                darkTheme = false,
                androidTheme = true
            )
            ThemeVariationCard(
                title = "Dark Android",
                darkTheme = true,
                androidTheme = true
            )
        }
    }
}

@Composable
private fun ThemeVariationCard(
    title: String,
    darkTheme: Boolean,
    androidTheme: Boolean,
) {
    MovieTheme(
        darkTheme = darkTheme,
        androidTheme = androidTheme,
    ) {
        Surface(
            modifier = Modifier
                .size(160.dp, 120.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(12.dp)
                ),
            color = MaterialTheme.colorScheme.surface
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // Color samples
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        ColorSample(MaterialTheme.colorScheme.primary)
                        ColorSample(MaterialTheme.colorScheme.secondary)
                        ColorSample(MaterialTheme.colorScheme.tertiary)
                    }
                    
                    // Surface samples
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Surface",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ColorSample(color: Color) {
    Surface(
        modifier = Modifier.size(24.dp),
        color = color,
        shape = RoundedCornerShape(4.dp)
    ) {}
}

@Composable
private fun ComponentShowcase() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Components",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        // Buttons
        MovieCard {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Buttons",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MovieButton(onClick = {}) {
                        Text("Primary")
                    }
                    MovieOutlinedButton(onClick = {}) {
                        Text("Outlined")
                    }
                    MovieTextButton(onClick = {}) {
                        Text("Text")
                    }
                }
            }
        }
        
        // Cards
        MovieCard {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Cards & Surfaces",
                    style = MaterialTheme.typography.titleMedium
                )
                
                MovieCard(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Primary Container",
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                
                MovieCard(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Secondary Container",
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BackgroundShowcase() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Backgrounds",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        // Gradient Background Sample
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            MovieGradientBackground(
                topColor = LocalGradientColors.current.top,
                bottomColor = LocalGradientColors.current.bottom
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Gradient Background",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
        
        // Cinematic Background Sample
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            MovieCinematicBackground {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cinematic Background",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieThemeShowcasePreview() {
    MovieTheme {
        MovieBackground {
            MovieThemeShowcase()
        }
    }
}