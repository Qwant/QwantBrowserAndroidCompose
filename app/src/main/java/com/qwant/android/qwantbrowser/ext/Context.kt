package com.qwant.android.qwantbrowser.ext

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.qwant.android.qwantbrowser.QwantActivity
import com.qwant.android.qwantbrowser.QwantApplication
import com.qwant.android.qwantbrowser.R
import org.mozilla.geckoview.BuildConfig
import java.util.Locale
import mozilla.components.feature.downloads.R as downloadsR


/**
 * Get the current Activity object from a context.
 */
val Context.activity: QwantActivity?
    get() = when (this) {
        is QwantActivity -> this
        is ContextWrapper -> baseContext.activity
        else -> null
    }

/**
 * Get the QwantApplication object from a context.
 */
val Context.application: QwantApplication
    get() = applicationContext as QwantApplication


fun Context.openAppSystemSettings() = startActivity(
    Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null))
)

fun Context.openFileInApp(contentUri: Uri, contentType: String?) {
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contentUri, contentType)
            flags = Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        val chooserIntent = Intent.createChooser(
            intent,
            this.getString(downloadsR.string.mozac_feature_downloads_third_party_app_chooser_dialog_title),
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        this.startActivity(chooserIntent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            this,
            this.getString(downloadsR.string.mozac_feature_downloads_unable_to_open_third_party_app),
            Toast.LENGTH_LONG
        ).show()
    }
}

fun Context.openAppStorePage() = startActivity(
    Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.store_url)))
)

@RequiresApi(Build.VERSION_CODES.N)
fun Context.openDefaultAppsSystemSettings() {
    startActivity(Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS))
}

fun Context.isPackageInstalled(packageToFind: String) = try {
    this.packageManager.getPackageInfo(packageToFind, 0)
    true
} catch (e: PackageManager.NameNotFoundException) {
    false
}

fun Context.selectedLocale(): Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    this.resources.configuration.locales.get(0)
} else {
    @Suppress("DEPRECATION")
    this.resources.configuration.locale
}