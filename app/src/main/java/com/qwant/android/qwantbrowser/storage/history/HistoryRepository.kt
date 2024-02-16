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
import javax.inject.Singleton

// TODO add search terms history

@Singleton
class HistoryRepository @Inject constructor(
    private val db: HistoryDatabase
): HistoryStorage, SuggestionProvider {
    private val dao = db.historyDao()

    val hasHistoryFlow = dao.hasHistoryFlow()

    override fun canAddUri(uri: String): Boolean =
        !uri.isQwantUrl() && !uri.startsWith("moz-extension://")

    override suspend fun recordVisit(uri: String, visit: PageVisit) {
        dao.insertIfNeeded(Page(uri))
        dao.insert(Visit(uri, System.currentTimeMillis(), visit.visitType))
    }

    suspend fun recordVisitWithTimestamp(uri: String, visit: PageVisit, timestamp: Long) {
        dao.insertIfNeeded(Page(uri))
        dao.insert(Visit(uri, timestamp, visit.visitType))
    }

    override suspend fun recordObservation(uri: String, observation: PageObservation) {
        val page = dao.getPage(uri) ?: Page(uri)
        observation.title?.let { page.title = it }
        observation.previewImageUrl?.let { page.previewImageUrl = it }
        dao.upsert(page)
    }

    override suspend fun deleteEverything() =
        db.clearAllTables()

    override suspend fun deleteVisit(url: String, timestamp: Long) =
        dao.deleteVisitByKey(VisitKey(url, timestamp))

    override suspend fun deleteVisitsBetween(startTime: Long, endTime: Long) =
        dao.deleteVisitsBetween(startTime, endTime)

    override suspend fun deleteVisitsFor(url: String) =
        dao.deleteURI(url)

    override suspend fun deleteVisitsSince(since: Long) =
        dao.deleteVisitsSince(since)

    override suspend fun getDetailedVisits(
        start: Long,
        end: Long,
        excludeTypes: List<VisitType>
    ): List<VisitInfo> = dao.getVisitsDetailed(start, end, excludeTypes).getVisitInfos()

    override fun getSuggestions(query: String, limit: Int): List<SearchResult> =
        dao.getSuggestions(query, limit)
            .mapIndexed { index, page -> SearchResult(
                id = page.uri,
                url = page.uri,
                title = page.title,
                score = index // Not really relevant. Should rank it using levenshteinDistance
            ) }

    override suspend fun getSuggestions(text: String): List<Suggestion> =
        dao.getSuggestions(query = text, limit = 2) // TODO History suggestions limit should be a parameter
            .map { Suggestion.OpenTabSuggestion(
                provider = this,
                search = text,
                title = it.title,
                url = it.uri
            ) }

    override suspend fun getTopFrecentSites(
        numItems: Int,
        frecencyThreshold: FrecencyThresholdOption
    ): List<TopFrecentSiteInfo> {
        return listOf()
        // TODO history top frequent sites
        /* return (if (frecencyThreshold == FrecencyThresholdOption.NONE) dao.getTopFrecentSites(numItems)
        else dao.getTopFrecentSitesSkippingOneTimePages(numItems))
            .map { TopFrecentSiteInfo(it.uri, it.title) } */
    }

    override suspend fun getVisited(): List<String> =
        dao.getAllPages().map { it.uri }

    override suspend fun getVisited(uris: List<String>): List<Boolean> =
        uris.map { dao.getPage(it) != null }

    override suspend fun getVisitsPaginated(
        offset: Long,
        count: Long,
        excludeTypes: List<VisitType>
    ): List<VisitInfo> = dao.getVisitsPaginated(offset, count, excludeTypes).getVisitInfos()

    private fun Map<Page, List<Visit>>.getVisitInfos() = this
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

    /*
      Not applicable.
     */
    override suspend fun prune() {}
    override suspend fun runMaintenance(dbSizeLimit: UInt) {}
    override suspend fun warmUp() {}
}