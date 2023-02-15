package com.qwant.android.qwantbrowser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.mozac.Core
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.lib.state.ext.flow
import javax.inject.Inject


enum class PrivacyMode {
    NORMAL, PRIVATE, SELECTED_TAB_PRIVACY
}

@HiltViewModel
class QwantApplicationViewModel @Inject constructor(
    val core: Core
) : ViewModel() {
    private val privacyMode = MutableStateFlow(PrivacyMode.SELECTED_TAB_PRIVACY)

    private val selectedTabPrivacy = core.store.flow()
        .map { state -> state.selectedTab?.content?.private ?: false }

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

    fun setPrivacyMode(mode: PrivacyMode) {
        privacyMode.update { mode }
    }
}