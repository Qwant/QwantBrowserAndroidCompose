package com.qwant.android.qwantbrowser.ui.preferences

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qwant.android.qwantbrowser.preferences.app.*
import com.qwant.android.qwantbrowser.stats.Piwik
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mozilla.components.browser.engine.gecko.permission.GeckoSitePermissionsStorage
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.engine.permission.SitePermissions
import mozilla.components.concept.engine.permission.SitePermissionsStorage
import mozilla.components.feature.tabs.TabsUseCases
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val permissionsStorage: GeckoSitePermissionsStorage,
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

    var permissions: MutableStateFlow<PagingData<SitePermissions>> = MutableStateFlow(PagingData.empty())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            permissionsStorage.getSitePermissionsPaged().asPagingSourceFactory().let { source ->
                Pager<Int, SitePermissions>(
                    PagingConfig(pageSize = 50)
                ) {
                    source.invoke()
                }.flow
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
                    .collect {
                        permissions.value = it
                    }
            }
        }
    }

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

    fun updatePermissions(permissions: SitePermissions, permission: SitePermissionsStorage.Permission, value: SitePermissions.Status) {
        viewModelScope.launch(Dispatchers.IO) {
            val newPermission = getNewPermission(permissions, permission, value)
            permissionsStorage.update(newPermission, true)
            permissionsStorage.update(newPermission, false)
        }
    }

    private fun getNewPermission(permissions: SitePermissions, permission: SitePermissionsStorage.Permission, value: SitePermissions.Status): SitePermissions {
        val autoplayStatus = when (value) {
            SitePermissions.Status.BLOCKED -> SitePermissions.AutoplayStatus.BLOCKED
            SitePermissions.Status.NO_DECISION -> SitePermissions.AutoplayStatus.BLOCKED
            SitePermissions.Status.ALLOWED -> SitePermissions.AutoplayStatus.ALLOWED
        }
        return when (permission) {
            SitePermissionsStorage.Permission.MICROPHONE -> permissions.copy(microphone = value)
            SitePermissionsStorage.Permission.BLUETOOTH -> permissions.copy(bluetooth = value)
            SitePermissionsStorage.Permission.CAMERA -> permissions.copy(camera = value)
            SitePermissionsStorage.Permission.LOCAL_STORAGE -> permissions.copy(localStorage = value)
            SitePermissionsStorage.Permission.NOTIFICATION -> permissions.copy(notification = value)
            SitePermissionsStorage.Permission.LOCATION -> permissions.copy(location = value)
            SitePermissionsStorage.Permission.AUTOPLAY_AUDIBLE -> permissions.copy(autoplayAudible = autoplayStatus)
            SitePermissionsStorage.Permission.AUTOPLAY_INAUDIBLE -> permissions.copy(autoplayInaudible = autoplayStatus)
            SitePermissionsStorage.Permission.MEDIA_KEY_SYSTEM_ACCESS -> permissions.copy(mediaKeySystemAccess = value)
            SitePermissionsStorage.Permission.STORAGE_ACCESS -> permissions.copy(crossOriginStorageAccess = value)
            SitePermissionsStorage.Permission.LOCAL_DEVICE_ACCESS -> permissions.copy(localDeviceAccess = value)
            SitePermissionsStorage.Permission.LOCAL_NETWORK_ACCESS -> permissions.copy(localNetworkAccess = value)
        }
    }

    fun revokeAllPermissions(permissions: SitePermissions? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            if (permissions != null) {
                permissionsStorage.remove(permissions, true)
                permissionsStorage.remove(permissions, false)
            } else {
                permissionsStorage.removeAll()
            }
        }
    }
    val addTabsUseCase = tabsUseCases.addTab
    val openTestTabUseCase = qwantUseCases.openTestPageUseCase
}