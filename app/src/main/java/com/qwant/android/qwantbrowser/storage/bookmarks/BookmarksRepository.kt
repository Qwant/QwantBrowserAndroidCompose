package com.qwant.android.qwantbrowser.storage.bookmarks

import android.content.Context
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.SuggestionProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import mozilla.components.concept.storage.BookmarkInfo
import mozilla.components.concept.storage.BookmarkNode
import mozilla.components.concept.storage.BookmarkNodeType
import mozilla.components.concept.storage.BookmarksStorage
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BookmarksRepository @Inject constructor(
    db: BookmarksDatabase,
    @ApplicationContext context: Context
): BookmarksStorage, SuggestionProvider {
    private val dao = db.bookmarksDao()

    val root = BookmarkNode(
        type = BookmarkNodeType.FOLDER,
        guid = ROOT_GUID,
        title = context.getString(R.string.bookmarks),
        parentGuid = null,
        position = null,
        url = null,
        dateAdded = 0,
        children = null
    )

    fun getBookmarksInFolderFlow(guid: String): Flow<List<BookmarkNode>> =
        dao.getBookmarksInFolderFlow(guid).map { list ->
            list.map { it.toMozillaBookmarkNode() }
        }

    fun isUrlBookmarkedFlow(url: String): Flow<Boolean> =
        dao.isUrlBookmarkedFlow(url)

    suspend fun getFolderTree(guid: String): BookmarkNode? {
        val children = dao.getChildren(guid, BookmarkNodeType.FOLDER)
            .mapNotNull { child -> this.getFolderTree(child.guid) }
        return when (guid) {
            root.guid -> root.copy(children = children)
            else -> dao.get(guid)?.toMozillaBookmarkNode(children)
        }
    }

    override suspend fun addFolder(
        parentGuid: String,
        title: String,
        position: UInt?
    ): String {
        val guid = getNewGuid()
        dao.insert(BookmarkNode(
            guid = guid,
            type = BookmarkNodeType.FOLDER,
            parentGuid = parentGuid,
            title = title,
            position = position?.toInt(),
            url = null,
            dateAdded = System.currentTimeMillis()
        ))
        return guid
    }

    override suspend fun addItem(
        parentGuid: String,
        url: String,
        title: String,
        position: UInt?
    ): String {
        val guid = getNewGuid()
        dao.insert(BookmarkNode(
            guid = guid,
            type = BookmarkNodeType.ITEM,
            parentGuid = parentGuid,
            title = title,
            position = position?.toInt(),
            url = url,
            dateAdded = System.currentTimeMillis()
        ))
        return guid
    }

    override suspend fun addSeparator(parentGuid: String, position: UInt?): String {
        val guid = getNewGuid()
        dao.insert(BookmarkNode(
            guid = guid,
            type = BookmarkNodeType.SEPARATOR,
            parentGuid = parentGuid,
            title = null,
            position = position?.toInt(),
            url = null,
            dateAdded = System.currentTimeMillis()
        ))
        return guid
    }

    override suspend fun deleteNode(guid: String): Boolean {
         val exists = dao.alreadyExists(guid)
         if (exists) dao.deleteByGuid(guid)
         return exists
    }

    suspend fun deleteBookmarksByUrl(url: String) {
        dao.deleteByUrl(url)
    }

    override suspend fun getBookmark(guid: String): BookmarkNode? =
        dao.get(guid)?.toMozillaBookmarkNode()

    override suspend fun getBookmarksWithUrl(url: String): List<BookmarkNode> =
        dao.getByUrl(url).map { it.toMozillaBookmarkNode() }

    override suspend fun getRecentBookmarks(
        limit: Int,
        maxAge: Long?,
        currentTime: Long
    ): List<BookmarkNode> =
        dao.getRecent(maxAge ?: 0, currentTime, limit).map { it.toMozillaBookmarkNode() }

    override suspend fun getTree(guid: String, recursive: Boolean): BookmarkNode? {
        val children = dao.getChildren(guid).mapNotNull { child ->
            if (recursive) {
                this.getTree(child.guid, true)
            } else {
                child.toMozillaBookmarkNode()
            }
        }
        return when (guid) {
            root.guid -> root.copy(children = children)
            else -> dao.get(guid)?.toMozillaBookmarkNode(children)
        }
    }

    override suspend fun searchBookmarks(query: String, limit: Int): List<BookmarkNode> =
        dao.search(query, limit).map { it.toMozillaBookmarkNode() }

    override suspend fun updateNode(guid: String, info: BookmarkInfo) {
        val bookmark = dao.get(guid)
        bookmark?.let { b ->
            info.parentGuid?.let { b.parentGuid = it }
            info.title?.let { b.title = it }
            info.url?.let { b.url = it }
            info.position?.let { b.position = it.toInt() }
            dao.update(b)
        }
    }

    override suspend fun getSuggestions(text: String): List<Suggestion> =
        dao.search(text, 1).map { // TODO make bookmarks suggestions limit dynamic
            Suggestion.OpenTabSuggestion(this, text, it.title, it.url)
        }

    private suspend fun getNewGuid(): String {
        var guid: String
        do {
            guid = UUID.randomUUID().toString()
        } while (dao.alreadyExists(guid))
        return guid
    }

    // Not applicable
    override suspend fun warmUp() {}
    override suspend fun runMaintenance(dbSizeLimit: UInt) {}

    companion object {
        private const val ROOT_GUID = "bookmarks_root"
    }
}

fun com.qwant.android.qwantbrowser.storage.bookmarks.BookmarkNode.toMozillaBookmarkNode(
    children: List<BookmarkNode>? = null
): BookmarkNode {
    return BookmarkNode(
        this.type,
        this.guid,
        this.parentGuid,
        this.position?.toUInt(),
        this.title,
        this.url,
        this.dateAdded,
        children
    )
}