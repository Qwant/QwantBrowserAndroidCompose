package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.runtime.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.suggest.GroupedSuggestionProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.lib.state.ext.flow
import mozilla.components.support.ktx.kotlinx.coroutines.flow.ifChanged

class ToolbarState(
    store: BrowserStore,
    appPreferencesRepository: AppPreferencesRepository,
    suggestionProvider: GroupedSuggestionProvider,
    private val coroutineScope: CoroutineScope = MainScope()
) {
    val toolbarPosition = appPreferencesRepository.flow
        .map { prefs -> when (prefs.toolbarPosition) {
            ToolbarPosition.BOTTOM -> HideOnScrollPosition.Bottom
            else -> HideOnScrollPosition.Top
        }}
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HideOnScrollPosition.Top
        )

    val shouldHideOnScroll = appPreferencesRepository.flow
        .map { prefs -> prefs.hideToolbarOnScroll }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    var visible by mutableStateOf(true)
        private set

    var text by mutableStateOf(TextFieldValue(""))
        private set

    var hasFocus by mutableStateOf(false)
        private set

    var trueHeightPx by mutableStateOf(0)
        private set

    val suggestions = snapshotFlow { text.text }
        .ifChanged()
        .mapNotNull { search ->
            if (hasFocus) suggestionProvider.getSuggestions(search)
            else listOf()
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = listOf()
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
        updateTextWithUrl(currentUrl.value ?: "")
    }

    private fun updateTextWithUrl(url: String) {
        coroutineScope.launch {
            text = if (!hasFocus) {
                TextFieldValue(url.removePrefix("https://").removePrefix("www."))
            } else {
                delay(10) // needed else selection is overrided by the onValueChange
                TextFieldValue(url, selection = TextRange(0, url.length))
            }
        }
    }

    internal fun updateTrueHeightPx(height: Int) {
        trueHeightPx = height
    }
}
