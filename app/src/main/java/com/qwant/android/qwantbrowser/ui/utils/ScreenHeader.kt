package com.qwant.android.qwantbrowser.ui.utils

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme

@Composable
fun ScreenHeader(
    title: String,
    icon: ImageVector? = null,
    actions: @Composable () -> Unit = {}
) {
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        IconButton(onClick = { backPressedDispatcher?.onBackPressed() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        if (icon != null) {
            Icon(icon, contentDescription = title, modifier = Modifier.padding(end = 4.dp))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(2f)
        )

        actions()
    }
}


@Preview
@Composable fun ScreenHeaderPreviewTitleOnly() {
    QwantBrowserTheme {
        ScreenHeader(
            title = "Coucou"
        )
    }
}

@Preview
@Composable fun ScreenHeaderPreviewTitleAndIcon() {
    QwantBrowserTheme {
        ScreenHeader(
            title = "Coucou",
            icon = Icons.Default.PointOfSale
        )
    }
}

@Preview
@Composable fun ScreenHeaderPreviewFull() {
    QwantBrowserTheme {
        ScreenHeader(
            title = "Coucou",
            icon = Icons.Default.PointOfSale,
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Terrain, contentDescription = "test")
                }
            }
        )
    }
}