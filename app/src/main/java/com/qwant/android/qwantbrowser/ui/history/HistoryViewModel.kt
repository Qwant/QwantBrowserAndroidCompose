package com.qwant.android.qwantbrowser.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.qwant.android.qwantbrowser.storage.history.HistoryPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.concept.storage.HistoryStorage
import mozilla.components.feature.tabs.TabsUseCases
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyStorage: HistoryStorage,
    val browserIcons: BrowserIcons,
    tabsUseCases: TabsUseCases
) : ViewModel() {

    private val source = InvalidatingPagingSourceFactory { HistoryPagingSource(historyStorage) }
    private val pager = Pager(
        config = PagingConfig(
            pageSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = source
    )

    private val backgroundScope = CoroutineScope(Dispatchers.IO + Job())
    val historyItems = pager.flow.cachedIn(backgroundScope)

    val openNewTab = tabsUseCases.addTab
    // val browserIcons = core.browserIcons

    fun deleteUrlFromHistory(url: String) {
        viewModelScope.launch {
            historyStorage.deleteVisitsFor(url)
            source.invalidate()
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            historyStorage.deleteEverything()
            source.invalidate()
        }
    }
}