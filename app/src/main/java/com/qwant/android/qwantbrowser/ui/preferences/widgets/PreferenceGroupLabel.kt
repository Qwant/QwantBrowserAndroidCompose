package com.qwant.android.qwantbrowser.ui.preferences.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreferenceGroupLabel(
    @StringRes label: Int
) {
    Column {
        Divider()
        Text(
            text = stringResource(label),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}