package com.qwant.android.qwantbrowser.ext

import android.content.Context
import android.content.ContextWrapper
import com.qwant.android.qwantbrowser.MainActivity
import com.qwant.android.qwantbrowser.QwantApplication
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases


/**
 * Get the current Activity object from a context.
 */
val Context.activity: MainActivity?
    get() = when (this) {
        is MainActivity -> this
        is ContextWrapper -> baseContext.activity
        else -> null
    }

/**
 * Get the BrowserApplication object from a context.
 */
val Context.application: QwantApplication
    get() = applicationContext as QwantApplication

/**
 * Get the mozac core of this application.
 */
val Context.core: Core
    get() = application.core

/**
 * Get the use cases of this application.
 */
val Context.useCases: UseCases
    get() = application.useCases