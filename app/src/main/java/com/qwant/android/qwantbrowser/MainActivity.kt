package com.qwant.android.qwantbrowser

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.qwant.android.qwantbrowser.legacy.bookmarks.BookmarksStorage
import com.qwant.android.qwantbrowser.legacy.history.History
import com.qwant.android.qwantbrowser.migration.MigrationUtility
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
    @Inject lateinit var migrationUtility: MigrationUtility

    // TODO Create keyboard controller class
    private var onKeyboardHiddenCallback: (() -> Unit)? = null
    private lateinit var rootView: View
    private var imm: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationsDelegate.bindToActivity(this)

        val v = ComposeView(this).apply {
            setContent {
                QwantBrowserApp()
            }
        }
        setContentView(v)

        rootView = v.rootView
        imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)

        ViewCompat.setOnApplyWindowInsetsListener(v.rootView) { view, insets ->
            if (!insets.isVisible(WindowInsetsCompat.Type.ime())) {
                onKeyboardHiddenCallback?.invoke()
            }
            ViewCompat.onApplyWindowInsets(view, insets)
        }

        migrationUtility.checkMigrations()
    }

    fun forceHideKeyboard() {
        imm?.hideSoftInputFromWindow(rootView.windowToken, 0)
    }

    fun registerOnKeyboardHiddenCallback(callback: () -> Unit) {
        onKeyboardHiddenCallback = callback
    }

    fun unregisterOnKeyboardHiddenCallback() {
        onKeyboardHiddenCallback = null
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
        bookmarkStorage.run { this.persist() }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        bookmarkStorage.run { this.restore() }
    }
}
