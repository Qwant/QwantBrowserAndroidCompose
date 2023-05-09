package com.qwant.android.qwantbrowser.ui.preferences.frontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.preferences.frontend.*
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

    fun updateAppearance(appearance: Appearance) {
        viewModelScope.launch { repository.updateAppearance(appearance) }
    }

    fun updateCustomPageColor(color: CustomPageColor) {
        viewModelScope.launch { repository.updateCustomPageColor(color) }
    }

    fun updateCustomPageCharacter(character: CustomPageCharacter) {
        viewModelScope.launch { repository.updateCustomPageCharacter(character) }
    }

    fun updateShowSponsor(show: Boolean) {
        viewModelScope.launch { repository.updateShowSponsor(show) }
    }

    fun updateSearchResultRegion(region: String) {
        viewModelScope.launch { repository.updateSearchResultRegion(region) }
    }

    fun updateAdultFilter(filter: AdultFilter) {
        viewModelScope.launch { repository.updateAdultFilter(filter) }
    }

    fun updateShowFavicons(show: Boolean) {
        viewModelScope.launch { repository.updateShowFavicons(show) }
    }

    fun updateOpenResultsInNewTab(openInNewTab: Boolean) {
        viewModelScope.launch { repository.updateOpenResultsInNewTab(openInNewTab) }
    }

    fun updateVideosOnQwant(playOnQwant: Boolean) {
        viewModelScope.launch { repository.updateVideosOnQwant(playOnQwant) }
    }
}