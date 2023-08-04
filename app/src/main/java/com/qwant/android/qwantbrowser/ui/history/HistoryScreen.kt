package com.qwant.android.qwantbrowser.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.widgets.ScreenHeader
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    var showDeleteAllConfirmation by remember { mutableStateOf(false) }

    Column {
        ScreenHeader(
            title = stringResource(id = R.string.history),
            scrollableState = lazyListState,
            actions = {
                IconButton(onClick = { showDeleteAllConfirmation = true }) {
                    Icon(painter = painterResource(id = R.drawable.icons_close), contentDescription = "Delete history")
                }
            }
        )
        HistoryList(
            historyViewModel = historyViewModel,
            lazyListState = lazyListState,
            onItemSelected = { visit, private ->
                historyViewModel.openNewTab(visit.url, selectTab = true, private = private)
                onClose()
            }
        )
    }

    if (showDeleteAllConfirmation) {
        YesNoDialog(
            onDismissRequest = { showDeleteAllConfirmation = false },
            title = stringResource(id = R.string.history_clear_all_confirm_title),
            description = stringResource(id = R.string.history_clear_all_confirm_message),
            yesText = stringResource(id = R.string.history_clear_all_confirm_ok),
            onYes = {
                historyViewModel.deleteAllHistory()
                showDeleteAllConfirmation = false
            },
            onNo = { showDeleteAllConfirmation = false }
        )
    }
}