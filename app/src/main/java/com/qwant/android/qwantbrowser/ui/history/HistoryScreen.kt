package com.qwant.android.qwantbrowser.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.widgets.ScreenHeader

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    Column {
        ScreenHeader(
            title = "History",
            actions = {
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(id = R.drawable.icons_close), contentDescription = "Delete history")
                }
            }
        )
        HistoryList(historyViewModel,
            onItemSelected = { visit, private ->
                historyViewModel.openNewTab(visit.url)
                onClose()
            }
        )
    }
}