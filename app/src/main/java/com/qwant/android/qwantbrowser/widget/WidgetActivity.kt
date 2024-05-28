package com.qwant.android.qwantbrowser.widget

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.ui.platform.ComposeView
import com.qwant.android.qwantbrowser.QwantActivity
import com.qwant.android.qwantbrowser.intent.IntentReceiverActivity
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme
import com.qwant.android.qwantbrowser.widget.ui.Widget
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WidgetActivity: QwantActivity() {
    private val viewModel: WidgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v = ComposeView(this).apply {
            setContent {
                QwantBrowserTheme {
                    Widget(viewModel)
                }
            }
        }
        setContentView(v)
        this.bindRootView(v.rootView)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        viewModel.resetSearch()
    }

    fun openFullBrowsingActivity(url: String) {
        startActivity(
            Intent(this, IntentReceiverActivity::class.java).also {
                it.setPackage(packageName)
                it.action = Intent.ACTION_VIEW
                it.data = Uri.parse(url)
            }
        )
    }
}