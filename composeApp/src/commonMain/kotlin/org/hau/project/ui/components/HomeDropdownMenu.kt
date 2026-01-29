package org.hau.project.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.hau.project.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A dropdown menu specific to the Home screen for managing global application options.
 *
 * [Note: This is currently a skeletal implementation provided for future expansion.]
 *
 * @param onClickMenuItem Callback triggered when a menu selection is made.
 * @param isExpanded Controls whether the menu is visible or hidden.
 */
@Composable
fun HomeDropdownMenu(
    onClickMenuItem: () -> Unit,
    isExpanded: Boolean
) {
    // Component implementation pending logic for specific menu items
}

@Composable
@Preview(showBackground = true, widthDp = 400, heightDp = 600)
fun HomeDropdownMenuLightPreview() {
    AppTheme(useDarkTheme = false) {
        Surface(modifier = Modifier.padding(16.dp)) {
            HomeDropdownMenu(
                onClickMenuItem = {},
                isExpanded = true
            )
        }
    }
}

@Composable
@Preview(showBackground = true, widthDp = 400, heightDp = 600)
fun HomeDropdownMenuDarkPreview() {
    AppTheme(useDarkTheme = true) {
        Surface(modifier = Modifier.padding(16.dp)) {
            HomeDropdownMenu(
                onClickMenuItem = {},
                isExpanded = true
            )
        }
    }
}