package com.qwant.android.qwantbrowser.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.qwant.android.qwantbrowser.userdata.history.HistoryPagingSource
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val core: Core,
    useCases: UseCases
) : ViewModel() {

    private val source = InvalidatingPagingSourceFactory { HistoryPagingSource(core.historyStorage) }
    private val pager = Pager(
        config = PagingConfig(
            pageSize = 50,
            enablePlaceholders = false
        ),
        pagingSourceFactory = source
    )

    val historyItems = pager.flow.cachedIn(viewModelScope)

    val openNewTab = useCases.tabsUseCases.addTab
    val browserIcons = core.browserIcons

    fun deleteUrlFromHistory(url: String) {
        viewModelScope.launch {
            core.historyStorage.deleteVisitsFor(url)
            source.invalidate()
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            core.historyStorage.deleteEverything()
            source.invalidate()
        }
    }
}