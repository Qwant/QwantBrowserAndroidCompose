package com.qwant.android.qwantbrowser.ui.preferences.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.preferences.PreferenceNavDestination
import com.qwant.android.qwantbrowser.ui.utils.ScreenHeader

@Composable
fun BasePreferenceScreen(
    destination: PreferenceNavDestination,
    content: @Composable () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            title = stringResource(id = destination.label)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            content()
        }
    }
}