package com.qwant.android.qwantbrowser


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import com.qwant.android.qwantbrowser.ext.core
import com.qwant.android.qwantbrowser.legacy.onboarding.OnboardingActivity
import com.qwant.android.qwantbrowser.ui.QwantBrowserApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @Inject lateinit var localeManager: LocaleManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.action == "CLOSE_APP") { // should be called with flag Intent.FLAG_ACTIVITY_NEW_TASK
            this.quitApp()
            return
        }

        runBlocking { localeManager.setLocale() }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                localeManager.watchLocale()
            }
        }

        setContent {
            QwantBrowserApp(intent.action)
        }

        checkFirstLaunch()
        launchOnboardingIfNecessary() // TODO replace onboarding activity by compose component
    }

    // TODO replace legacy preferences
    private fun checkFirstLaunch() {
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
    }

    // Legacy onboarding
    private fun launchOnboardingIfNecessary() {
        val prefkey = resources.getString(R.string.pref_key_show_onboarding)
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val shouldShowOnboarding = prefs.getBoolean(prefkey, false)
        if (shouldShowOnboarding) {
            val i = Intent(this, OnboardingActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(i)
        }
    }

    private fun quitApp() {
        /* QwantUtils.clearDataOnQuit(this,
            success = {
                Toast.makeText(this, R.string.cleardata_done, Toast.LENGTH_LONG).show()
                this.trueQuit()
            },
            error = {
                if (it.message == "disabled") {
                    this.trueQuit()
                } else {
                    Toast.makeText(this, R.string.cleardata_failed, Toast.LENGTH_LONG).show()
                }
            }
        ) */
        // TODO Automatic zap on quit if active
        trueQuit()
    }

    private fun trueQuit() {
        finishAffinity()
        exitProcess(0)
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
