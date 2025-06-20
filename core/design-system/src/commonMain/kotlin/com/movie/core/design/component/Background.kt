package com.movie.core.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.movie.core.design.theme.LocalGradientColors

/**
 * The main background for the app.
 * Uses the theme's gradient colors to create a smooth background.
 *
 * @param modifier Modifier to be applied to the background.
 * @param content The background content.
 */
@Composable
fun MovieBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val gradientColors = LocalGradientColors.current
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        gradientColors.top,
                        gradientColors.bottom,
                    ),
                ),
            ),
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}

/**
 * A gradient background with custom colors.
 *
 * @param modifier Modifier to be applied to the background.
 * @param topColor The color at the top of the gradient.
 * @param bottomColor The color at the bottom of the gradient.
 * @param content The background content.
 */
@Composable
fun MovieGradientBackground(
    modifier: Modifier = Modifier,
    topColor: Color = Color.Transparent,
    bottomColor: Color = Color.Transparent,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(topColor, bottomColor),
                ),
            ),
    ) {
        content()
    }
}

/**
 * A simple surface background using Material theme colors.
 *
 * @param modifier Modifier to be applied to the background.
 * @param color The background color. Defaults to MaterialTheme.colorScheme.background.
 * @param content The background content.
 */
@Composable
fun MovieSurfaceBackground(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background,
    content: @Composable () -> Unit,
) {
    Surface(
        color = color,
        modifier = modifier.fillMaxSize(),
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}

/**
 * A cinematic gradient background with predefined movie-themed colors.
 *
 * @param modifier Modifier to be applied to the background.
 * @param content The background content.
 */
@Composable
fun MovieCinematicBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val gradientColors = LocalGradientColors.current
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0f to gradientColors.container,
                    0.5f to MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                    1f to gradientColors.container,
                ),
            ),
    ) {
        content()
    }
}

/**
 * An overlay gradient that can be placed on top of content (like movie posters).
 * Useful for ensuring text readability over images.
 *
 * @param modifier Modifier to be applied to the overlay.
 * @param gradientColors The colors for the gradient. Defaults to transparent to black.
 */
@Composable
fun MovieGradientOverlay(
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(
        Color.Transparent,
        Color.Black.copy(alpha = 0.7f),
    ),
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = gradientColors),
            ),
    )
}

/**
 * A radial gradient background for special screens or effects.
 *
 * @param modifier Modifier to be applied to the background.
 * @param centerColor The color at the center of the gradient.
 * @param edgeColor The color at the edges of the gradient.
 * @param content The background content.
 */
@Composable
fun MovieRadialBackground(
    modifier: Modifier = Modifier,
    centerColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    edgeColor: Color = MaterialTheme.colorScheme.background,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(centerColor, edgeColor),
                ),
            ),
    ) {
        content()
    }
}