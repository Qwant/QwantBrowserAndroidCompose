package com.qwant.android.qwantbrowser.ui.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mozilla.components.browser.state.state.TabSessionState
import mozilla.components.browser.thumbnails.storage.ThumbnailStorage
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.toCleanHost

@Composable
fun TabRow(
    tab: TabSessionState,
    selected: Boolean,
    thumbnailStorage: ThumbnailStorage,
    onSelected: (tab: TabSessionState) -> Unit,
    onDeleted: (tab: TabSessionState) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(if (selected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface)
            .clickable { onSelected(tab) }
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(90.dp)
                .height(70.dp)
                .padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
        ) {
            TabThumbnail(tab.id, 90.dp, thumbnailStorage)
        }

        Column(modifier = Modifier
            .weight(2f)
            .padding(start = 12.dp)) {
            Text(
                tab.content.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                lineHeight = 20.sp
            )
            Text(
                tab.content.url.toCleanHost(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(onClick = { onDeleted(tab) }) {
            Icon(painter = painterResource(id = R.drawable.icons_close), contentDescription = "Delete")
        }
    }
}