package com.qwant.android.qwantbrowser.ui.bookmarks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.browser.suggest.WebsiteRow
import com.qwant.android.qwantbrowser.ui.browser.suggest.WebsiteRowWithIcon
import com.qwant.android.qwantbrowser.ui.theme.GreenUrl
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import com.qwant.android.qwantbrowser.ui.widgets.UrlIcon
import mozilla.components.browser.icons.compose.Loader
import mozilla.components.browser.icons.compose.Placeholder
import mozilla.components.browser.icons.compose.WithIcon
import org.mozilla.reference.browser.storage.BookmarkItemV2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarksList(
    viewModel: BookmarksScreenViewModel,
    onBrowse: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState()
) {
    var editItem: BookmarkItemV2? by remember { mutableStateOf(null) }
    var moveItem: BookmarkItemV2? by remember { mutableStateOf(null) }

    LazyColumn(state = lazyListState) {
        viewModel.currentBookmarks.forEach { bookmark ->
            val itemMenu: @Composable RowScope.() -> Unit = {
                Box {
                    var showMenu by remember { mutableStateOf(false) }

                    IconButton(onClick = { showMenu = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icons_more_vertical),
                            contentDescription = "more"
                        )
                    }
                    Dropdown(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        if (bookmark.type == BookmarkItemV2.BookmarkType.BOOKMARK) {
                            DropdownMenuItem(
                                text = { Text(text = "Open in new tab") },
                                leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                                onClick = {
                                    viewModel.openBookmarkTab(bookmark)
                                    onBrowse()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Open in new private tab") },
                                leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                                onClick = {
                                    viewModel.openBookmarkTab(bookmark, private = true)
                                    onBrowse()
                                }
                            )
                            bookmark.url?.let {
                                val clipboardManager = LocalClipboardManager.current
                                DropdownMenuItem(
                                    text = { Text(text = "Copy link") },
                                    leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(it))
                                        showMenu = false
                                    }
                                )
                            }
                        }
                        DropdownMenuItem(
                            text = { Text(text = "Move to") },
                            leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                            onClick = {
                                moveItem = bookmark
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Edit") },
                            leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                            onClick = {
                                editItem = bookmark
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Delete") },
                            leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                            onClick = {
                                viewModel.deleteBookmark(bookmark)
                                showMenu = false
                            }
                        )
                    }
                }
            }

            item { // TODO add key and animateItemPlacement
                when (bookmark.type) {
                    BookmarkItemV2.BookmarkType.BOOKMARK -> WebsiteRowWithIcon(
                        title = bookmark.title,
                        url = bookmark.url ?: "",
                        browserIcons = viewModel.browserIcons,
                        trailing = itemMenu,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.openBookmarkTab(bookmark)
                                onBrowse()
                            }
                            .padding(start = 16.dp)
                    )
                    BookmarkItemV2.BookmarkType.FOLDER -> WebsiteRow(
                        title = bookmark.title,
                        leading = { Icon(
                            painter = painterResource(id = R.drawable.icons_shuffle),
                            contentDescription = "folder icon",
                            modifier = Modifier.size(32.dp).padding(4.dp)
                        ) },
                        trailing = itemMenu,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.visitFolder(bookmark) }
                            .padding(start = 16.dp)
                    )
                }

                /* Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .animateItemPlacement()
                        .fillMaxWidth()
                        .clickable {
                            when (bookmark.type) {
                                BookmarkItemV2.BookmarkType.BOOKMARK -> {
                                    viewModel.openBookmarkTab(bookmark)
                                    onBrowse()
                                }

                                BookmarkItemV2.BookmarkType.FOLDER -> {
                                    viewModel.visitFolder(bookmark)
                                }
                            }
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    ) {
                        if (bookmark.type == BookmarkItemV2.BookmarkType.BOOKMARK) {
                            bookmark.icon?.bitmap?.let { icon ->
                                Image(bitmap = icon.asImageBitmap(), contentDescription = "icon")
                            } ?: bookmark.url?.let { url ->
                                UrlIcon(browserIcons = viewModel.browserIcons, url = url)
                                /* viewModel.browserIcons.Loader(url) {
                                    WithIcon {
                                        Image(painter = it.painter, contentDescription = "icon")
                                    }
                                    Placeholder {
                                        Image(
                                            painter = painterResource(id = R.drawable.icons_shuffle), // TODO replace placeholder icon
                                            contentDescription = "icon"
                                        )
                                    }
                                } */
                            }
                        } else {
                            Icon(painter = painterResource(id = R.drawable.icons_shuffle), contentDescription = "Folder icon") // TODO change icon
                        }
                    }

                    Column(modifier = Modifier.weight(2f)) {
                        Text(text = bookmark.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        bookmark.url?.let {
                            Text(
                                text = it,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = GreenUrl
                                )
                            )
                        }
                    }
                    Box {
                        var showMenu by remember { mutableStateOf(false) }

                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.icons_more_vertical),
                                contentDescription = "more"
                            )
                        }
                        Dropdown(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            if (bookmark.type == BookmarkItemV2.BookmarkType.BOOKMARK) {
                                DropdownMenuItem(
                                    text = { Text(text = "Open in new tab") },
                                    leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                                    onClick = {
                                        viewModel.openBookmarkTab(bookmark)
                                        onBrowse()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = "Open in new private tab") },
                                    leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                                    onClick = {
                                        viewModel.openBookmarkTab(bookmark, private = true)
                                        onBrowse()
                                    }
                                )
                                bookmark.url?.let {
                                    val clipboardManager = LocalClipboardManager.current
                                    DropdownMenuItem(
                                        text = { Text(text = "Copy link") },
                                        leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                                        onClick = {
                                            clipboardManager.setText(AnnotatedString(it))
                                            showMenu = false
                                        }
                                    )
                                }
                            }
                            DropdownMenuItem(
                                text = { Text(text = "Move to") },
                                leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                                onClick = {
                                    moveItem = bookmark
                                    showMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Edit") },
                                leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                                onClick = {
                                    editItem = bookmark
                                    showMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(text = "Delete") },
                                leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), "open in new tab") },
                                onClick = {
                                    viewModel.deleteBookmark(bookmark)
                                    showMenu = false
                                }
                            )
                        }
                    }
                } */
            }
        }
    }

    editItem?.let { item ->
        BookmarkEditDialog(
            item = item,
            onSubmit = { title, url ->
                viewModel.editBookmark(item, title, url)
            },
            onDismiss = { editItem = null }
        )
    }

    moveItem?.let { item ->
        BookmarkMoveDialog(
            item = item,
            bookmarksRoot = viewModel.bookmarksRoot,
            onSubmit = { to ->
                viewModel.moveBookmark(item, to)
            },
            onDismiss = { moveItem = null }
        )
    }
}