package com.movie.core.design.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Movie card component following Material 3 design.
 *
 * @param onClick Optional click handler for the card.
 * @param modifier Modifier to be applied to the card.
 * @param shape The shape of the card.
 * @param containerColor The background color of the card.
 * @param contentColor The preferred content color for the card.
 * @param tonalElevation The tonal elevation of the card.
 * @param shadowElevation The shadow elevation of the card.
 * @param border Optional border to draw on the card.
 * @param content The card content.
 */
@Composable
fun MovieCard(
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    tonalElevation: Dp = 1.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = tonalElevation,
                pressedElevation = shadowElevation,
            ),
            border = border,
            content = content,
        )
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = tonalElevation,
            ),
            border = border,
            content = content,
        )
    }
}