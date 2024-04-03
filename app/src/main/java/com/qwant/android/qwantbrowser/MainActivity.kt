package com.qwant.android.qwantbrowser

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.qwant.android.qwantbrowser.storage.QwantClientProvider
import com.qwant.android.qwantbrowser.ui.QwantBrowserApp
import dagger.hilt.android.AndroidEntryPoint
import mozilla.components.support.base.android.NotificationsDelegate
import javax.inject.Inject
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var notificationsDelegate: NotificationsDelegate
    @Inject lateinit var clientProvider: QwantClientProvider

    // TODO Create keyboard controller class
    private var onKeyboardHiddenCallback: (() -> Unit)? = null
    private lateinit var rootView: View
    private var imm: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clientProvider.bindToActivity(this)
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
}
