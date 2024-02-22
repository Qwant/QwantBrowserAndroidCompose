package com.qwant.android.qwantbrowser.storage.bookmarks

import androidx.room.*

@Dao
interface BookmarksDao {
    @Insert
    suspend fun insert(bookmark: BookmarkNode)

    @Update
    suspend fun update(bookmark: BookmarkNode)

    @Query("DELETE from bookmark_node WHERE guid = :guid")
    suspend fun deleteByGuid(guid: String)

    @Query("SELECT * FROM bookmark_node WHERE guid = :guid")
    suspend fun get(guid: String): BookmarkNode?

    @Query("SELECT * FROM bookmark_node WHERE url = :url")
    suspend fun getByUrl(url: String): List<BookmarkNode>

    @Query("SELECT * FROM bookmark_node " +
            "WHERE dateAdded BETWEEN :maxAge AND :currentTime " +
            "LIMIT :limit")
    suspend fun getRecent(maxAge: Long, currentTime: Long, limit: Int): List<BookmarkNode>

    @Query("SELECT * FROM bookmark_node " +
            "WHERE url LIKE '%' || :query || '%' " +
            "OR title LIKE '%' || :query || '%'" +
            "ORDER BY dateAdded DESC " +
            "LIMIT :limit")
    suspend fun search(query: String, limit: Int): List<BookmarkNode>

    @Query("SELECT * FROM bookmark_node " +
            "WHERE parentGuid = :guid " +
            "ORDER BY position ")
    suspend fun getChildren(guid: String): List<BookmarkNode>

    @Query("SELECT EXISTS (SELECT * FROM bookmark_node WHERE guid = :guid)")
    suspend fun alreadyExists(guid: String) : Boolean
}