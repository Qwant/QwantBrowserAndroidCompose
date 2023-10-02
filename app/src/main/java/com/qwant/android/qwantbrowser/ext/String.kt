package com.qwant.android.qwantbrowser.ext

import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import com.qwant.android.qwantbrowser.ui.browser.suggest.toAnnotatedStringRange
import com.qwant.android.qwantbrowser.ui.browser.suggest.toSuggestAnnotatedString
import java.net.URI
import java.net.URLDecoder

fun String.isQwantUrl(): Boolean {
    return this.startsWith("https://www.qwant.com")
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
        if (this.contains("?q=") || this.contains("&q=")) {
            return this.split("?q=", "&q=")[1].split("&")[0]
        }
    }
    return null
}

fun String.urlDecode(): String {
    return URLDecoder.decode(this, Charsets.UTF_8.name())
}

fun String.cleanUrl(): String {
    return this
        .removePrefix("http://")
        .removePrefix("https://")
        .removePrefix("www.")
        // .substringBefore('?')
}

@Composable
fun String.toCleanUrlAnnotatedString(color: Color): AnnotatedString {
    val cleaned = this.cleanUrl()
    val startIndex = cleaned.indexOf('?')

    return if (startIndex != -1) {
        val ab = AnnotatedString.Builder()
        ab.append(cleaned)
        ab.addStyle(SpanStyle(color), startIndex, cleaned.length)
        return ab.toAnnotatedString()
    } else AnnotatedString(cleaned)
}