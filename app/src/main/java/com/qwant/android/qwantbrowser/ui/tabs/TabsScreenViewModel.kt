package com.qwant.android.qwantbrowser.ui.tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.app.TabsViewOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.browser.thumbnails.storage.ThumbnailStorage
import mozilla.components.feature.tabs.TabsUseCases
import mozilla.components.lib.state.ext.flow
import javax.inject.Inject

@HiltViewModel
class TabsScreenViewModel @Inject constructor(
    store: BrowserStore,
    private val tabsUseCases: TabsUseCases,
    private val appPreferencesRepository: AppPreferencesRepository,
    val thumbnailStorage: ThumbnailStorage,
    val browserIcons: BrowserIcons
): ViewModel() {
    val tabs = store.flow()
        .map { state -> state.tabs }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = listOf()
        )

    val selectedTabId = store.flow()
        .map { state -> state.selectedTabId }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val tabsViewOption = appPreferencesRepository.flow
        .map { prefs -> prefs.tabsView }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = TabsViewOption.UNRECOGNIZED
        )

    fun updateTabsViewOption(option: TabsViewOption) {
        viewModelScope.launch { appPreferencesRepository.updateTabsView(option) }
    }

    fun removeTabs(private: Boolean = false) {
        if (private) {
            tabsUseCases.removePrivateTabs.invoke()
        } else {
            tabsUseCases.removeNormalTabs.invoke()
        }
    }

    // val thumbnailStorage = mozac.thumbnailStorage
    // val browserIcons = mozac.browserIcons

    val selectTab = tabsUseCases.selectTab
    val removeTab = tabsUseCases.removeTab
}