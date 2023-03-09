package com.qwant.android.qwantbrowser.ui.preferences.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun PreferenceToggle(
    label: String,
    icon: Painter,
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary)
        Text(text = label, modifier = Modifier.weight(2f).padding(horizontal = 8.dp))
        Switch(checked = value, onCheckedChange = onValueChange)
    }
}