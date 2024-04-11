package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.runtime.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getTextBeforeSelection
import com.qwant.android.qwantbrowser.ext.toCleanHost
import com.qwant.android.qwantbrowser.ext.getQwantSERPSearch
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.ext.urlDecode
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.stats.Datahub
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.SuggestionProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.lib.state.ext.flow

open class ToolbarState(
    val browserIcons: BrowserIcons,
    val datahub: Datahub,
    suggestionProviders: List<SuggestionProvider>,
    coroutineScope: CoroutineScope = MainScope()
) {
    var text by mutableStateOf(TextFieldValue(""))
        internal set

    var hasFocus by mutableStateOf(false)
        private set

    private val emptySuggestions = suggestionProviders.associateWith { emptyList<Suggestion>() }

    @OptIn(ExperimentalCoroutinesApi::class)
    val suggestions = snapshotFlow { text.getTextBeforeSelection(text.text.length).text }
        .distinctUntilChanged()
        .mapLatest { search ->
            delay(100)
            if (hasFocus && search.isNotBlank()) {
                suggestionProviders.associateWith { provider -> provider.getSuggestions(search) }
            } else emptySuggestions
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptySuggestions
        )

    internal fun updateText(text: TextFieldValue) {
        this.text = text
    }

    internal fun updateText(text: String) {
        updateText(TextFieldValue(text))
    }

    internal open fun updateFocus(hasFocus: Boolean) {
        this.hasFocus = hasFocus
    }
}
