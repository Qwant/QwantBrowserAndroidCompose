package com.qwant.android.qwantbrowser.ui.preferences.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Kitesurfing
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme

@Composable
fun Link(
    text: String,
    icon: ImageVector,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClicked() }
            .padding(4.dp)
    ){
        Icon(icon, contentDescription = text)
        Text(
            text = text,
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp)
        )
        Icon(Icons.Default.ArrowRight, contentDescription = "arrow")
    }

}

@Preview
@Composable
fun LinkPreview() {
    QwantBrowserTheme {
        Link(
            text = "test label",
            icon = Icons.Default.Kitesurfing,
            onClicked = {}
        )
    }
}