package com.example.qwantbrowsercompose.preferences.frontend

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile


object FrontEndPreferencesFactory {
    private const val FILENAME = "prefs_frontend.pb"

    fun create(context: Context): DataStore<FrontEndPreferences> {
        return DataStoreFactory.create(
            serializer = FrontEndPreferencesSerializer,
            produceFile = { context.dataStoreFile(FILENAME) },
            migrations = listOf(FrontEndPreferencesMigration.create(context))
        )
    }
}
