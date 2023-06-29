package com.qwant.android.qwantbrowser.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.qwant.android.qwantbrowser.userdata.history.HistoryPagingSource
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    core: Core,
    useCases: UseCases
) : ViewModel() {
    val historyItems = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { HistoryPagingSource(core.historyStorage) }
    ).flow.cachedIn(viewModelScope)

    val openNewTab = useCases.tabsUseCases.addTab
}