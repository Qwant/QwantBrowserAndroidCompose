package com.qwant.android.qwantbrowser.preferences.frontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FrontEndPreferencesViewModel @Inject constructor(
    private val repository: FrontEndPreferencesRepository,
    // private val test_history: HistoryRepository
): ViewModel() {
    val flow = repository.flow

    fun updateShowNews(show: Boolean) {
        viewModelScope.launch {
            repository.updateShowNews(show)
            // test_history.insert(Page("uri", "title", null))
        }
    }
}