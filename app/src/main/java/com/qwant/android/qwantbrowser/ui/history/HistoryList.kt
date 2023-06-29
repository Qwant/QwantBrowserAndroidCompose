package com.qwant.android.qwantbrowser.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import mozilla.components.concept.storage.VisitInfo
import com.qwant.android.qwantbrowser.R

@Composable
fun HistoryList(
    historyViewModel: HistoryViewModel,
    onItemSelected: (visit: VisitInfo, private: Boolean) -> Unit
) {
    val visits = historyViewModel.historyItems.collectAsLazyPagingItems()

    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {

            if (visits.loadState.refresh == LoadState.Loading) {
                item {
                    Text(
                        text = "Waiting for items to load from the backend",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }

            items(
                count = visits.itemCount,
                key = visits.itemKey(),
                contentType = visits.itemContentType()
            ) { index ->
                visits[index]?.let { item ->
                    HistoryItem(
                        visit = item,
                        onItemSelected = onItemSelected,
                        menuItems = listOf(
                            MenuItem("Ouvrir le lien dans un nouvel onglet", R.drawable.icons_search) {
                                onItemSelected(item,false)
                            },
                            MenuItem("Ouvrir le lien dans un nouvel onglet privé", R.drawable.icons_privacy_mask) {
                                onItemSelected(item,true)
                            },
                            MenuItem("Copier le lien", R.drawable.icons_download) {},
                            MenuItem("Supprimer", R.drawable.icons_close) {}
                        )
                    )
                    Divider(color = Color.Black)
                }
            }

            if (visits.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}