package com.movie.core.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Movie loading wheel component.
 *
 * @param modifier Modifier to be applied to the loading wheel.
 * @param color The color of the loading wheel.
 * @param size The size of the loading wheel.
 * @param strokeWidth The stroke width of the loading wheel.
 */
@Composable
fun MovieLoadingWheel(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 40.dp,
    strokeWidth: Dp = 4.dp,
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = color,
        strokeWidth = strokeWidth,
    )
}

/**
 * Movie loading wheel in a box for centering.
 *
 * @param modifier Modifier to be applied to the box.
 * @param color The color of the loading wheel.
 * @param size The size of the loading wheel.
 * @param strokeWidth The stroke width of the loading wheel.
 */
@Composable
fun MovieLoadingWheelBox(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 40.dp,
    strokeWidth: Dp = 4.dp,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        MovieLoadingWheel(
            color = color,
            size = size,
            strokeWidth = strokeWidth,
        )
    }
}