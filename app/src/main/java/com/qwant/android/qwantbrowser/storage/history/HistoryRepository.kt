package com.qwant.android.qwantbrowser.storage.history

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepository @Inject constructor(db: HistoryDatabase) {
    private val dao = db.historyDao()

    suspend fun insert(page: Page) = dao.insert(page)
    fun getAllPages(): Flow<List<Page>> = dao.getAllPages()
}