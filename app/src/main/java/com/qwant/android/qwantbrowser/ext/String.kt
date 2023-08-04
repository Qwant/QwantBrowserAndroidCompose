package com.qwant.android.qwantbrowser.ext

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