package com.example.qwantbrowsercompose.preferences.frontend

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject


private const val LOGTAG: String = "FrontEndPreferencesRepo"

class FrontEndPreferencesRepository @Inject constructor(
    private val datastore: DataStore<FrontEndPreferences>
) {
    val flow: Flow<FrontEndPreferences> = datastore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(LOGTAG, "Error reading frontend preferences.", exception)
                emit(FrontEndPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun updateShowNews(show: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setShowNews(show).build()
        }
    }
}