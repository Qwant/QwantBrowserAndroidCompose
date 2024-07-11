package com.qwant.android.qwantbrowser.contentBlocker

import javax.inject.Inject

// TODO create common interface for ContentBlockerState
class ContentBlockerState @Inject constructor() {
    enum class Status {
        ALLOWED, BLOCKED, CHECKING
    }

    val status = Status.ALLOWED

    fun isTabBlocked(tabId: String? = null): Boolean = false
}