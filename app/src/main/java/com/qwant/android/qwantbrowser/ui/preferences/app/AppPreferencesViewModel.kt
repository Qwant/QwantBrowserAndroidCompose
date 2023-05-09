package com.qwant.android.qwantbrowser.ui.preferences.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.preferences.app.AppPreferences
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.preferences.frontend.*
import com.qwant.android.qwantbrowser.ui.browser.toolbar.HideOnScrollPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppPreferencesViewModel @Inject constructor(
    private val repository: AppPreferencesRepository
): ViewModel() {
    private val flow = repository.flow

    val preferencesState = flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AppPreferences.getDefaultInstance()
    )

    fun updateToolbarPosition(position: ToolbarPosition) {
        viewModelScope.launch { repository.updateToolbarPosition(position) }
    }

    fun updateHideToolbarOnScroll(shouldHide: Boolean) {
        viewModelScope.launch { repository.updateHideToolbarOnScroll(shouldHide) }
    }
}