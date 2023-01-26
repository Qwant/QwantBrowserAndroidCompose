package com.example.qwantbrowsercompose

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.qwantbrowsercompose.mozac.Core
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


enum class PrivacyMode {
    NORMAL, PRIVATE, SELECTED_TAB_PRIVACY
}

@HiltViewModel
class QwantApplicationViewModel @Inject constructor(
    val core: Core
) : ViewModel() {
    var privacyMode by mutableStateOf(PrivacyMode.SELECTED_TAB_PRIVACY)
}