package com.qwant.android.qwantbrowser.ui.widgets

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.extraSmall)
                .background(MaterialTheme.colorScheme.tertiaryContainer)
                .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline), MaterialTheme.shapes.extraSmall)
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

            description?.let {
                Text(text = it)
            }

            additionalContent()

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.align(Alignment.End).padding(top = 16.dp)
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
}