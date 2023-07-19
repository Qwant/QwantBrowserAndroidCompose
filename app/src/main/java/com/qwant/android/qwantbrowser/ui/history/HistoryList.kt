package com.qwant.android.qwantbrowser.ui.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import mozilla.components.concept.storage.VisitInfo
import com.qwant.android.qwantbrowser.R
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryList(
    historyViewModel: HistoryViewModel,
    onItemSelected: (visit: VisitInfo, private: Boolean) -> Unit,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val visits = historyViewModel.historyItems.collectAsLazyPagingItems()

    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            if (visits.loadState.refresh == LoadState.Loading) {
                item {
                    Text(
                        text = "Waiting for items to load from the backend", // TODO replace text by loading icon
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }


            if (visits.itemCount > 0) {

                val calendar = Calendar.getInstance()
                val todayDayOfYear = calendar.apply { time = Date() }.get(Calendar.DAY_OF_YEAR)
                var lastDayOfYear: Int? = null

                for (i in 0 until visits.itemCount) {
                    visits[i]?.let { item ->

                        calendar.apply { time = Date(item.visitTime) }
                        val visitDayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
                        if (lastDayOfYear == null || visitDayOfYear != lastDayOfYear) {
                            val dateString = when (todayDayOfYear - calendar.get(Calendar.DAY_OF_YEAR)) {
                                0 -> "Today" // TODO translations
                                1 -> "Yesterday"
                                else -> "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH)}"
                            }

                            item(key = "date-$dateString-${calendar.get(Calendar.YEAR)}") {
                                Text(text = dateString, modifier = Modifier.padding(start = 8.dp))
                            }

                            lastDayOfYear = visitDayOfYear
                        }

                        item(visits.itemKey().invoke(i)) {
                            val clipboardManager = LocalClipboardManager.current
                            HistoryItem(
                                visit = item,
                                browserIcons = historyViewModel.browserIcons,
                                onItemSelected = onItemSelected,
                                menuItems = listOf(
                                    MenuItem("Ouvrir le lien dans un nouvel onglet", R.drawable.icons_search) {
                                        onItemSelected(item,false)
                                    },
                                    MenuItem("Ouvrir le lien dans un nouvel onglet privé", R.drawable.icons_privacy_mask) {
                                        onItemSelected(item,true)
                                    },
                                    MenuItem("Copier le lien", R.drawable.icons_download) {
                                        clipboardManager.setText(AnnotatedString(item.url))
                                    },
                                    MenuItem("Supprimer", R.drawable.icons_close) {
                                        historyViewModel.deleteUrlFromHistory(item.url)
                                    }
                                ),
                                modifier = Modifier.animateItemPlacement()
                            )
                        }
                    }
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