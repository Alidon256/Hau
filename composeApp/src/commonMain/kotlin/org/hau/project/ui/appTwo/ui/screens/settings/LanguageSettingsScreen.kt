package org.hau.project.ui.appTwo.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.hau.project.ui.appTwo.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LanguageSettingsScreen(onBack: () -> Unit) {
    val languages = remember {
        listOf(
            "English" to "(device's language)",
            "Kiswahili" to "Swahili",
            "Afrikaans" to "Afrikaans",
            "Shqip" to "Albanian",
            "አማርኛ" to "Amharic",
            "العربية" to "Arabic",
            "Azərbaycan dili" to "Azerbaijani",
            "বাংলা" to "Bangla",
        )
    }
    var selectedLanguage by remember { mutableStateOf("English") }

    Scaffold(
        topBar = { SettingsTopAppBar("App language", onBack) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(languages, key = { it.first }) { (nativeName, englishName) ->
                LanguageItem(
                    nativeName = nativeName,
                    englishName = englishName,
                    isSelected = selectedLanguage == nativeName,
                    onSelect = { selectedLanguage = nativeName }
                )
            }
        }
    }
}

@Composable
private fun LanguageItem(
    nativeName: String,
    englishName: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = nativeName,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
            Text(
                text = englishName,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(name = "Language Settings (Dark)", showBackground = true)
@Composable
private fun LanguageSettingsScreenDarkPreview() {
    AppTheme(useDarkTheme = true) {
        LanguageSettingsScreen {}
    }
}

@Preview(name = "Language Settings (Light)", showBackground = true)
@Composable
private fun LanguageSettingsScreenLightPreview() {
    AppTheme(useDarkTheme = false) {
        LanguageSettingsScreen {}
    }
}
