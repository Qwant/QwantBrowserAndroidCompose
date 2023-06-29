package com.qwant.android.qwantbrowser.ui.preferences.widgets

import androidx.annotation.StringRes
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable

@Composable
fun PreferenceToggle(
    @StringRes label: Int,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
) {
    PreferenceRow(
        label = label,
        icon = { Switch(checked = value, onCheckedChange = onValueChange) }
    )
}