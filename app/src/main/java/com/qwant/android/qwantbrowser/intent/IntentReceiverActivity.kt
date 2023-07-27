package com.qwant.android.qwantbrowser.intent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.qwant.android.qwantbrowser.MainActivity
import com.qwant.android.qwantbrowser.ext.useCases
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import mozilla.components.feature.pwa.intent.WebAppIntentProcessor

class IntentReceiverActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val processor = QwantIntentProcessor(useCases.tabsUseCases, useCases.qwantUseCases)

        super.onCreate(savedInstanceState)

        val intent = intent?.let { Intent(it) } ?: Intent()

        if (intent.action == WebAppIntentProcessor.ACTION_VIEW_PWA || intent.action == OLD_SHORTCUTS_ACTION) {
            intent.action = Intent.ACTION_VIEW
        }

        MainScope().launch {
            // TODO add WebAppIntentProcessor when supporting special display for pwas
            processor.process(intent)

            intent.setClassName(applicationContext, MainActivity::class.java.name)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val OLD_SHORTCUTS_ACTION = "org.mozilla.gecko.BOOKMARK"
    }
}