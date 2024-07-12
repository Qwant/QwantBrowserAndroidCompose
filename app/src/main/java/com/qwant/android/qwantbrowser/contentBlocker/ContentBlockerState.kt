package com.qwant.android.qwantbrowser.contentBlocker

import kotlinx.coroutines.Job

open class ContentBlockerState {
    enum class Status {
        ALLOWED, BLOCKED, CHECKING
    }

    enum class BlockReason {
        NONE, INVALID, ERROR, IP, DOMAIN, URL
    }

    open fun getStatus(tabId: String? = null) = Status.ALLOWED
    open fun getBlockReason(tabId: String? = null) = BlockReason.NONE

    open fun check(url: String): Job? = null
    open fun onQwant(): Job? = null
    open fun cancel() = Unit
}

