package com.qwant.android.qwantbrowser.preferences.app

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.*
import mozilla.components.concept.engine.Engine
import java.io.IOException
import javax.inject.Inject


private const val LOGTAG: String = "FrontEndPreferencesRepo"

data class ClearDataPreferences(
    val browsingData: Engine.BrowsingData,
    val tabs: Boolean,
    val history: Boolean
)

// Not needed, while this class remains stateless
// @Module
// @InstallIn(ActivityRetainedComponent::class)
class AppPreferencesRepository @Inject constructor(
    private val datastore: DataStore<AppPreferences>
) {
    val flow: Flow<AppPreferences> = datastore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(LOGTAG, "Error reading frontend preferences.", exception)
                emit(AppPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    val clearDataPreferencesFlow = flow
        .map { ClearDataPreferences(
            browsingData = Engine.BrowsingData.select(it.clearDataBrowsingdata),
            tabs = it.clearDataTabs,
            history = it.clearDataHistory
        )}

    suspend fun updateToolbarPosition(position: ToolbarPosition) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setToolbarPosition(position).build()
        }
    }

    suspend fun updateHideToolbarOnScroll(hideOnScroll: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setHideToolbarOnScroll(hideOnScroll).build()
        }
    }

    suspend fun updateTabsView(option: TabsViewOption) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setTabsView(option).build()
        }
    }

    suspend fun updateOpenLinksInApp(openInApp: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setOpenLinksInApp(openInApp).build()
        }
    }

    suspend fun updateClearDataOnQuit(clear: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setClearDataOnQuit(clear).build()
        }
    }

    suspend fun updateClearDataPreferences(preferences: ClearDataPreferences) {
        Log.d("QB", "Clear data browsing set to " + preferences.browsingData.types)
        datastore.updateData { prefs ->
            prefs.toBuilder()
                .setClearDataBrowsingdata(preferences.browsingData.types)
                .setClearDataTabs(preferences.tabs)
                .setClearDataHistory(preferences.history)
                .build()
        }
    }
}