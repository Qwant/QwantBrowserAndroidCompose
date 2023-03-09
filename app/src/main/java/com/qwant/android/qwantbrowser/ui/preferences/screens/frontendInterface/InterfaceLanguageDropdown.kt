package com.qwant.android.qwantbrowser.ui.preferences.screens.frontendInterface

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.widgets.DropdownSelector
import com.qwant.android.qwantbrowser.ui.widgets.DropdownSelectorItem

@Composable
fun InterfaceLanguageDropdown(
    value: String,
    onValueChange: (String) -> Unit
) {
    val availableInterfaceLanguages = listOf(
        DropdownSelectorItem("en", R.string.available_language_english),
        DropdownSelectorItem("fr", R.string.available_language_french),
        DropdownSelectorItem("de", R.string.available_language_german),
        DropdownSelectorItem("it", R.string.available_language_italian),
        DropdownSelectorItem("es", R.string.available_language_spanish)
    )
    val selectedInterfaceLanguage by remember(value) { derivedStateOf {
        availableInterfaceLanguages.find { it.value == value } ?: availableInterfaceLanguages[0]
    } }

    DropdownSelector(
        items = availableInterfaceLanguages,
        selectedItem = selectedInterfaceLanguage,
        onItemSelected = { onValueChange(it.value) },
        modifier = Modifier.fillMaxWidth()
    )
}