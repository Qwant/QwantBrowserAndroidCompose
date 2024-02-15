package com.qwant.android.qwantbrowser.legacy.history

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.VisibleForTesting
import mozilla.components.concept.storage.*
import mozilla.components.support.utils.StorageUtils.levenshteinDistance
import java.io.*
import java.util.*
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.SuggestionProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

data class Visit(val timestamp: Long, val type: VisitType): Serializable
data class PageObservation(val title: String?) : Serializable

const val AUTOCOMPLETE_SOURCE_NAME = "history"

class History(val context: Context) : HistoryStorage, SuggestionProvider {
    @VisibleForTesting
    internal var pages: HashMap<String, MutableList<Visit>> = linkedMapOf()
    @VisibleForTesting
    internal var pageMeta: HashMap<String, PageObservation> = hashMapOf()

    var size = pages.size
    var onSizeChanged: ((Int) -> Unit)? = null


    override suspend fun prune() {}

    override suspend fun recordVisit(uri: String, visit: PageVisit) {
        if (visit.visitType == VisitType.LINK) {
            recordVisit(uri, visit, System.currentTimeMillis())
            onSizeChanged?.invoke(pages.size)
        }
    }

    private fun recordVisit(uri: String, visit: PageVisit, timestamp: Long) {
        synchronized(pages) {
            if (!pages.containsKey(uri)) {
                pages[uri] = mutableListOf(Visit(timestamp, visit.visitType))
            } else {
                pages[uri]!!.add(Visit(timestamp, visit.visitType))
                onSizeChanged?.invoke(pages.size)
            }
        }
    }

    override suspend fun recordObservation(uri: String, observation: mozilla.components.concept.storage.PageObservation) = synchronized(pageMeta) {
        if (observation.title != null)
            pageMeta[uri] = PageObservation(observation.title)
    }

    override suspend fun getVisited(uris: List<String>): List<Boolean> = synchronized(pages) {
        return uris.map {
            if (pages[it] != null && pages[it]!!.size > 0) {
                return@map true
            }
            return@map false
        }
    }

    override suspend fun getVisited(): List<String> = synchronized(pages) {
        return pages.keys.toList()
    }

    override suspend fun getVisitsPaginated(offset: Long, count: Long, excludeTypes: List<VisitType>): List<VisitInfo> {
        val visits = mutableListOf<VisitInfo>()

        var i = 0
        val endIndex = offset + count

        val sortedPages = pages.toList().sortedByDescending { (_, allVisits) ->
            allVisits.sortByDescending { visit -> visit.timestamp }
            allVisits[0].timestamp
        }.toMap()

        sortedPages.forEach {
            if (i in offset until endIndex) {
                it.value.sortByDescending { visit -> visit.timestamp }
                visits.add(VisitInfo(
                    url = it.key,
                    title = pageMeta[it.key]?.title,
                    visitTime = it.value[0].timestamp,
                    visitType = it.value[0].type,
                    previewImageUrl = null,
                    isRemote = false
                ))
            } else if (i >= endIndex) {
                return visits
            }
            i++
        }

        return visits
    }

    override suspend fun getDetailedVisits(
            start: Long,
            end: Long,
            excludeTypes: List<VisitType>
    ): List<VisitInfo> = synchronized(pages + pageMeta) {

        val visits = mutableListOf<VisitInfo>()

        pages.forEach {
            it.value.forEach { visit ->
                if (visit.timestamp in start..end && !excludeTypes.contains(visit.type)) {
                    visits.add(VisitInfo(
                            url = it.key,
                            title = pageMeta[it.key]?.title,
                            visitTime = visit.timestamp,
                            visitType = visit.type,
                            previewImageUrl = null,
                            isRemote = false
                    ))
                }
            }
        }

        return visits
    }

    override fun getSuggestions(query: String, limit: Int): List<SearchResult> = synchronized(pages + pageMeta) {
        data class Hit(val url: String, val score: Int)

        val urlMatches = pages.asSequence().map {
            Hit(it.key, levenshteinDistance(it.key, query))
        }
        val titleMatches = pageMeta.asSequence().map {
            Hit(it.key, levenshteinDistance(it.value.title ?: "", query))
        }
        val matchedUrls = mutableMapOf<String, Int>()
        urlMatches.plus(titleMatches).forEach {
            if (matchedUrls.containsKey(it.url) && matchedUrls[it.url]!! < it.score) {
                matchedUrls[it.url] = it.score
            } else {
                matchedUrls[it.url] = it.score
            }
        }
        // Calculate maxScore so that we can invert our scoring.
        // Lower Levenshtein distance should produce a higher score.
        val maxScore = urlMatches.maxByOrNull { it.score }?.score ?: return@synchronized listOf()

        // TODO exclude non-matching results entirely? Score that implies complete mismatch.
        matchedUrls.asSequence().sortedBy { it.value }.map {
            SearchResult(id = it.key, score = maxScore - it.value, url = it.key, title = pageMeta[it.key]?.title)
        }.take(limit).toList()
    }

    override suspend fun getTopFrecentSites(numItems: Int, frecencyThreshold: FrecencyThresholdOption): List<TopFrecentSiteInfo> {
        throw UnsupportedOperationException("Pagination is not yet supported by the in-memory history storage")
    }

    override suspend fun getSuggestions(text: String): List<Suggestion> {
        return pages.keys
            .sortedByDescending { url -> pages[url]?.maxBy { visit -> visit.timestamp }?.timestamp }
            .filter {
                it.contains(text, ignoreCase = true) || pageMeta[it]?.title?.contains(text, ignoreCase = true) == true
            }
            .take(2) // TODO make history suggestions provider result maximum dynamic
            .map {
                Suggestion.OpenTabSuggestion(this, text, pageMeta[it]?.title, it)
            }
    }

    /* override fun getAutocompleteSuggestion(query: String): HistoryAutocompleteResult? = synchronized(pages) {
        segmentAwareDomainMatch(query, pages.keys)?.let { urlMatch ->
            return HistoryAutocompleteResult(
                    query, urlMatch.matchedSegment, urlMatch.url, AUTOCOMPLETE_SOURCE_NAME, pages.size)
        }
    } */

    override suspend fun deleteEverything() = synchronized(pages + pageMeta) {
        pages.clear()
        pageMeta.clear()
        onSizeChanged?.invoke(0)
        this.persist()
    }

    override suspend fun deleteVisitsSince(since: Long) = synchronized(pages) {
        pages.entries.forEach {
            it.setValue(it.value.filterNot { visit -> visit.timestamp >= since }.toMutableList())
        }
        pages = pages.filter { it.value.isNotEmpty() } as HashMap<String, MutableList<Visit>>
    }

    override suspend fun deleteVisitsBetween(startTime: Long, endTime: Long) = synchronized(pages) {
        pages.entries.forEach {
            it.setValue(it.value.filterNot { visit ->
                visit.timestamp in startTime..endTime
            }.toMutableList())
        }
        pages = pages.filter { it.value.isNotEmpty() } as HashMap<String, MutableList<Visit>>
    }

    override suspend fun deleteVisitsFor(url: String) = synchronized(pages + pageMeta) {
        pages.remove(url)
        pageMeta.remove(url)
        Unit
    }

    override suspend fun deleteVisit(url: String, timestamp: Long) = synchronized(pages) {
        if (pages.containsKey(url)) {
            pages[url] = pages[url]!!.filter { it.timestamp != timestamp }.toMutableList()
            onSizeChanged?.invoke(pages.size)
        }
    }

    /* override suspend fun prune() {
        // Not applicable.
    } */

    override suspend fun runMaintenance(dbSizeLimit: UInt) {
        // Not applicable.
    }

    override suspend fun warmUp() {
        this.restore()
    }

    override fun canAddUri(uri: String): Boolean {
        return !uri.isQwantUrl() && !uri.startsWith("moz-extension://")
    }

    override fun cleanup() {
        this.persist()
        // GC will take care of our internal data structures, so there's nothing to do here.
    }

    fun setupAutoPersist(delayMs: Long = 20000) {
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                Log.d("QWANT_BROWSER", "set autopersist history")
                this@History.persist()
                mainHandler.postDelayed(this, delayMs)
            }
        })
    }

    fun persist() {
        Log.d("QWANT_BROWSER", "persist history: ${pages.size} pages / ${pageMeta.size} metas")
        try {
            val fileOutputStream: FileOutputStream = context.openFileOutput(QWANT_HISTORY_FILENAME, Context.MODE_PRIVATE)
            val objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(this.pages)
            objectOutputStream.writeObject(this.pageMeta)
            objectOutputStream.flush()
            objectOutputStream.close()
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun restore() {
        var fileInputStream: FileInputStream? = null
        var objectInputStream: ObjectInputStream? = null

        try {
            val file: File = context.getFileStreamPath(QWANT_HISTORY_FILENAME)
            if (file.exists()) {
                fileInputStream = context.openFileInput(QWANT_HISTORY_FILENAME)
                objectInputStream = ObjectInputStream(fileInputStream)
                this.pages = objectInputStream.readObject() as HashMap<String, MutableList<Visit>>
                this.pageMeta = objectInputStream.readObject() as HashMap<String, PageObservation>
                Log.d("QWANT_BROWSER", "history restored: ${pages.size} pages / ${pageMeta.size} metas")
            }
        } catch (e: FileNotFoundException) {
            Log.e("QWANT_BROWSER", "Failed reading history file: File not found: " + e.message)
            // e.printStackTrace()
        } catch (e: IOException) {
            Log.e("QWANT_BROWSER", "Failed reading history file: IO exception: " + e.message)
            // e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            Log.e("QWANT_BROWSER", "Failed reading history file: Class not found: " + e.message)
            // e.printStackTrace()
        } catch (e: Exception) {
            Log.e("QWANT_BROWSER", "Failed reading history file: " + e.message)
            // e.printStackTrace()
        } finally {
            objectInputStream?.close()
            fileInputStream?.close()
        }
    }

    companion object {
        const val QWANT_HISTORY_FILENAME = "qwant_history"
    }
}
