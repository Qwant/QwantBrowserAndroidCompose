package com.qwant.android.qwantbrowser.contentBlocker.cacheDb

import com.qwant.android.qwantbrowser.contentBlocker.ContentBlockerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentBlockerCacheRepository @Inject constructor(
    db: ContentBlockerCacheDatabase
) {
    private val dao = db.contentBlockerCacheDao()

    suspend fun getUrl(hash: String, type: BlockListType): Url? = withContext(Dispatchers.IO) {
        return@withContext dao.getUrl(hash, type)
    }

    suspend fun getDomain(hash: String, type: BlockListType): Domain? = withContext(Dispatchers.IO) {
        return@withContext dao.getDomain(hash, type)
    }

    suspend fun getTabId(tabId: String): TabId? = withContext(Dispatchers.IO) {
        return@withContext dao.getTabId(tabId)
    }

    suspend fun deleteTabId(tabId: TabId) = withContext(Dispatchers.IO) {
        dao.deleteTabId(tabId)
    }

    suspend fun saveUrl(hash: String, blocked: Boolean, type: BlockListType) = withContext(Dispatchers.IO) {
        dao.insert(Url(hash, type, blocked, System.currentTimeMillis()))
    }

    suspend fun saveDomain(hash: String, blocked: Boolean, type: BlockListType) = withContext(Dispatchers.IO) {
        dao.insert(Domain(hash, type, blocked, System.currentTimeMillis()))
    }

    suspend fun saveTabId(tabId: TabId) {
        dao.upsert(tabId)
    }

    suspend fun runMaintenance() = withContext(Dispatchers.IO) {
        val validTime = System.currentTimeMillis() - CACHE_VALIDITY_LIFETIME
        dao.deleteDomainsBefore(validTime)
        dao.deleteUrlsBefore(validTime)
    }

    companion object {
        const val CACHE_VALIDITY_LIFETIME: Long = 48 * 60 * 60 * 1000 // 48h in milliseconds
    }
}