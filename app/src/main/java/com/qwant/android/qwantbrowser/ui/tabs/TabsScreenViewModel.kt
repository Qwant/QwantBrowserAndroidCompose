package com.qwant.android.qwantbrowser.ui.tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import mozilla.components.lib.state.ext.flow
import javax.inject.Inject

@HiltViewModel
class TabsScreenViewModel @Inject constructor(
    mozac: Core,
    private val useCases: UseCases
): ViewModel() {
    val tabs = mozac.store.flow()
        .map { state -> state.tabs }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = listOf()
        )

    val selectedTabId = mozac.store.flow()
        .map { state -> state.selectedTabId }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    fun openNewQwantTab(private: Boolean = false) {
        useCases.qwantUseCases.openHomePage(viewModelScope, private = private)
    }

    fun removeTabs(private: Boolean = false) {
        if (private) {
            useCases.tabsUseCases.removePrivateTabs.invoke()
        } else {
            useCases.tabsUseCases.removeNormalTabs.invoke()
        }
    }

    val thumbnailStorage = mozac.thumbnailStorage

    val selectTab = useCases.tabsUseCases.selectTab
    val removeTab = useCases.tabsUseCases.removeTab
}