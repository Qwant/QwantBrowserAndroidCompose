package com.example.qwantbrowsercompose.ui.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mozilla.components.browser.state.state.TabSessionState
import com.example.qwantbrowsercompose.R

// import mozilla.components.browser.thumbnails.storage.ThumbnailStorage

@Composable
fun TabRow(
    tab: TabSessionState,
    selected: Boolean,
    // thumbnailStorage: ThumbnailStorage,
    onSelected: (tab: TabSessionState) -> Unit,
    onDeleted: (tab: TabSessionState) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
        .clickable { onSelected(tab) }
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(90.dp)
                .height(70.dp)
                .padding(start = 12.dp, top = 4.dp, bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                colorFilter = ColorFilter.tint(Color.Gray),
                contentDescription = "Thumbnail placeholder",
                contentScale = ContentScale.None
            )
            TabThumbnail(tab.id/*, 90.dp , thumbnailStorage */)
        }

        Box(modifier = Modifier
            .weight(1.0f)
            .height(70.dp)
            .padding(start = 12.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                tab.content.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Text(
                tab.content.url,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }

        Box(modifier = Modifier
            .width(44.dp)
            .height(70.dp)
            .padding(end = 12.dp)
            .clickable { onDeleted(tab) }
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Delete",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}