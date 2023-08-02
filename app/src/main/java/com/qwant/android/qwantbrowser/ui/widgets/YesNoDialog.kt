package com.qwant.android.qwantbrowser.ui.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun YesNoDialog(
    onDismissRequest: () -> Unit,
    onYes: () -> Unit,
    onNo: () -> Unit,
    title: String? = null,
    description: String? = null,
    @DrawableRes icon: Int? = null,
    yesText: String = "Yes", // TODO translations
    noText: String = "No", // TODO translations
    additionalContent: @Composable ColumnScope.() -> Unit = {}
) {
    // TODO should use `Layout` to compose YesNoDialog instead of paddings
    val density = LocalDensity.current
    var topSize by remember { mutableStateOf(0.dp) }
    var bottomSize by remember { mutableStateOf(0.dp) }

    val scrollState = rememberScrollState()

    Dialog(onDismissRequest = onDismissRequest) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clip(MaterialTheme.shapes.extraSmall)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.extraSmall)
            .padding(24.dp)
        ) {
            Column(modifier = Modifier
                .align(Alignment.TopCenter)
                .onSizeChanged {
                    with(density) {
                        topSize = it.height.toDp()
                    }
                }
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                ) {
                    icon?.let {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = "dialog icon"
                        )
                    }

                    title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                if (scrollState.canScrollBackward) {
                    Divider()
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(top = topSize, bottom = bottomSize)
                    .verticalScroll(scrollState)
            ) {
                description?.let {
                    Text(text = it)
                }

                additionalContent()
            }

            Column(modifier = Modifier
                .align(Alignment.BottomEnd)
                .onSizeChanged {
                    with(density) {
                        bottomSize = it.height.toDp()
                    }
                }
            ) {
                if (scrollState.canScrollForward) {
                    Divider()
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 16.dp)
                ) {
                    TextButton(onClick = onNo) {
                        Text(text = noText)
                    }
                    Button(onClick = onYes) {
                        Text(text = yesText)
                    }
                }
            }

        }

        /* Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.extraSmall)
                .padding(24.dp)
        ) {
            icon?.let {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "dialog icon"
                )
            }

            title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                description?.let {
                    Text(text = it)
                }

                additionalContent()
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp)
            ) {
                TextButton(onClick = onNo) {
                    Text(text = noText)
                }
                Button(onClick = onYes) {
                    Text(text = yesText)
                }
            }
        } */
    }
}