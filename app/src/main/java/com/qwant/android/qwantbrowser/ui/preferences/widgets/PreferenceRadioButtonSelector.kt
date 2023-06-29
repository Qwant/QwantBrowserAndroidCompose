package com.qwant.android.qwantbrowser.ui.preferences.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

data class RadioButtonOption<T>(
    val value: T,
    @StringRes val label: Int,
    val icon: Painter? = null
)

@Composable
fun <T> PreferenceRadioButtonSelector(options: List<RadioButtonOption<T>>, value: T, onValueChange: (T) -> Unit) {
    Column {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onValueChange(option.value) }
            ) {
                RadioButton(
                    selected = (value == option.value),
                    onClick = { onValueChange(option.value) }
                )
                Text(
                    text = stringResource(option.label),
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
    }
}