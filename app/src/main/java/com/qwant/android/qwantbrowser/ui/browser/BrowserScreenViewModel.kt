package com.qwant.android.qwantbrowser.ui.browser


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases
import com.qwant.android.qwantbrowser.suggest.GroupedSuggestionProvider
import com.qwant.android.qwantbrowser.suggest.Suggestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.lib.state.ext.flow
import mozilla.components.support.ktx.kotlin.isUrl
import mozilla.components.support.ktx.kotlin.toNormalizedUrl
import javax.inject.Inject

const val LOGTAG = "QB_BROWSERVM"

@HiltViewModel
class BrowserScreenViewModel @Inject constructor(
    mozac: Core,
    private val useCases: UseCases,
    suggestionProvider: GroupedSuggestionProvider
): ViewModel() {
    private var searchTerms by mutableStateOf("")
    var toolbarFocus by mutableStateOf(false)
        private set

    val suggestions = snapshotFlow { searchTerms }
        .map { search -> suggestionProvider.getSuggestions(search) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = listOf()
        )

    val loadingProgress = mozac.store.flow()
        .map { state -> state.selectedTab?.content?.progress?.toFloat()?.div(100) }
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = 0f
        )

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

    val reloadUrl = useCases.sessionUseCases.reload
    val stopLoading = useCases.sessionUseCases.stopLoading
    val goBack = useCases.sessionUseCases.goBack

    val engine = mozac.engine
    val store = mozac.store

    fun commitSuggestion(suggestion: Suggestion) {
        if (suggestion.url != null) {
            useCases.sessionUseCases.loadUrl(url = suggestion.url.toNormalizedUrl())
        } else {
            commitSearch(suggestion.label)
        }
    }

    fun changeToolbarFocus(hasFocus: Boolean) {
        toolbarFocus = hasFocus
    }

    fun commitSearch(searchText: String) {
        if (searchText.isUrl()) {
            useCases.sessionUseCases.loadUrl(url = searchText.toNormalizedUrl())
        } else {
            useCases.qwantUseCases.loadSERPPage.invoke(searchText, viewModelScope)
        }
    }

    fun updateSearchTerms(newSearchTerms: String) {
        searchTerms = newSearchTerms
    }
}
