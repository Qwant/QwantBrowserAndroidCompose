package com.qwant.android.qwantbrowser.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.qwant.android.qwantbrowser.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

data class QwantClientProvider(val client: String)

// TODO move saved client to internal datastore instead of shared prefs ?

@InstallIn(SingletonComponent::class)
@Module
object QwantClientProviderHiltModule {
    private val prefKey = "pref_key_saved_client"

    @Provides
    fun provideQwantClientProvider(@ApplicationContext context: Context): QwantClientProvider {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return QwantClientProvider(
            prefs.getString(prefKey, null)
            ?: context.getString(R.string.app_client_string).also {
                with(prefs.edit()) {
                    putString(prefKey, it)
                    apply()
                }
            }
        )
    }
}