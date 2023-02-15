package com.qwant.android.qwantbrowser.ui.preferences.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainPreferencesScreen(
    onFrontendClicked: () -> Unit = {},
    onEngineClicked: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Frontend",
            modifier = Modifier
                .clickable { onFrontendClicked() }
                .fillMaxWidth()
                .height(48.dp)
        )
        Text(
            text = "Engine",
            modifier = Modifier
                .clickable { onEngineClicked() }
                .fillMaxWidth()
                .height(48.dp)
        )
    }
}