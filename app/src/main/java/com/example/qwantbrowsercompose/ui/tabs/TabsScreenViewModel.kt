package com.example.qwantbrowsercompose.ui.tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qwantbrowsercompose.mozac.Core
import com.example.qwantbrowsercompose.mozac.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import mozilla.components.lib.state.ext.flow
import javax.inject.Inject

@HiltViewModel
class TabsScreenViewModel @Inject constructor(
    mozac: Core,
    private val useCases: UseCases
): ViewModel() {
    private val homepageUrl = "https://www.qwant.com" // TODO get this from preference repository

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
        useCases.tabsUseCases.addTab.invoke(homepageUrl, selectTab = true, private = private)
    }

    fun removeTabs(private: Boolean = false) {
        if (private) {
            useCases.tabsUseCases.removePrivateTabs.invoke()
        } else {
            useCases.tabsUseCases.removeNormalTabs.invoke()
        }
    }

    val selectTab = useCases.tabsUseCases.selectTab
    val removeTab = useCases.tabsUseCases.removeTab
}