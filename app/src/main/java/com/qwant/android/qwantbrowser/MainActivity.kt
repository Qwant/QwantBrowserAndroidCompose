package com.qwant.android.qwantbrowser

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.qwant.android.qwantbrowser.legacy.bookmarks.BookmarksStorage
import com.qwant.android.qwantbrowser.legacy.history.History
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import com.qwant.android.qwantbrowser.ui.QwantBrowserApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mozilla.components.concept.storage.HistoryStorage
import mozilla.components.support.base.android.NotificationsDelegate
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var frontEndPreferencesRepository: FrontEndPreferencesRepository
    @Inject lateinit var historyStorage: HistoryStorage
    @Inject lateinit var bookmarkStorage: BookmarksStorage
    @Inject lateinit var notificationsDelegate: NotificationsDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationsDelegate.bindToActivity(this)

        setContent {
            QwantBrowserApp()
        }
    }

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
        (historyStorage as History).run { this.persist() }
        bookmarkStorage.run { this.persist() }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        (historyStorage as History).run { this.restore() }
        bookmarkStorage.run { this.restore() }
    }
}
