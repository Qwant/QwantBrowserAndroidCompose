package com.qwant.android.qwantbrowser.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.stats.Piwik
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import com.qwant.android.qwantbrowser.storage.history.HistoryRepository
import com.qwant.android.qwantbrowser.ui.zap.ZapState
import com.qwant.android.qwantbrowser.usecases.ClearDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.lib.state.ext.flow
import javax.inject.Inject

enum class PrivacyMode {
    NORMAL, PRIVATE, SELECTED_TAB_PRIVACY
}

// TODO Separate ApplicationViewModel into ThemeViewModel and ZapViewModel
//  but I don't where to put snackbar methods
@HiltViewModel
class QwantApplicationViewModel @Inject constructor(
    store: BrowserStore,
    historyRepository: HistoryRepository,
    frontEndPreferencesRepository: FrontEndPreferencesRepository,
    appPreferencesRepository: AppPreferencesRepository,
    clearDataUseCase: ClearDataUseCase,
    private val piwik: Piwik
) : ViewModel() {
    private val privacyMode = MutableStateFlow(PrivacyMode.SELECTED_TAB_PRIVACY)

    private val selectedTabPrivacy = store.flow()
        .map { state -> state.selectedTab?.content?.private ?: false }

    private val backgroundScope = CoroutineScope(Dispatchers.IO + Job())
    val hasHistory = historyRepository.hasHistoryFlow
        .stateIn(
            scope = backgroundScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

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