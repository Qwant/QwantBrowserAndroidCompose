package com.qwant.android.qwantbrowser.ext

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import java.net.URI
import java.net.URLDecoder

fun String.isQwantUrl(): Boolean {
    // TODO use regex
    return (this.startsWith("https://www.qwant.com")
            || this.startsWith("http://www.qwant.com"))
            && !this.startsWith("https://www.qwant.com/maps")
            && !this.startsWith("https://www.qwant.com/flight")
}

fun String.isQwantUrlValid(): Boolean {
    return this.indexOf("&qbc=1") != -1
}

fun String.isQwantSERPUrl(): Boolean {
    return this.isQwantUrl()
            && (this.contains("&q=") || this.contains("?q="))
}

fun String.getQwantSERPSearch(): String? {
    if (this.isQwantSERPUrl()) {
        return this.split("?q=", "&q=")[1].split("&")[0]
    }
    return null
}

fun String.urlDecode(): String {
    return URLDecoder.decode(this, Charsets.UTF_8.name())
}

fun String.toCleanUrl(): String {
    return this
        .removePrefix("http://")
        .removePrefix("https://")
        // TODO use 'hostWithoutCommonPrefixes' from mozilla support ktx
        //  https://github.com/mozilla-mobile/firefox-android/blob/main/android-components/components/support/ktx/src/main/java/mozilla/components/support/ktx/android/net/Uri.kt
        .removePrefix("www.")
        // .substringBefore('?')
}

fun String.toCleanHost(): String {
    // TODO use 'toShortUrl' from mozilla support ktx there
    //  https://github.com/mozilla-mobile/firefox-android/blob/main/android-components/components/support/ktx/src/main/java/mozilla/components/support/ktx/kotlin/String.kt
    return try {
        URI(this).normalize().host.removePrefix("www.")
    } catch (e: Exception) {
        Log.w("QB", "Could not normalize url and get the host. Fallback to empty string for security concerns")
        ""
    }
}

@Composable
fun String.toCleanUrlAnnotatedString(color: Color): AnnotatedString {
    val cleaned = this.toCleanUrl()
    val startIndex = cleaned.indexOf('?')

    return if (startIndex != -1) {
        return AnnotatedString.Builder().apply {
            append(cleaned)
            addStyle(SpanStyle(color), startIndex, cleaned.length)
        }.toAnnotatedString()
    } else AnnotatedString(cleaned)
}