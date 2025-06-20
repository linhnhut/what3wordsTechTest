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
import com.movie.core.design.component.MovieBackground
import com.movie.core.design.component.MovieButton
import com.movie.core.design.component.MovieCard
import com.movie.core.design.component.MovieCinematicBackground
import com.movie.core.design.component.MovieGradientBackground
import com.movie.core.design.component.MovieGradientOverlay
import com.movie.core.design.component.MovieRadialBackground
import com.movie.core.design.component.MovieSurfaceBackground
import com.movie.core.design.theme.MovieTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BackgroundShowcaseScreen() {
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
                text = "Background Components",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            
            // Standard MovieBackground
            BackgroundPreview(
                title = "MovieBackground",
                description = "Uses theme gradient colors"
            ) {
                MovieBackground {
                    SampleContent()
                }
            }
            
            // Custom Gradient Background
            BackgroundPreview(
                title = "MovieGradientBackground",
                description = "Custom gradient colors"
            ) {
                MovieGradientBackground(
                    topColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    bottomColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                ) {
                    SampleContent()
                }
            }
            
            // Surface Background
            BackgroundPreview(
                title = "MovieSurfaceBackground",
                description = "Solid color background"
            ) {
                MovieSurfaceBackground(
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    SampleContent()
                }
            }
            
            // Cinematic Background
            BackgroundPreview(
                title = "MovieCinematicBackground",
                description = "Cinema-themed gradient"
            ) {
                MovieCinematicBackground {
                    SampleContent()
                }
            }
            
            // Radial Background
            BackgroundPreview(
                title = "MovieRadialBackground",
                description = "Radial gradient effect"
            ) {
                MovieRadialBackground(
                    centerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
                    edgeColor = MaterialTheme.colorScheme.background
                ) {
                    SampleContent()
                }
            }
            
            // Gradient Overlay Example
            BackgroundPreview(
                title = "MovieGradientOverlay",
                description = "Overlay for images"
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Simulated image background
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.primary
                    ) {}
                    
                    // Gradient overlay
                    MovieGradientOverlay(
                        gradientColors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                    
                    // Content on top
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Column {
                            Text(
                                text = "Movie Title",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Gradient ensures text readability",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BackgroundPreview(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp)),
            shadowElevation = 4.dp
        ) {
            content()
        }
    }
}

@Composable
private fun SampleContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        MovieCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sample Content",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "This content is displayed on the background",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MovieButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Action")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun BackgroundShowcaseScreenLight() {
    MovieTheme(darkTheme = false) {
        BackgroundShowcaseScreen()
    }
}

@Preview
@Composable
fun BackgroundShowcaseScreenDark() {
    MovieTheme(darkTheme = true) {
        BackgroundShowcaseScreen()
    }
}