package com.qwant.android.qwantbrowser.preferences.frontend

import android.content.Context
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView

object FrontEndPreferencesMigration {
    private const val SHARED_PREFS_NAME = "frontend_preferences"
    private val keysToMigrate = setOf(
        "pref_key_news_on_homepage"
    )

    fun create(context: Context) : SharedPreferencesMigration<FrontEndPreferences> {
        return SharedPreferencesMigration(
            context, SHARED_PREFS_NAME, keysToMigrate
        ) { sharedPrefs: SharedPreferencesView, currentData: FrontEndPreferences ->
            // TODO frontend preferences migrations
            currentData.toBuilder()
                .setShowNews(sharedPrefs.getBoolean("pref_key_news_on_homepage", true))
                .build()
        }
    }
}