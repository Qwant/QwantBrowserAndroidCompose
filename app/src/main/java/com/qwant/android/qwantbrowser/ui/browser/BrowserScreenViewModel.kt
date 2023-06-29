package com.qwant.android.qwantbrowser.ui.browser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases
import com.qwant.android.qwantbrowser.ui.browser.toolbar.ToolbarState
import com.qwant.android.qwantbrowser.ui.browser.toolbar.ToolbarStateFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.concept.awesomebar.AwesomeBar
import mozilla.components.lib.state.ext.flow
import mozilla.components.support.ktx.kotlin.isUrl
import mozilla.components.support.ktx.kotlin.toNormalizedUrl
import javax.inject.Inject

@HiltViewModel
class BrowserScreenViewModel @Inject constructor(
    mozac: Core,
    private val useCases: UseCases
): ViewModel() {
    @Inject lateinit var toolbarStateFactory: ToolbarStateFactory
    val toolbarState: ToolbarState by lazy {
        toolbarStateFactory.create(viewModelScope)
    }
    // val toolbarState = ToolbarState(mozac.store, appPreferencesRepository, suggestionProvider, viewModelScope)

    val tabCount = mozac.store.flow()
        .map { state -> state.tabs.count() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = 0
        )

    val currentUrl = mozac.store.flow()
        .map { state -> state.selectedTab?.content?.url }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ""
        )

    val canGoBack = mozac.store.flow()
        .map { state -> state.selectedTab?.content?.canGoBack ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val canGoForward = mozac.store.flow()
        .map { state -> state.selectedTab?.content?.canGoForward ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val reloadUrl = useCases.sessionUseCases.reload
    val stopLoading = useCases.sessionUseCases.stopLoading
    val goBack = useCases.sessionUseCases.goBack
    val goForward = useCases.sessionUseCases.goForward

    val desktopMode = mozac.store.flow()
        .map { state -> state.selectedTab?.content?.desktopMode ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )
    val requestDesktopSite = useCases.sessionUseCases.requestDesktopSite

    val engine = mozac.engine
    val store = mozac.store
    val downloadManager = mozac.downloadManager
    val permissionStorage = mozac.geckoSitePermissionsStorage
    val sessionUseCases = useCases.sessionUseCases
    val tabsUseCases = useCases.tabsUseCases
    val contextMenuUseCases = useCases.contextMenuUseCases
    val downloadUseCases = useCases.downloadUseCases

    fun commitSuggestion(suggestion: AwesomeBar.Suggestion) {
        /* if (suggestion.url != null) {
            useCases.sessionUseCases.loadUrl(url = suggestion.url.toNormalizedUrl())
        } else {
            commitSearch(suggestion.label)
        } */
        // suggestion.onSuggestionClicked?.let { it() }
    }

    fun commitSearch(searchText: String) {
        if (searchText.isUrl()) {
            useCases.sessionUseCases.loadUrl(url = searchText.toNormalizedUrl())
        } else {
            useCases.qwantUseCases.loadSERPPage(viewModelScope, searchText)
        }
    }

    fun openNewQwantTab(private: Boolean = false) {
        if (private) {
            useCases.qwantUseCases.openPrivatePage()
        } else {
            useCases.qwantUseCases.openHomePage(viewModelScope, private = false)
        }
        toolbarState.updateFocus(true)
    }
}
