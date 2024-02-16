package com.qwant.android.qwantbrowser.storage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.qwant.android.qwantbrowser.BuildConfig
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.isPackageInstalled
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


// TODO move saved client to internal datastore instead of shared prefs ?
@Singleton
class QwantClientProvider @Inject constructor(@ApplicationContext context: Context) {
    val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private var shouldCheckCompanion: Boolean = false

    private val client = MutableStateFlow(
        prefs.getString(PREF_KEY, null)?.also {
            shouldCheckCompanion = false
        }
        ?: context.getString(R.string.app_client_string).also {
            saveLocally(it)
            shouldCheckCompanion = true
        }
    )

    val clientState = client.asStateFlow()

    fun bindToActivity(activity: AppCompatActivity) {
        if (shouldCheckCompanion) {
            activity.registerForActivityResult(QwantCompanionResultContract()) {
                it?.let { result ->
                    client.update { result }
                    saveLocally(result)
                }
            }.launch(Unit)
            shouldCheckCompanion = false
        }
    }

    private fun saveLocally(c: String) {
        with(prefs.edit()) {
            putString(PREF_KEY, c)
            apply()
        }
    }

    private class QwantCompanionResultContract: ActivityResultContract<Unit, String?>() {
        override fun createIntent(context: Context, input: Unit): Intent {
            return Intent("com.qwant.android.intent.action.PARTNER")
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode == Activity.RESULT_OK) {
                return intent?.getStringExtra("partner_client")
            }
            return null
        }

        override fun getSynchronousResult(context: Context, input: Unit): SynchronousResult<String?>? {
            if (!context.isPackageInstalled(BuildConfig.COMPANION_ID)) {
                return SynchronousResult(null)
            }
            return null
        }
    }

    companion object {
        private const val PREF_KEY = "pref_key_saved_client"
    }
}
