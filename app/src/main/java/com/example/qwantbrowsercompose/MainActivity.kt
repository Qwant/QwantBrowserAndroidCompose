package com.example.qwantbrowsercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.qwantbrowsercompose.ui.QwantBrowserApp
import com.example.qwantbrowsercompose.ui.theme.QwantBrowserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QwantBrowserApp()
        }
    }
}