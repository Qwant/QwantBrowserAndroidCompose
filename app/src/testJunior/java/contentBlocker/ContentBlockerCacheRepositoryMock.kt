package contentBlocker

import com.qwant.android.qwantbrowser.contentBlocker.cacheDb.BlockListType
import com.qwant.android.qwantbrowser.contentBlocker.cacheDb.ContentBlockerCacheRepositoryInterface
import com.qwant.android.qwantbrowser.contentBlocker.cacheDb.Domain
import com.qwant.android.qwantbrowser.contentBlocker.cacheDb.TabId
import com.qwant.android.qwantbrowser.contentBlocker.cacheDb.Url

class ContentBlockerCacheRepositoryMock(): ContentBlockerCacheRepositoryInterface {
    override suspend fun getUrl(hash: String, type: BlockListType): Url? = null
    override suspend fun getDomain(hash: String, type: BlockListType): Domain? = null
    override suspend fun getTabId(tabId: String): TabId? = null
    override suspend fun deleteTabId(tabId: TabId) {}
    override suspend fun saveUrl(hash: String, blocked: Boolean, type: BlockListType) {}
    override suspend fun saveDomain(hash: String, blocked: Boolean, type: BlockListType) {}
    override suspend fun saveTabId(tabId: TabId) {}
    override suspend fun runMaintenance() {}
}