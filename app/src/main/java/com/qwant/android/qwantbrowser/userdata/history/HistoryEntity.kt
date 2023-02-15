package com.qwant.android.qwantbrowser.userdata.history

import androidx.room.Entity
import androidx.room.PrimaryKey
import mozilla.components.concept.storage.VisitType

@Entity(tableName = "pages")
data class Page(
    @PrimaryKey(autoGenerate = false)
    val uri: String,

    val title: String,
    val previewImageUrl: String?
)

@Entity(tableName = "visits", primaryKeys = ["pageUri", "time"])
data class Visit(
    val pageUri: String,
    val time: Long,
    val type: VisitType,
)