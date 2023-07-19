package com.qwant.android.qwantbrowser.preferences.app

import android.content.Context
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView

object AppPreferencesMigration {
    private const val SHARED_PREFS_NAME = "app_preferences"
    private val keysToMigrate = setOf(
        "pref_key_news_on_homepage"
    )

    fun create(context: Context) : SharedPreferencesMigration<AppPreferences> {
        return SharedPreferencesMigration(
            context, SHARED_PREFS_NAME, keysToMigrate
        ) { sharedPrefs: SharedPreferencesView, currentData: AppPreferences ->
            // TODO app preferences migrations
            currentData.toBuilder()
                // .setShowNews(sharedPrefs.getBoolean("pref_key_news_on_homepage", true))
                .build()
        }
    }
}