package com.qwant.android.qwantbrowser.ext

fun String.isQwantUrl(): Boolean {
    return this.startsWith("https://www.qwant.com")
    // TODO add exceptions for maps, etc...
}