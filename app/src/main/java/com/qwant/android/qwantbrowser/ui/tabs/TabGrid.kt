package com.qwant.android.qwantbrowser.ui.tabs

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mozilla.components.browser.state.state.TabSessionState
import mozilla.components.browser.thumbnails.storage.ThumbnailStorage
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.browser.ToolbarAction
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.browser.icons.compose.Loader
import mozilla.components.browser.icons.compose.Placeholder
import mozilla.components.browser.icons.compose.WithIcon

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabGrid(
    tabs: List<TabSessionState>,
    selectedTabId: String?,
    thumbnailStorage: ThumbnailStorage,
    browserIcons: BrowserIcons,
    onTabSelected: (tab: TabSessionState) -> Unit,
    onTabDeleted: (tab: TabSessionState) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        items(tabs, key = { it.id }) { tab ->
            TabCard(
                tab = tab,
                selected = tab.id == selectedTabId,
                thumbnailStorage = thumbnailStorage,
                browserIcons = browserIcons,
                onSelected = onTabSelected,
                onDeleted = onTabDeleted,
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

@Composable
fun TabCard(
    tab: TabSessionState,
    selected: Boolean,
    thumbnailStorage: ThumbnailStorage,
    browserIcons: BrowserIcons,
    onSelected: (tab: TabSessionState) -> Unit,
    onDeleted: (tab: TabSessionState) -> Unit,
    modifier: Modifier = Modifier
) {
    var deleting by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (deleting) 0f else 1f, finishedListener = {
        if (it == 0f) {
            onDeleted(tab)
            deleting = false
        }
    })

    Column(modifier = modifier
        .scale(scale)
        .clip(MaterialTheme.shapes.extraSmall)
        .clickable { onSelected(tab) }
        .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer)
    ) {
        CompositionLocalProvider(LocalContentColor provides
            if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer)
        {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.padding(start = 10.dp).size(18.dp)) {
                    tab.content.icon?.let {
                        Image(bitmap = it.asImageBitmap(), contentDescription = "icon")
                    } ?: browserIcons.Loader(url = tab.content.url, isPrivate = tab.content.private) {
                        WithIcon {
                            Image(painter = it.painter, contentDescription = "icon")
                        }
                        Placeholder {
                            Image(painter = painterResource(id = R.drawable.icons_shuffle), contentDescription = "icon")
                        }
                    }
                }

                Text(
                    text = tab.content.title,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(2f)
                )
                ToolbarAction(onClick = { deleting = true }) { // TODO rename ToolbarAction to "SmallButton" and put it in widgets
                    Icon(painterResource(id = R.drawable.icons_close), contentDescription = "icon")
                }
            }
            Box(modifier = Modifier
                .defaultMinSize(150.dp, 175.dp)
                .padding(top = 0.dp, bottom = 4.dp, start = 4.dp, end = 4.dp)
                .height(175.dp) // TODO height relative to calculated width ?
                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 12.dp, bottomEnd = 12.dp)),
                propagateMinConstraints = true
            ) {
                // TODO size equal to calculated width
                TabThumbnail(tabId = tab.id, size = 200.dp, thumbnailStorage = thumbnailStorage)
            }
        }
    }
}