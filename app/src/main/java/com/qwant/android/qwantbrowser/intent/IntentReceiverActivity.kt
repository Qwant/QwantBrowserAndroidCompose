package com.qwant.android.qwantbrowser.intent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.qwant.android.qwantbrowser.MainActivity
import com.qwant.android.qwantbrowser.ext.useCases

class IntentReceiverActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        useCases.qwantUseCases.warmUp()

        val intent = intent?.let { Intent(it) } ?: Intent()

        val processor = QwantIntentProcessor(useCases.tabsUseCases, useCases.qwantUseCases)
        processor.process(intent)

        intent.setClassName(applicationContext, MainActivity::class.java.name)
        startActivity(intent)
        finish()
    }


}