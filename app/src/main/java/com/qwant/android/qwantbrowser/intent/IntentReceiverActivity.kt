package com.qwant.android.qwantbrowser.intent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qwant.android.qwantbrowser.MainActivity
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import dagger.hilt.android.AndroidEntryPoint
import mozilla.components.feature.tabs.TabsUseCases
import javax.inject.Inject

@AndroidEntryPoint
class IntentReceiverActivity: AppCompatActivity() {
    @Inject lateinit var tabsUseCases: TabsUseCases
    @Inject lateinit var qwantUseCases: QwantUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent?.let { Intent(it) } ?: Intent()

        val processor = QwantIntentProcessor(tabsUseCases, qwantUseCases)
        processor.process(intent)

        intent.setClassName(applicationContext, MainActivity::class.java.name)
        startActivity(intent)
        finish()
    }
}