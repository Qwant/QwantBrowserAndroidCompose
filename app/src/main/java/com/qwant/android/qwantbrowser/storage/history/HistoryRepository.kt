package com.qwant.android.qwantbrowser.storage.history

import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.SuggestionProvider
import mozilla.components.concept.storage.FrecencyThresholdOption
import mozilla.components.concept.storage.HistoryStorage
import mozilla.components.concept.storage.PageObservation
import mozilla.components.concept.storage.PageVisit
import mozilla.components.concept.storage.SearchResult
import mozilla.components.concept.storage.TopFrecentSiteInfo
import mozilla.components.concept.storage.VisitInfo
import mozilla.components.concept.storage.VisitType
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val db: HistoryDatabase
): HistoryStorage, SuggestionProvider {
    private val dao = db.historyDao()

    override fun canAddUri(uri: String): Boolean =
        !uri.isQwantUrl() && !uri.startsWith("moz-extension://")

    override suspend fun recordVisit(uri: String, visit: PageVisit) {
        dao.insertIfNeeded(Page(uri))
        dao.insert(Visit(uri, System.currentTimeMillis(), visit.visitType))
    }

    override suspend fun recordObservation(uri: String, observation: PageObservation) {
        val page = dao.getPage(uri) ?: Page(uri)
        observation.title?.let { page.title = it }
        observation.previewImageUrl?.let { page.previewImageUrl = it }
        dao.upsert(page)
    }

    override suspend fun deleteEverything() = db.clearAllTables()

    override suspend fun deleteVisit(url: String, timestamp: Long) =
        dao.deleteVisitByKey(VisitKey(url, timestamp))

    override suspend fun deleteVisitsBetween(startTime: Long, endTime: Long) =
        dao.deleteVisitsBetween(startTime, endTime)

    override suspend fun deleteVisitsFor(url: String) = dao.deleteURI(url)

    override suspend fun deleteVisitsSince(since: Long) = dao.deleteVisitsSince(since)

    override suspend fun getDetailedVisits(
        start: Long,
        end: Long,
        excludeTypes: List<VisitType>
    ): List<VisitInfo> = dao.getVisitsDetailed(start, end, excludeTypes)
        .flatMap { (page, visits) ->
            visits.map { visit ->
                VisitInfo(
                    page.uri,
                    page.title,
                    visit.time,
                    visit.type,
                    page.previewImageUrl,
                    false
                )
            }
        }

    override fun getSuggestions(query: String, limit: Int): List<SearchResult> {
        TODO("Not yet implemented")
    }

    override suspend fun getSuggestions(text: String): List<Suggestion> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopFrecentSites(
        numItems: Int,
        frecencyThreshold: FrecencyThresholdOption
    ): List<TopFrecentSiteInfo> {
        TODO("Not yet implemented")
    }

    override suspend fun getVisited(): List<String> = dao.getAllPages().map { it.uri }

    override suspend fun getVisited(uris: List<String>): List<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getVisitsPaginated(
        offset: Long,
        count: Long,
        excludeTypes: List<VisitType>
    ): List<VisitInfo> {
        TODO("Not yet implemented")
    }

    /*
      Not applicable.
     */
    override suspend fun prune() {}
    override suspend fun runMaintenance(dbSizeLimit: UInt) {}
    override suspend fun warmUp() {}
}