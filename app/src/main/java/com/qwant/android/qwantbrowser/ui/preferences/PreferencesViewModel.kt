package com.qwant.android.qwantbrowser.ui.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.preferences.app.*
import com.qwant.android.qwantbrowser.preferences.frontend.*
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mozilla.components.concept.engine.Engine
import mozilla.components.feature.tabs.TabsUseCases
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val frontEndPreferencesRepository: FrontEndPreferencesRepository,
    tabsUseCases: TabsUseCases,
    qwantUseCases: QwantUseCases
) : ViewModel() {
    val appPreferences = appPreferencesRepository.flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AppPreferences.getDefaultInstance()
    )

    val frontEndPreferences = frontEndPreferencesRepository.flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = FrontEndPreferences.getDefaultInstance()
    )

    val clearDataPreferences = appPreferencesRepository.clearDataPreferencesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = ClearDataPreferences(
            browsingData = Engine.BrowsingData.select(Engine.BrowsingData.ALL),
            tabs = true,
            history = true
        )
    )

    fun updateToolbarPosition(position: ToolbarPosition) {
        viewModelScope.launch { appPreferencesRepository.updateToolbarPosition(position) }
    }

    fun updateHideToolbarOnScroll(hideOnScroll: Boolean) {
        viewModelScope.launch { appPreferencesRepository.updateHideToolbarOnScroll(hideOnScroll) }
    }

    fun updateTabsView(option: TabsViewOption) {
        viewModelScope.launch { appPreferencesRepository.updateTabsView(option) }
    }

    fun updateOpenLinksInApp(openInApp: Boolean) {
        viewModelScope.launch { appPreferencesRepository.updateOpenLinksInApp(openInApp) }
    }

    fun updateClearDataOnQuit(clear: Boolean) {
        viewModelScope.launch { appPreferencesRepository.updateClearDataOnQuit(clear) }
    }

    fun updateAppearance(appearance: Appearance) {
        viewModelScope.launch { frontEndPreferencesRepository.updateAppearance(appearance) }
    }

    fun updateCustomPageColor(color: CustomPageColor) {
        viewModelScope.launch { frontEndPreferencesRepository.updateCustomPageColor(color) }
    }

    fun updateCustomPageCharacter(character: CustomPageCharacter) {
        viewModelScope.launch { frontEndPreferencesRepository.updateCustomPageCharacter(character) }
    }

    fun updateShowNews(show: Boolean) {
        viewModelScope.launch { frontEndPreferencesRepository.updateShowNews(show) }
    }

    fun updateShowSponsor(show: Boolean) {
        viewModelScope.launch { frontEndPreferencesRepository.updateShowSponsor(show) }
    }

    fun updateAdultFilter(filter: AdultFilter) {
        viewModelScope.launch { frontEndPreferencesRepository.updateAdultFilter(filter) }
    }

    fun updateShowFavicons(show: Boolean) {
        viewModelScope.launch { frontEndPreferencesRepository.updateShowFavicons(show) }
    }

    fun updateOpenResultsInNewTab(openInNewTab: Boolean) {
        viewModelScope.launch { frontEndPreferencesRepository.updateOpenResultsInNewTab(openInNewTab) }
    }

    fun updateSearchResultRegion(region: String) {
        viewModelScope.launch { frontEndPreferencesRepository.updateSearchResultRegion(region) }
    }

    fun updateClearDataPreferences(preferences: ClearDataPreferences) {
        viewModelScope.launch { appPreferencesRepository.updateClearDataPreferences(preferences) }
    }

    val addTabsUseCase = tabsUseCases.addTab
    val openTestTabUseCase = qwantUseCases.openTestPageUseCase
}