package com.qwant.android.qwantbrowser

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.qwant.android.qwantbrowser.ext.core
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import com.qwant.android.qwantbrowser.ui.QwantBrowserApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var frontEndPreferencesRepository: FrontEndPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QwantBrowserApp()
        }
    }

    // TODO replace legacy preferences
    /* private fun checkFirstLaunch() {
        val prefkeyFirstLaunch = resources.getString(R.string.pref_key_first_launch)
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val firstLaunch = prefs.getBoolean(prefkeyFirstLaunch, true)
        if (firstLaunch) {
            with(prefs.edit()) {
                putBoolean(resources.getString(R.string.pref_key_show_onboarding), true)
                // TODO add prefkey for first request
                //   putBoolean(resources.getString(R.string.pref_key_first_request), true)
                putBoolean(prefkeyFirstLaunch, false)
                commit()
            }
        }
    } */

    fun quit() {
        finishAffinity()
        exitProcess(0)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        AppCompatDelegate.getApplicationLocales()[0]?.language?.let {
            lifecycleScope.launch {
                frontEndPreferencesRepository.updateInterfaceLanguage(it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        application.core.historyStorage.run { this.persist() }
        application.core.bookmarkStorage.run { this.persist() }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        application.core.historyStorage.run { this.restore() }
        application.core.bookmarkStorage.run { this.restore() }
    }
}
