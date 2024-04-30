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
class MainActivity : QwantActivity() {
    @Inject lateinit var notificationsDelegate: NotificationsDelegate
    @Inject lateinit var clientProvider: QwantClientProvider

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
        this.bindRootView(v.rootView)
    }
}
