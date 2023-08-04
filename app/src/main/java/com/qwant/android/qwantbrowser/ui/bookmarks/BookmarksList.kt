package com.qwant.android.qwantbrowser.ui.bookmarks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.browser.suggest.WebsiteRow
import com.qwant.android.qwantbrowser.ui.browser.suggest.WebsiteRowWithIcon
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import org.mozilla.reference.browser.storage.BookmarkItemV2
import mozilla.components.feature.contextmenu.R as mozacR

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
                        val folderOrBookmarkString = when (bookmark.type) {
                            BookmarkItemV2.BookmarkType.BOOKMARK -> stringResource(R.string.bookmarks_bookmark)
                            else -> stringResource(R.string.bookmarks_folder)
                        }
                        val moveTitle = stringResource(R.string.bookmarks_move_x_to, folderOrBookmarkString)
                        val editTitle = "${stringResource(R.string.edit)} $folderOrBookmarkString"
                        val deleteTitle = "${stringResource(R.string.delete)} $folderOrBookmarkString"
                        if (bookmark.type == BookmarkItemV2.BookmarkType.BOOKMARK) {
                            val openTitle = stringResource(mozacR.string.mozac_feature_contextmenu_open_link_in_new_tab)
                            val openPrivateTitle = stringResource(mozacR.string.mozac_feature_contextmenu_open_link_in_private_tab)
                            val copyTitle = stringResource(mozacR.string.mozac_feature_contextmenu_copy_link)
                            DropdownMenuItem(
                                text = { Text(text = openTitle) },
                                leadingIcon = { Icon(painterResource(id = R.drawable.icons_add_tab), openTitle) },
                                onClick = {
                                    viewModel.openBookmarkTab(bookmark)
                                    onBrowse()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(text = openPrivateTitle) },
                                leadingIcon = { Icon(painterResource(id = R.drawable.icons_privacy_mask), openPrivateTitle) },
                                onClick = {
                                    viewModel.openBookmarkTab(bookmark, private = true)
                                    onBrowse()
                                }
                            )
                            bookmark.url?.let {
                                val clipboardManager = LocalClipboardManager.current
                                DropdownMenuItem(
                                    text = { Text(text = copyTitle) },
                                    leadingIcon = { Icon(painterResource(id = R.drawable.assist_icons_document_file_copy_line), copyTitle) },
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(it))
                                        showMenu = false
                                    }
                                )
                            }
                        }
                        DropdownMenuItem(
                            text = { Text(text = moveTitle) },
                            leadingIcon = { Icon(painterResource(id = R.drawable.icons_arrow_forward), moveTitle) },
                            onClick = {
                                moveItem = bookmark
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = editTitle) },
                            leadingIcon = { Icon(painterResource(id = R.drawable.icons_edit), editTitle) },
                            onClick = {
                                editItem = bookmark
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = deleteTitle) },
                            leadingIcon = { Icon(painterResource(id = R.drawable.icons_delete_bookmark), deleteTitle) },
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
                            painter = painterResource(id = R.drawable.icons_folder),
                            contentDescription = "folder icon",
                            modifier = Modifier
                                .size(32.dp)
                                .padding(4.dp)
                        ) },
                        trailing = itemMenu,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.visitFolder(bookmark) }
                            .padding(start = 16.dp)
                    )
                }
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