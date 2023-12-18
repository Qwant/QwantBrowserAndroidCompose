package com.qwant.android.qwantbrowser.ui.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme

@Composable
fun YesNoDialog(
    onDismissRequest: () -> Unit,
    onYes: () -> Unit,
    onNo: () -> Unit,
    title: String? = null,
    description: String? = null,
    @DrawableRes icon: Int? = null,
    yesText: String = stringResource(id = R.string.ok),
    noText: String = stringResource(id = R.string.cancel),
    additionalContent: (@Composable ColumnScope.() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = { Button(onClick = onYes) { Text(text = yesText) }},
        dismissButton = { TextButton(onClick = onNo) { Text(text = noText) }},
        icon = { icon?.let { Icon(painterResource(id = it), contentDescription = "icon") } },
        title = { title?.let { Text(text = it, modifier = Modifier.fillMaxWidth(), textAlign = if (icon != null) TextAlign.Center else TextAlign.Start) } },
        text = {
            if (additionalContent != null) {
                Column {
                    val scrollState = rememberScrollState()
                    if (scrollState.canScrollBackward) {
                        HorizontalDivider()
                    }
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        description?.let { Text(text = it) }
                        additionalContent()
                    }
                    if (scrollState.canScrollForward) {
                        HorizontalDivider()
                    }
                }
            } else {
                description?.let { Text(text = it) }
            }
        },
        shape = MaterialTheme.shapes.extraSmall,
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.extraSmall)
    )
}

@Preview(name = "Complete", showSystemUi = true)
@Composable
fun YesNoDialogPreviewComplete() {
    QwantBrowserTheme(
        darkTheme = true,
        privacy = false
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            YesNoDialog(
                onDismissRequest = {},
                onYes = {},
                onNo = {},
                title = "Title",
                description = "Description",
                icon = R.drawable.icons_paste,
                additionalContent = {
                    TextField(value = "additional content", onValueChange = {})
                }
            )
        }
    }
}

@Preview(name = "Text only", showSystemUi = true)
@Composable
fun YesNoDialogPreviewTextOnly() {
    QwantBrowserTheme(
        darkTheme = false,
        privacy = false
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            YesNoDialog(
                onDismissRequest = {},
                onYes = {},
                onNo = {},
                description = stringResource(id = R.string.cleardata_confirm_text)// "Description"
            )
        }
    }
}

@Preview(name = "Icon and text", showSystemUi = true)
@Composable
fun YesNoDialogPreviewIconAndText() {
    QwantBrowserTheme(
        darkTheme = false,
        privacy = false
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            YesNoDialog(
                onDismissRequest = {},
                onYes = {},
                onNo = {},
                icon = R.drawable.icons_paste,
                description = stringResource(id = R.string.cleardata_confirm_text)// "Description"
            )
        }
    }
}

@Preview(name = "Title and text", showSystemUi = true)
@Composable
fun YesNoDialogPreviewTitleAndText() {
    QwantBrowserTheme(
        darkTheme = false,
        privacy = false
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            YesNoDialog(
                onDismissRequest = {},
                onYes = {},
                onNo = {},
                title = "Titre",
                description = "Description"
            )
        }
    }
}

@Preview(name = "Overflow", showSystemUi = true)
@Composable
fun YesNoDialogPreviewOverflow() {
    QwantBrowserTheme(
        darkTheme = false,
        privacy = false
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            YesNoDialog(
                onDismissRequest = {},
                onYes = {},
                onNo = {},
                title = "Title",
                description = "Description",
                icon = R.drawable.icons_paste,
                additionalContent = {
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    Text("Additional content")
                    TextField(value = "aaaza", onValueChange = {})
                }
            )
        }
    }
}