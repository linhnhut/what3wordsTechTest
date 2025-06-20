package com.movie.core.design.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Movie icon button component.
 *
 * @param onClick Called when the button is clicked.
 * @param icon The icon to display.
 * @param modifier Modifier to be applied to the button.
 * @param enabled Controls the enabled state of the button.
 * @param iconSize The size of the icon.
 * @param tint The tint color of the icon.
 * @param contentDescription The content description for accessibility.
 */
@Composable
fun MovieIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconSize: Dp = 24.dp,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    contentDescription: String? = null,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = tint,
        ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            tint = tint,
        )
    }
}