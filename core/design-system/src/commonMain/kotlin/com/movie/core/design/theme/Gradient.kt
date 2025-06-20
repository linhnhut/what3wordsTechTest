package com.movie.core.design.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * A class to model background gradient color values for Movie.
 */
data class GradientColors(
    val top: Color = Color.Unspecified,
    val bottom: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
)

/**
 * A composition local for [GradientColors].
 */
val LocalGradientColors = staticCompositionLocalOf { GradientColors() }

/**
 * The Material gradient colors for Movie.
 */
val MaterialTheme.gradientColors: GradientColors
    @Composable
    @ReadOnlyComposable
    get() = LocalGradientColors.current

/**
 * Creates a vertical gradient brush.
 */
@Composable
fun GradientColors.verticalGradient(): Brush = Brush.verticalGradient(
    colors = listOf(top, bottom),
)

/**
 * Creates a horizontal gradient brush.
 */
@Composable
fun GradientColors.horizontalGradient(): Brush = Brush.horizontalGradient(
    colors = listOf(top, bottom),
)