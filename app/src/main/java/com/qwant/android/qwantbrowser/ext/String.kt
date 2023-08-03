package com.qwant.android.qwantbrowser.ext

import android.util.Log
import java.net.URLDecoder

fun String.isQwantUrl(): Boolean {
    return this.startsWith("https://www.qwant.com")
            && !this.startsWith("https://www.qwant.com/maps")
            && !this.startsWith("https://www.qwant.com/flight")
}

fun String.isQwantUrlValid(): Boolean {
    return this.indexOf("&qbc=1") != -1
    // TODO add exceptions for maps, etc... in qwant url detection
}

fun String.isQwantSERPUrl(): Boolean {
    return this.isQwantUrl()
            && (this.contains("&q=") || this.contains("?q="))
}

fun String.getQwantSERPSearch(): String? {
    Log.d("QB_INTERCEPT", "get search term")
    if (this.isQwantSERPUrl()) {
        Log.d("QB_INTERCEPT", "is serp")
        var searchStart = this.indexOf("&q=")
        if (searchStart == -1) searchStart = this.indexOf("?q=")
        Log.d("QB_INTERCEPT", "search start: $searchStart")
        return if (searchStart != -1) {
            var searchEnd = this.indexOf('&', searchStart + 3)
            if (searchEnd == -1) searchEnd = this.length
            Log.d("QB_INTERCEPT", "search start: $searchEnd")
            val a = this.substring(searchStart + 3, searchEnd).urlDecode()
            Log.d("QB_INTERCEPT", "search: $a")
            a
        } else null
    }
    Log.d("QB_INTERCEPT", "not a serp")
    return null
}

/**
 * Translates the string into {@code application/x-www-form-urlencoded} string.
 */
fun String.urlDecode(): String {
    return URLDecoder.decode(this, Charsets.UTF_8.name())
}