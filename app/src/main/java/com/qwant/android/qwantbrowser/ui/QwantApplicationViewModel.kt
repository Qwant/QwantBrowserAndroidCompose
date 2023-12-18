package com.qwant.android.qwantbrowser.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.legacy.history.History
import com.qwant.android.qwantbrowser.piwik.Piwik
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import com.qwant.android.qwantbrowser.ui.zap.ZapState
import com.qwant.android.qwantbrowser.usecases.ClearDataUseCase
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.storage.HistoryStorage
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.lib.state.ext.flow
import javax.inject.Inject

enum class PrivacyMode {
    NORMAL, PRIVATE, SELECTED_TAB_PRIVACY
}

@HiltViewModel
class QwantApplicationViewModel @Inject constructor(
    store: BrowserStore,
    historyStorage: HistoryStorage,
    sessionUseCases: SessionUseCases,
    frontEndPreferencesRepository: FrontEndPreferencesRepository,
    appPreferencesRepository: AppPreferencesRepository,
    qwantUseCases: QwantUseCases,
    clearDataUseCase: ClearDataUseCase,
    private val piwik: Piwik
) : ViewModel() {
    private val privacyMode = MutableStateFlow(PrivacyMode.SELECTED_TAB_PRIVACY)
    private val history = historyStorage as History

    private val selectedTabPrivacy = store.flow()
        .map { state -> state.selectedTab?.content?.private ?: false }

    var hasHistory by mutableStateOf(history.size != 0)
        private set

    init {
        history.onSizeChanged = {
            hasHistory = (it != 0)
        }
    }

    val snackbarHostState = SnackbarHostState()
    data class SnackbarAction(val label: String, val apply: () -> Unit)
    fun showSnackbar(
        message: String,
        action: SnackbarAction? = null,
        withDismissAction: Boolean = true,
        duration: SnackbarDuration = SnackbarDuration.Long
    ) {
        viewModelScope.launch {
            when (snackbarHostState.showSnackbar(message, action?.label, withDismissAction, duration)) {
                SnackbarResult.ActionPerformed -> { action?.apply?.invoke() }
                SnackbarResult.Dismissed -> {}
            }
        }
    }

    val toolbarPosition = appPreferencesRepository.flow
        .map { it.toolbarPosition }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ToolbarPosition.UNRECOGNIZED
        )

    val isPrivate = privacyMode
        .combine(selectedTabPrivacy) { privacyMode, selectedTabPrivacy ->
            when (privacyMode) {
                PrivacyMode.NORMAL -> false
                PrivacyMode.PRIVATE -> true
                PrivacyMode.SELECTED_TAB_PRIVACY -> selectedTabPrivacy
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val appearance = frontEndPreferencesRepository.flow
        .map { it.appearance }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = Appearance.UNRECOGNIZED
        )

    val homeUrl = frontEndPreferencesRepository.homeUrl
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val qwantTabs = store.flow()
        .map { state ->
            state.tabs.filter { it.content.url.isQwantUrl() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = listOf()
        )

    val loadUrlUseCase = sessionUseCases.loadUrl
    val getQwantUrlUseCase = qwantUseCases.getQwantUrl

    fun setPrivacyMode(mode: PrivacyMode) {
        privacyMode.update { mode }
    }

    val zapOnQuit = appPreferencesRepository.flow
        .map { it.clearDataOnQuit }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val zapState: ZapState = ZapState(clearDataUseCase, viewModelScope)
    fun zap(
        from: String = "Toolbar",
        skipConfirmation: Boolean = false,
        then: (Boolean) -> Unit = {}
    ) {
        if (skipConfirmation) {
            piwik.event("Zap", "Auto", name = "App quit")
        } else {
            piwik.event("Zap", "Intention", from)
        }

        zapState.zap(skipConfirmation) { success ->
            if (success && !skipConfirmation) {
                piwik.event("Zap", "Confirmation", from)
            }
            then(success)
        }
    }
}