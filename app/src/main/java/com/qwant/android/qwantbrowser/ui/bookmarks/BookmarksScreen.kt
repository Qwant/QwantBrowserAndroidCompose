package com.qwant.android.qwantbrowser.ui.bookmarks

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.widgets.EmptyPagePlaceholder
import com.qwant.android.qwantbrowser.ui.widgets.ScreenHeader

@Composable
fun BookmarksScreen(
    onBrowse: () -> Unit,
    appViewModel: QwantApplicationViewModel = hiltViewModel(),
    viewModel: BookmarksScreenViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()

    BackHandler(viewModel.currentFolder != null) {
        viewModel.visitFolder(viewModel.currentFolder?.parent)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
        ScreenHeader(
            title = viewModel.currentFolder?.title ?: stringResource(id = R.string.bookmarks),
            scrollableState = lazyListState,
            actions = { NewFolderAction(viewModel) }
        )
        if (viewModel.currentBookmarks.isNotEmpty()) {
            BookmarksList(viewModel = viewModel, lazyListState = lazyListState, onBrowse = onBrowse)
        } else {
            EmptyPagePlaceholder(
                icon = R.drawable.icons_bookmark,
                title = stringResource(id = R.string.bookmarks_empty_title,
                    viewModel.currentFolder?.title ?: stringResource(R.string.bookmarks_empty_default_folder_name)
                ),
                subtitle = stringResource(id = R.string.bookmarks_empty_message)
            )
        }
    }
}

@Composable
fun NewFolderAction(viewModel: BookmarksScreenViewModel) {
    var showCreateFolderDialog by remember { mutableStateOf(false) }

    IconButton(onClick = { showCreateFolderDialog = true }) {
        Icon(painter = painterResource(id = R.drawable.icons_folder_add), contentDescription = "Add folder") // TODO change icon
    }
    if (showCreateFolderDialog) {
        BookmarkEditDialog(
            onSubmit = { title, _ ->
                viewModel.addFolder(title, viewModel.currentFolder)
            },
            onDismiss = { showCreateFolderDialog = false }
        )
    }
}

