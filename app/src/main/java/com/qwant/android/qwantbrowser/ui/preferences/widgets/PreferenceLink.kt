package com.qwant.android.qwantbrowser.ui.preferences.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.preferences.PreferenceNavDestination

@Composable
fun PreferenceLink(
    destination: PreferenceNavDestination,
    onNavigateTo: (destination: PreferenceNavDestination) -> Unit
) {
    Link(
        text = stringResource(id = destination.label),
        icon = destination.icon,
        onClicked = { onNavigateTo(destination) },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    )
}