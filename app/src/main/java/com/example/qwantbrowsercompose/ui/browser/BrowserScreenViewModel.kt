package com.example.qwantbrowsercompose.ui.browser


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qwantbrowsercompose.mozac.Core
import com.example.qwantbrowsercompose.mozac.UseCases
import com.example.qwantbrowsercompose.suggest.GroupedSuggestionProvider
import com.example.qwantbrowsercompose.suggest.Suggestion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
    private val searchTerms: MutableStateFlow<String> = MutableStateFlow("")

    val suggestions = searchTerms
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

    val currentUrl = mozac.store.flow()
        .map { state -> state.selectedTab?.content?.url }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ""
        )

    val reloadUrl = useCases.sessionUseCases.reload
    val stopLoading = useCases.sessionUseCases.stopLoading

    fun commitSuggestion(suggestion: Suggestion) {
        if (suggestion.url != null) {
            useCases.sessionUseCases.loadUrl(url = suggestion.url.toNormalizedUrl())
        } else {
            commitSearch(suggestion.label)
        }
    }

    fun commitSearch(searchText: String) {
        if (searchText.isUrl()) {
            useCases.sessionUseCases.loadUrl(url = searchText.toNormalizedUrl())
        } else {
            useCases.qwantUseCases.openSERPPage.invoke(searchText, viewModelScope)
        }
    }

    fun updateSearchTerms(newSearchTerms: String) {
        viewModelScope.launch {
            searchTerms.emit(newSearchTerms)
        }
    }
}
