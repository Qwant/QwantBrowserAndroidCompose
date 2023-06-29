package com.qwant.android.qwantbrowser.ext

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
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
 * Get the QwantApplication object from a context.
 */
val Context.application: QwantApplication
    get() = applicationContext as QwantApplication

/**
 * Get the mozac core of this application.
 */
val Context.core: Core
    get() = application.mozac

/**
 * Get the use cases of this application.
 */
val Context.useCases: UseCases
    get() = application.useCases


fun Context.openAppSystemSettings() = startActivity(
    Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
)

fun Context.openFileInApp(contentUri: Uri, contentType: String?) {
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contentUri, contentType)
            flags = Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        val chooserIntent = Intent.createChooser(intent, "Open with").apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        this.startActivity(chooserIntent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "Could not find any app to open this file", Toast.LENGTH_LONG).show()
    }
}

fun Context.openAppStorePage() = startActivity(
    Intent("android.intent.action.VIEW", Uri.parse(
        "https://play.google.com/store/apps/details?id=com.qwant.liberty"
        // if (BuildConfig.BUILD_TYPE == "appgallery") "market://details?id=com.qwant.liberty"
        // else "https://play.google.com/store/apps/details?id=com.qwant.liberty"
    ))
)

fun Context.openDefaultAppsSystemSettings() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        startActivity(Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS))
    } else {
        Log.e("DEFAULT_BROWSER", "Android version too old")
    }
}
