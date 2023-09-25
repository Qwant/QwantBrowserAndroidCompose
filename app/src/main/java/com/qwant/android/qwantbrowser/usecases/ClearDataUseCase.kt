package com.qwant.android.qwantbrowser.usecases

import android.util.Log
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.preferences.app.ClearDataPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.storage.HistoryStorage
import mozilla.components.feature.tabs.TabsUseCases
import javax.inject.Inject

// TODO reforge clear data
class ClearDataUseCase @Inject constructor(
    private val prefs: AppPreferencesRepository,
    private val tabsUseCases: TabsUseCases,
    private val engine: Engine,
    private val historyStorage: HistoryStorage
) {
    operator fun invoke(clearDataPreferences: ClearDataPreferences, then: (Boolean) -> Unit = {}) {
        val clearHistoryJob: Job? = if (clearDataPreferences.history) {
            MainScope().launch {
                historyStorage.deleteEverything()
            }
        } else null

        if (clearDataPreferences.tabs) {
            tabsUseCases.removeAllTabs()
        } else {
            // Always remove private tabs, no matter clearDataPreferences
            tabsUseCases.removePrivateTabs()
        }

        val onBrowsingDataComplete: (Boolean) -> Unit = { browsingDataSuccess ->
            if (clearHistoryJob?.isActive == true) {
                clearHistoryJob.invokeOnCompletion { clearHistoryException ->
                    if (clearHistoryException != null) {
                        Log.e("QWANT_BROWSER", "Failed to clear history storage: ${clearHistoryException.message}")
                        then(false)
                    } else {
                        then(browsingDataSuccess)
                    }
                }
            } else {
                then(browsingDataSuccess)
            }
        }

        if (clearDataPreferences.browsingData.types != 0) {
            engine.clearData(
                clearDataPreferences.browsingData,
                onSuccess = { onBrowsingDataComplete(true) },
                onError = { onBrowsingDataComplete(false) }
            )
        } else {
            onBrowsingDataComplete(true)
        }
    }

    operator fun invoke(coroutineScope: CoroutineScope = MainScope(), then: (Boolean, Boolean) -> Unit = { _, _ -> }) {
        coroutineScope.launch {
            val clearDataPrefs = prefs.clearDataPreferencesFlow.first()
            invoke(clearDataPrefs) { success -> then(success, clearDataPrefs.tabs) }
        }
    }

    suspend operator fun invoke(then: (Boolean, Boolean) -> Unit = { _, _ -> }) {
        val clearDataPrefs = prefs.clearDataPreferencesFlow.first()
        invoke(clearDataPrefs) { success -> then(success, clearDataPrefs.tabs) }
    }
}