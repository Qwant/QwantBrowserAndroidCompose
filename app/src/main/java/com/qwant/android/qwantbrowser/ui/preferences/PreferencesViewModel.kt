package com.qwant.android.qwantbrowser.ui.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.preferences.app.*
import com.qwant.android.qwantbrowser.stats.Piwik
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
    private val piwik: Piwik,
    tabsUseCases: TabsUseCases,
    qwantUseCases: QwantUseCases
) : ViewModel() {
    val appPreferences = appPreferencesRepository.flow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = AppPreferences.getDefaultInstance()
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

    fun updateAppearance(appearance: Appearance) { // TODO move appearance to app prefs
        viewModelScope.launch { appPreferencesRepository.updateAppearance(appearance) }
    }

    fun updateClearDataPreferences(preferences: ClearDataPreferences) {
        viewModelScope.launch { appPreferencesRepository.updateClearDataPreferences(preferences) }
    }

    fun updatePiwikOptout(optout: Boolean) {
        viewModelScope.launch {
            if (optout) { piwik.event("Tracking", "Off") }
            appPreferencesRepository.updatePiwikOptout(optout)
            if (!optout) { piwik.event("Tracking", "On") }
        }
    }

    val addTabsUseCase = tabsUseCases.addTab
    val openTestTabUseCase = qwantUseCases.openTestPageUseCase
}