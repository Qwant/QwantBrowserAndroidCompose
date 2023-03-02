package com.qwant.android.qwantbrowser.preferences.frontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FrontEndPreferencesViewModel @Inject constructor(
    private val repository: FrontEndPreferencesRepository
): ViewModel() {
    private val flow = repository.flow

    val preferencesState = flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = FrontEndPreferences.getDefaultInstance()
    )

    fun updateShowNews(show: Boolean) {
        viewModelScope.launch { repository.updateShowNews(show) }
    }

    fun updateInterfaceLanguage(language: String) {
        viewModelScope.launch { repository.updateInterfaceLanguage(language) }
    }
}