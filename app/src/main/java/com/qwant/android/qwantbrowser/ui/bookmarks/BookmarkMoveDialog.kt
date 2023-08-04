package com.qwant.android.qwantbrowser.ui.bookmarks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog
import org.mozilla.reference.browser.storage.BookmarkItemV2
import com.qwant.android.qwantbrowser.R

@Composable
fun BookmarkMoveDialog(
    item: BookmarkItemV2,
    bookmarksRoot: ArrayList<BookmarkItemV2>,
    onDismiss: () -> Unit = {},
    onSubmit: (to: BookmarkItemV2?) -> Unit = {}
) {
    var selectedItem by remember { mutableStateOf(item.parent) }

    YesNoDialog(
        onDismissRequest = { onDismiss() },
        title = stringResource(id = R.string.bookmarks_move_x_to, stringResource(
            if (item.type == BookmarkItemV2.BookmarkType.BOOKMARK) R.string.bookmarks_bookmark
            else R.string.bookmarks_folder
        )),
        onYes = {
            onSubmit(selectedItem)
            onDismiss()
        },
        onNo = { onDismiss() },
        yesText = stringResource(id = R.string.save)
    ) {
        RootBookmarkFolderTreeItem(
            root = bookmarksRoot,
            exclude = item,
            selectedFolder = selectedItem,
            onSelected = { selectedItem = it }
        )
    }
}

@Composable
fun RootBookmarkFolderTreeItem(
    root: ArrayList<BookmarkItemV2>,
    exclude: BookmarkItemV2?,
    selectedFolder: BookmarkItemV2?,
    onSelected: (BookmarkItemV2?) -> Unit
) {
    val background = if (selectedFolder == null) MaterialTheme.colorScheme.primary else Color.Transparent

    Column {
        CompositionLocalProvider(LocalContentColor provides
                if (selectedFolder == null) MaterialTheme.colorScheme.onPrimary else LocalContentColor.current
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(background)
                    .clickable { onSelected(null) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons_folder),
                    contentDescription = "Folder icon",
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Text(text = stringResource(id = R.string.bookmarks))
            }
        }
        root
            .filter { it != exclude && it.type == BookmarkItemV2.BookmarkType.FOLDER }
            .sortedBy { it.title.lowercase() }
            .forEach { child ->
                BookmarkFolderTreeItem(child, exclude, selectedFolder, onSelected)
            }
    }
}

@Composable
fun BookmarkFolderTreeItem(
    currentFolder: BookmarkItemV2,
    exclude: BookmarkItemV2? = null,
    selectedFolder: BookmarkItemV2? = null,
    onSelected: (BookmarkItemV2?) -> Unit
) {
    val background = if (selectedFolder == currentFolder) MaterialTheme.colorScheme.primary else Color.Transparent

    // if we only want actual selected parents to be open, use as starting value for open
    //      exclude?.let { currentFolder.isParentOf(exclude) } ?: false
    var open by remember { mutableStateOf( true) }

    val arrowRotation by animateFloatAsState(targetValue = if (open) 90f else 180f, label = "arrowRotation")

    Column(modifier = Modifier.padding(start = 16.dp)) {
        val children = currentFolder.children
            .filter { it != exclude && it.type == BookmarkItemV2.BookmarkType.FOLDER }
            .sortedBy { it.title.lowercase() }

        CompositionLocalProvider(LocalContentColor provides
                if (selectedFolder == currentFolder) MaterialTheme.colorScheme.onPrimary else LocalContentColor.current
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .background(background)
                    .clickable { onSelected(currentFolder) }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons_folder),
                    contentDescription = "Folder icon",
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Text(text = currentFolder.title, modifier = Modifier.weight(2f))

                if (children.isNotEmpty()) {
                    IconButton(onClick = { open = !open }) {
                        Icon(
                            painterResource(
                                id = R.drawable.icons_chevron_forward
                            ),
                            contentDescription = "Arrow",
                            modifier = Modifier.rotate(arrowRotation)
                        )
                    }
                }
            }
        }
        AnimatedVisibility(open) {
            Column {
                children.forEach {
                    BookmarkFolderTreeItem(it, exclude, selectedFolder, onSelected)
                }
            }
        }
    }
}