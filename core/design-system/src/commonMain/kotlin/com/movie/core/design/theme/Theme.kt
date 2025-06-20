package com.movie.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

/**
 * Light default theme color scheme
 */
val LightDefaultColorScheme = lightColorScheme(
    primary = CinemaRed40,
    onPrimary = Color.White,
    primaryContainer = CinemaRed90,
    onPrimaryContainer = CinemaRed10,
    secondary = Gold40,
    onSecondary = Color.White,
    secondaryContainer = Gold90,
    onSecondaryContainer = Gold10,
    tertiary = CinemaBlue40,
    onTertiary = Color.White,
    tertiaryContainer = CinemaBlue90,
    onTertiaryContainer = CinemaBlue10,
    error = Error40,
    onError = Color.White,
    errorContainer = Error90,
    onErrorContainer = Error10,
    background = DarkGray99,
    onBackground = DarkGray10,
    surface = DarkGray99,
    onSurface = DarkGray10,
    surfaceVariant = NeutralVariant95,
    onSurfaceVariant = DarkGray30,
    outline = DarkGray50,
    outlineVariant = DarkGray80,
    scrim = DarkGray10,
    inverseSurface = DarkGray20,
    inverseOnSurface = DarkGray95,
    inversePrimary = CinemaRed80,
    surfaceDim = DarkGray90,
    surfaceBright = DarkGray99,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = DarkGray95,
    surfaceContainer = DarkGray90,
    surfaceContainerHigh = NeutralVariant90,
    surfaceContainerHighest = DarkGray80,
)

/**
 * Dark default theme color scheme
 */
val DarkDefaultColorScheme = darkColorScheme(
    primary = CinemaRed80,
    onPrimary = CinemaRed20,
    primaryContainer = CinemaRed30,
    onPrimaryContainer = CinemaRed90,
    secondary = Gold80,
    onSecondary = Gold20,
    secondaryContainer = Gold30,
    onSecondaryContainer = Gold90,
    tertiary = CinemaBlue80,
    onTertiary = CinemaBlue20,
    tertiaryContainer = CinemaBlue30,
    onTertiaryContainer = CinemaBlue90,
    error = Error80,
    onError = Error20,
    errorContainer = Error30,
    onErrorContainer = Error90,
    background = DarkGray10,
    onBackground = DarkGray90,
    surface = DarkGray10,
    onSurface = DarkGray90,
    surfaceVariant = NeutralVariant20,
    onSurfaceVariant = DarkGray80,
    outline = DarkGray60,
    outlineVariant = DarkGray30,
    scrim = DarkGray10,
    inverseSurface = DarkGray90,
    inverseOnSurface = DarkGray20,
    inversePrimary = CinemaRed40,
    surfaceDim = DarkGray10,
    surfaceBright = NeutralVariant30,
    surfaceContainerLowest = DarkGray10,
    surfaceContainerLow = NeutralVariant10,
    surfaceContainer = NeutralVariant20,
    surfaceContainerHigh = NeutralVariant30,
    surfaceContainerHighest = DarkGray30,
)

/**
 * Light Android theme color scheme
 */
val LightAndroidColorScheme = lightColorScheme(
    primary = Gold40,
    onPrimary = Color.White,
    primaryContainer = Gold90,
    onPrimaryContainer = Gold10,
    secondary = DarkGray40,
    onSecondary = Color.White,
    secondaryContainer = DarkGray90,
    onSecondaryContainer = DarkGray10,
    tertiary = CinemaBlue40,
    onTertiary = Color.White,
    tertiaryContainer = CinemaBlue90,
    onTertiaryContainer = CinemaBlue10,
    error = Error40,
    onError = Color.White,
    errorContainer = Error90,
    onErrorContainer = Error10,
    background = DarkGray99,
    onBackground = DarkGray10,
    surface = DarkGray99,
    onSurface = DarkGray10,
    surfaceVariant = NeutralVariant95,
    onSurfaceVariant = DarkGray30,
    outline = DarkGray50,
    outlineVariant = DarkGray80,
    scrim = DarkGray10,
    inverseSurface = DarkGray20,
    inverseOnSurface = DarkGray95,
    inversePrimary = Gold80,
    surfaceDim = DarkGray90,
    surfaceBright = DarkGray99,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = DarkGray95,
    surfaceContainer = DarkGray90,
    surfaceContainerHigh = NeutralVariant90,
    surfaceContainerHighest = DarkGray80,
)

/**
 * Dark Android theme color scheme
 */
val DarkAndroidColorScheme = darkColorScheme(
    primary = Gold80,
    onPrimary = Gold20,
    primaryContainer = Gold30,
    onPrimaryContainer = Gold90,
    secondary = DarkGray80,
    onSecondary = DarkGray20,
    secondaryContainer = DarkGray30,
    onSecondaryContainer = DarkGray90,
    tertiary = CinemaBlue80,
    onTertiary = CinemaBlue20,
    tertiaryContainer = CinemaBlue30,
    onTertiaryContainer = CinemaBlue90,
    error = Error80,
    onError = Error20,
    errorContainer = Error30,
    onErrorContainer = Error90,
    background = DarkGray10,
    onBackground = DarkGray90,
    surface = DarkGray10,
    onSurface = DarkGray90,
    surfaceVariant = NeutralVariant20,
    onSurfaceVariant = DarkGray80,
    outline = DarkGray60,
    outlineVariant = DarkGray30,
    scrim = DarkGray10,
    inverseSurface = DarkGray90,
    inverseOnSurface = DarkGray20,
    inversePrimary = Gold40,
    surfaceDim = DarkGray10,
    surfaceBright = NeutralVariant30,
    surfaceContainerLowest = DarkGray10,
    surfaceContainerLow = NeutralVariant10,
    surfaceContainer = NeutralVariant20,
    surfaceContainerHigh = NeutralVariant30,
    surfaceContainerHighest = DarkGray30,
)

/**
 * Movie theme.
 *
 * @param darkTheme Whether the theme should use dark colors.
 * @param androidTheme Whether to use Android theme color scheme.
 * @param disableDynamicTheming If `true`, disables the use of dynamic theming, even when it is
 * supported. This parameter has no effect if [androidTheme] is `false`.
 */
@Composable
fun MovieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    // Color scheme selection logic
    val colorScheme = when {
        androidTheme -> if (darkTheme) DarkAndroidColorScheme else LightAndroidColorScheme
        darkTheme -> DarkDefaultColorScheme
        else -> LightDefaultColorScheme
    }

    // Background gradient for branded surfaces
    val defaultGradientColors = GradientColors(
        top = colorScheme.surface,
        bottom = colorScheme.surface,
        container = colorScheme.surface,
    )
    
    val brandedGradientColors = when {
        darkTheme -> GradientColors(
            top = DarkGray10,
            bottom = DarkGray20,
            container = DarkGray10,
        )
        else -> GradientColors(
            top = DarkGray99,
            bottom = DarkGray95,
            container = DarkGray99,
        )
    }

    // Background theme
    val backgroundTheme = when {
        androidTheme -> defaultGradientColors
        else -> brandedGradientColors
    }

    CompositionLocalProvider(
        LocalGradientColors provides backgroundTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = MovieTypography,
            content = content,
        )
    }
}