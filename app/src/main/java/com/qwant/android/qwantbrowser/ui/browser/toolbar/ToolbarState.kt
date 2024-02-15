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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.lib.state.ext.flow

@AssistedFactory
interface ToolbarStateFactory {
    fun create(coroutineScope: CoroutineScope = MainScope()) : ToolbarState
}

@OptIn(ExperimentalCoroutinesApi::class)
class ToolbarState @AssistedInject constructor(
    store: BrowserStore,
    appPreferencesRepository: AppPreferencesRepository,
    suggestionProviders: @JvmSuppressWildcards List<SuggestionProvider>,
    val browserIcons: BrowserIcons,
    val datahub: Datahub,
    @Assisted private val coroutineScope: CoroutineScope = MainScope()
) {
    var visible by mutableStateOf(true)
        private set

    var text by mutableStateOf(TextFieldValue(""))
        private set

    var hasFocus by mutableStateOf(false)
        private set

    var trueHeightPx by mutableIntStateOf(0)
        private set

    private val backgroundScope = CoroutineScope(Dispatchers.IO + Job())
    private val emptySuggestions = suggestionProviders.associateWith { emptyList<Suggestion>() }
    val suggestions = snapshotFlow { text.getTextBeforeSelection(text.text.length).text }
        .distinctUntilChanged()
        .mapLatest { search ->
            delay(100)
            if (hasFocus && search.isNotBlank()) {
                suggestionProviders.associateWith { provider -> provider.getSuggestions(search) }
            } else emptySuggestions
        }
        .stateIn(
            scope = backgroundScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptySuggestions
        )

    var onQwant by mutableStateOf(true)
        private set

    var showSiteSecurity by mutableStateOf(false)
        private set

    val toolbarPosition = appPreferencesRepository.flow
        .map { it.toolbarPosition }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ToolbarPosition.UNRECOGNIZED
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

    val currentUrl = store.flow()
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

    fun updateShowSiteSecurity(visible: Boolean) {
        this.showSiteSecurity = visible
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
                    if (hasFocus) TextFieldValue(search.urlDecode(), selection = TextRange(0, search.length))
                    else TextFieldValue(search.urlDecode())
                } ?: TextFieldValue("")
            } else if (!hasFocus) {
                TextFieldValue(url.toCleanHost())
            } else {
                // TODO Constraint url to a maximum size
                TextFieldValue(url, selection = TextRange(0, url.length))
            }
        }
    }

    internal fun updateTrueHeightPx(height: Int) {
        trueHeightPx = height
    }
}
