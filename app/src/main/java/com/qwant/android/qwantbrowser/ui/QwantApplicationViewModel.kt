package com.qwant.android.qwantbrowser.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.lib.state.ext.flow
import javax.inject.Inject


enum class PrivacyMode {
    NORMAL, PRIVATE, SELECTED_TAB_PRIVACY
}

@HiltViewModel
class QwantApplicationViewModel @Inject constructor(
    core: Core,
    useCases: UseCases,
    frontEndPreferencesRepository: FrontEndPreferencesRepository
) : ViewModel() {
    private val privacyMode = MutableStateFlow(PrivacyMode.SELECTED_TAB_PRIVACY)

    private val selectedTabPrivacy = core.store.flow()
        .map { state -> state.selectedTab?.content?.private ?: false }

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

    val qwantTabs = core.store.flow()
        .map { state ->
            state.tabs.filter { it.content.url.isQwantUrl() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = listOf()
        )

    val loadUrlUseCase = useCases.sessionUseCases.loadUrl

    fun setPrivacyMode(mode: PrivacyMode) {
        privacyMode.update { mode }
    }
}