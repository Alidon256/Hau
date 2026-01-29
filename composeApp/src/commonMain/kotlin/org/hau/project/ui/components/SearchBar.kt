package org.hau.project.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A dynamic search bar component that can expand from a simple row to a full-width text input.
 *
 * Features:
 * - Smooth expansion animation using [animateDpAsState].
 * - Focus management with [FocusRequester].
 * - Keyboard integration with [LocalSoftwareKeyboardController] and [LocalFocusManager].
 * - Crossfade animation for the search/back icon.
 *
 * @param query The current search text.
 * @param onQueryChange Callback for when the search text changes.
 * @param onSearch Callback for when the user triggers the search action from the keyboard.
 * @param isExpanded Whether the search bar is currently in its expanded (input) state.
 * @param onToggleExpanded Callback to switch between collapsed and expanded states.
 * @param modifier Custom modifier for the search bar container.
 * @param placeholderText The text to display when the search bar is empty or collapsed.
 */
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Search..."
) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val searchBarHorizontalPadding by animateDpAsState(
        targetValue = if (isExpanded) 0.dp else 16.dp,
        animationSpec = tween(durationMillis = 300),
        label = "SearchBarPadding"
    )
    val searchBarElevation by animateDpAsState(
        targetValue = if (isExpanded) 2.dp else 0.dp,
        animationSpec = tween(durationMillis = 300),
        label = "SearchBarElevation"
    )

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            focusRequester.requestFocus()
            keyboardController?.show()
        } else {
            keyboardController?.hide()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = searchBarHorizontalPadding)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
            shape = RoundedCornerShape(if (isExpanded) 0.dp else 28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = searchBarElevation),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .clickable(
                        enabled = !isExpanded,
                        onClick = {
                            if (!isExpanded) onToggleExpanded()
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onToggleExpanded) {
                    Crossfade(
                        targetState = isExpanded,
                        animationSpec = tween(300),
                        label = "SearchIconCrossfade"
                    ) { expanded ->
                        Icon(
                            imageVector = if (expanded) Icons.AutoMirrored.Filled.ArrowBack else Icons.Default.Search,
                            contentDescription = if (expanded) "Collapse Search" else "Expand Search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Box(modifier = Modifier.weight(1f)) {
                    if (isExpanded) {
                        TextField(
                            value = query,
                            onValueChange = onQueryChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                                .onFocusChanged { focusState ->
                                    if (!focusState.isFocused && isExpanded) {
                                        // Optional: handle focus loss
                                    }
                                },
                            placeholder = {
                                Text(
                                    placeholderText,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    onSearch(query)
                                    focusManager.clearFocus()
                                }
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = MaterialTheme.colorScheme.primary
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    } else {
                        Text(
                            text = placeholderText,
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp)
                                .align(Alignment.CenterStart),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                if (isExpanded && query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear Query",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else if (isExpanded && query.isEmpty()) {
                    Spacer(Modifier.width(48.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchBarPreviewCollapsed() {
    MaterialTheme {
        var isExpanded by remember { mutableStateOf(false) }
        var query by remember { mutableStateOf("") }
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {},
            isExpanded = isExpanded,
            onToggleExpanded = { isExpanded = !isExpanded }
        )
    }
}

@Preview
@Composable
private fun SearchBarPreviewExpandedEmpty() {
    MaterialTheme {
        var isExpanded by remember { mutableStateOf(true) }
        var query by remember { mutableStateOf("") }
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {},
            isExpanded = isExpanded,
            onToggleExpanded = { isExpanded = !isExpanded }
        )
    }
}

@Preview
@Composable
private fun SearchBarPreviewExpandedWithQuery() {
    MaterialTheme {
        var isExpanded by remember { mutableStateOf(true) }
        var query by remember { mutableStateOf("Compose") }
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {},
            isExpanded = isExpanded,
            onToggleExpanded = { isExpanded = !isExpanded }
        )
    }
}
