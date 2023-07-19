package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getTextBeforeSelection
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.suggest.providers.QwantOpensearchProvider
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.SuggestionProvider
import com.qwant.android.qwantbrowser.suggest.providers.SessionTabsProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.lib.state.ext.flow
import mozilla.components.support.ktx.kotlinx.coroutines.flow.ifChanged

@AssistedFactory
interface ToolbarStateFactory {
    fun create(coroutineScope: CoroutineScope = MainScope()) : ToolbarState
}

class ToolbarState @AssistedInject constructor(
    mozac: Core,
    appPreferencesRepository: AppPreferencesRepository,
    // suggestionProviders: List<SuggestionProvider>, // TODO use hilt to inject suggestion providers. Add ClipboardProvider
    @Assisted private val coroutineScope: CoroutineScope = MainScope()
) {
    private val suggestionProviders: List<SuggestionProvider> = listOf(
        QwantOpensearchProvider(mozac.client),
        SessionTabsProvider(mozac.store),
        mozac.historyStorage,
        mozac.bookmarkStorage
    )

    private val store = mozac.store

    var visible by mutableStateOf(true)
        private set

    var text by mutableStateOf(TextFieldValue(""))
        private set

    var hasFocus by mutableStateOf(false)
        private set

    var trueHeightPx by mutableIntStateOf(0)
        private set

     var suggestions = suggestionProviders.map { it to emptyList<Suggestion>() }.toMutableStateMap()
        private set

    init {
        coroutineScope.launch {
            snapshotFlow { text.getTextBeforeSelection(text.text.length).text }
                .ifChanged()
                .onEach { search ->
                    if (hasFocus && search.isNotEmpty()) {
                        suggestionProviders.forEach { provider ->
                            suggestions[provider] = provider.getSuggestions(search)
                        }
                    } else {
                        suggestions.keys.forEach { suggestions[it] = listOf() }
                    }
                }
                .collect()
        }
    }

    val toolbarPosition = appPreferencesRepository.flow
        .map { it.toolbarPosition }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ToolbarPosition.BOTTOM
        )

    val shouldHideOnScroll = appPreferencesRepository.flow
        .map { prefs -> prefs.hideToolbarOnScroll }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val loadingProgress = store.flow()
        .map { state -> state.selectedTab?.content?.progress?.toFloat()?.div(100) }
        .filterNotNull()
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = 0f
        )

    private val currentUrl = store.flow()
        .map { state -> state.selectedTab?.content?.url }
        .ifChanged()
        .onEach {
            if (!hasFocus) {
                updateTextWithUrl(it ?: "")
                updateVisibility(true)
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = ""
        )

    internal fun updateText(text: TextFieldValue) {
        this.text = text
    }

    internal fun updateText(text: String) {
        updateText(TextFieldValue(text))
    }

    fun updateVisibility(visible: Boolean) {
        this.visible = visible
    }

    internal fun updateFocus(hasFocus: Boolean) {
        this.hasFocus = hasFocus
        // TODO update toolbar text on focus should clear when on qwant home, display search when on SERP. Else only display full url
        updateTextWithUrl(currentUrl.value ?: "")
        // if (!hasFocus) suggestions = emptyMap()
    }

    private fun updateTextWithUrl(url: String) {
        coroutineScope.launch {
            val filteredUrl = if (url.startsWith("data:text/html")) "" else url
            text = if (!hasFocus) {
                // TODO Improve toolbar display url cleaning
                TextFieldValue(filteredUrl.removePrefix("https://").removePrefix("www."))
            } else {
                delay(10) // needed else selection is overridden by the onValueChange
                TextFieldValue(filteredUrl, selection = TextRange(0, url.length))
            }
        }
    }

    internal fun updateTrueHeightPx(height: Int) {
        trueHeightPx = height
    }

    /* private fun updateSuggestions(
        group: AwesomeBar.SuggestionProviderGroup,
        provider:  AwesomeBar.SuggestionProvider,
        newSuggestions: List<AwesomeBar.Suggestion>
    ) {
        val suggestionMap = suggestions

        val updatedSuggestions = (suggestionMap[group] ?: emptyList())
            .filter { suggestion -> suggestion.provider != provider }
            .toMutableList()

        updatedSuggestions.addAll(newSuggestions)
        updatedSuggestions.sortByDescending { suggestion -> suggestion.score }

        val updatedSuggestionMap = suggestionMap.toMutableMap()
        updatedSuggestionMap[group] = updatedSuggestions
        suggestions = updatedSuggestionMap
    } */
}
