package com.qwant.android.qwantbrowser.widget

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme
import com.qwant.android.qwantbrowser.widget.ui.Widget

class WidgetActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QwantBrowserTheme {
                Widget()
            }
        }
    }
}