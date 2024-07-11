package com.qwant.android.qwantbrowser.contentBlocker.cacheDb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.qwant.android.qwantbrowser.contentBlocker.ContentBlockerState

@Entity(tableName = "domains")
data class Domain(
    @PrimaryKey(autoGenerate = false)
    val hash: String,
    val blocked: Boolean,
    val time: Long
)

@Entity(tableName = "urls")
data class Url(
    @PrimaryKey(autoGenerate = false)
    val hash: String,
    val blocked: Boolean,
    val time: Long
)

@Entity(tableName = "tab_ids")
data class TabId(
    @PrimaryKey(autoGenerate = false)
    val tabId: String,
    val blocked: Boolean,
    val reason: ContentBlockerState.BlockReason,
    val url: String
)