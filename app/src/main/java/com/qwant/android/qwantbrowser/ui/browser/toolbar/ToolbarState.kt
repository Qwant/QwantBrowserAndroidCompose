package com.qwant.android.qwantbrowser.ui.browser.toolbar

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getTextBeforeSelection
import com.qwant.android.qwantbrowser.ext.getQwantSERPSearch
import com.qwant.android.qwantbrowser.ext.isQwantSERPUrl
import com.qwant.android.qwantbrowser.ext.isQwantUrl
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

    var onQwant by mutableStateOf(true)
        private set

    init {
        coroutineScope.launch {
            snapshotFlow { text.getTextBeforeSelection(text.text.length).text }
                .distinctUntilChanged()
                .onEach { search ->
                    if (hasFocus && search.isNotBlank()) {
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

    val siteSecurity = store.flow()
        .map { state -> state.selectedTab?.content?.securityInfo }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    private val currentUrl = store.flow()
        .map { state -> state.selectedTab?.content?.url }
        .distinctUntilChanged()
        .onEach {
            if (!hasFocus) {
                updateTextWithUrl(it ?: "")
                updateVisibility(true)
            }
            onQwant = it?.isQwantUrl() ?: true
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
        coroutineScope.launch {
            delay(10) // Needed else change to toolbar text is overridden by call to onChange.
            updateTextWithUrl(currentUrl.value ?: "")
        }
    }

    private fun updateTextWithUrl(url: String) {
        coroutineScope.launch {
            text = if (url.isQwantUrl()) {
                url.getQwantSERPSearch()?.let { search ->
                    if (hasFocus) TextFieldValue(search, selection = TextRange(0, search.length))
                    else TextFieldValue(search)
                } ?: TextFieldValue("")
            } else if (!hasFocus) {
                // TODO Improve toolbar display url cleaning
                TextFieldValue(url.removePrefix("https://").removePrefix("www."))
            } else {
                TextFieldValue(url, selection = TextRange(0, url.length))
            }
        }
    }

    internal fun updateTrueHeightPx(height: Int) {
        trueHeightPx = height
    }
}
