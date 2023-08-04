package com.qwant.android.qwantbrowser.ui.widgets

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.DrawableRes
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme

@Composable
fun ScreenHeader(
    title: String,
    @DrawableRes icon: Int? = null,
    scrollableState: ScrollableState? = null,
    actions: @Composable () -> Unit = {}
) {
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSecondaryContainer) {
                IconButton(onClick = { backPressedDispatcher?.onBackPressed() }) {
                    Icon(painterResource(id = R.drawable.icons_arrow_backward), contentDescription = "Back")
                }

                if (icon != null) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = title,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(2f)
                )

                actions()
            }
        }
        if (scrollableState?.canScrollBackward == true) {
            Divider()
        }
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

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable fun ScreenHeaderPreviewTitleOnlyNight() {
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
            icon = R.drawable.icons_lock
        )
    }
}

@Preview
@Composable fun ScreenHeaderPreviewFull() {
    QwantBrowserTheme {
        ScreenHeader(
            title = "Coucou",
            icon = R.drawable.icons_lock,
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        painterResource(id = R.drawable.icons_privacy_mask), contentDescription = "test")
                }
            }
        )
    }
}