package com.qwant.android.qwantbrowser.ext

import java.net.URLDecoder

fun String.isQwantUrl(): Boolean {
    return this.startsWith("https://www.qwant.com")
    // TODO add exceptions for maps, etc... in qwant url detection
}

fun String.isQwantSERPUrl(): Boolean {
    return this.isQwantUrl()
            && (this.contains("&s=") || this.contains("?s="))
}

fun String.getQwantSERPSearch(): String? {
    if (this.isQwantSERPUrl()) {
        var searchStart = this.indexOf("&q=")
        if (searchStart == -1) searchStart = this.indexOf("?q=")
        return if (searchStart != -1) {
            var searchEnd = this.indexOf('&', searchStart + 3)
            if (searchEnd == -1) searchEnd = this.length
            this.substring(searchStart + 3, searchEnd).urlDecode()
        } else null
    }
    return null
}

/**
 * Translates the string into {@code application/x-www-form-urlencoded} string.
 */
fun String.urlDecode(): String {
    return URLDecoder.decode(this, Charsets.UTF_8.name())
}