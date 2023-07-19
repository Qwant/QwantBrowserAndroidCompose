package com.qwant.android.qwantbrowser.ui.bookmarks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog
import org.mozilla.reference.browser.storage.BookmarkItemV2

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
        title = "Move " + // TODO bookmark create/edit title text
                (if (item.type == BookmarkItemV2.BookmarkType.BOOKMARK) "bookmark" else "folder") +
                " to",
        onYes = {
            onSubmit(selectedItem)
            onDismiss()
        },
        onNo = { onDismiss() },
        yesText = "Ok", // TODO translations
        noText = "Cancel" // TODO translations
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .minimumInteractiveComponentSize()
                .background(background)
                .clickable { onSelected(null) }
        ) {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "arrow")
            }
            Text(text = "Bookmarks") // TODO translation
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
    var open by remember { mutableStateOf( exclude?.let { currentFolder.isParentOf(exclude) } ?: false) }
    val arrowRotation by animateFloatAsState(targetValue = if (open) 0f else -90f)

    Column(modifier = Modifier.padding(start = 16.dp)) {
        val children = currentFolder.children
            .filter { it != exclude && it.type == BookmarkItemV2.BookmarkType.FOLDER }
            .sortedBy { it.title.lowercase() }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .minimumInteractiveComponentSize()
                .fillMaxWidth()
                .background(background)
                .clickable { onSelected(currentFolder) }
        ) {
            if (children.isNotEmpty()) {
                IconButton(
                    modifier = Modifier.rotate(arrowRotation),
                    onClick = { open = !open }
                ) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "arrow") // TODO change arrow icon
                }
            } else {
                Box(modifier = Modifier.minimumInteractiveComponentSize())
            }
            Text(text = currentFolder.title)
        }
        AnimatedVisibility (open) {
            Column {
                children.forEach {
                    BookmarkFolderTreeItem(it, exclude, selectedFolder, onSelected)
                }
            }
        }
    }
}