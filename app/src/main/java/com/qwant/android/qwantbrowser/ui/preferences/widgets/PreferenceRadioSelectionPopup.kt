package com.qwant.android.qwantbrowser.ui.preferences.widgets

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

@Composable
fun <T> PreferenceRadioSelectionPopup(
    @StringRes label: Int,
    options: Map<T, Int>,
    value: T,
    onValueChange: (T) -> Unit
) {
    PreferenceSelectionPopup(
        label = label,
        description = options[value],
        popupContent = { PreferenceRadioButtonSelector(
            options = options.map { RadioButtonOption(it.key, it.value) },
            value = value,
            onValueChange = onValueChange
        )}
    )
}