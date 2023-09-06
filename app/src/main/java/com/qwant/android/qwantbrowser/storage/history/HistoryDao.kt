package com.qwant.android.qwantbrowser.storage.history

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mozilla.components.concept.storage.VisitType

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(page: Page)

    @Upsert
    suspend fun upsert(page: Page)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIfNeeded(page: Page)

    @Insert
    suspend fun insert(visit: Visit)

    @Delete(entity = Visit::class)
    suspend fun deleteVisitByKey(visitKey: VisitKey)

    // Should delete visits by cascade
    @Query("DELETE from pages WHERE uri = :uri")
    suspend fun deleteURI(uri: String)

    @Query("DELETE from visits WHERE time >= :timestamp")
    suspend fun deleteVisitsSince(timestamp: Long)

    @Query("DELETE from visits WHERE time BETWEEN :startTime AND :endTime")
    suspend fun deleteVisitsBetween(startTime: Long, endTime: Long)

    @Query("SELECT * from pages")
    fun getAllPages(): List<Page>

    @Query("SELECT * from pages WHERE uri = :uri")
    fun getPage(uri: String): Page?

    @Query("SELECT * from pages " +
            "JOIN visits ON uri = pageUri " +
            "WHERE (time BETWEEN :start AND :end) " +
            "AND (type NOT IN (:excludeTypes))")
    fun getVisitsDetailed(
        start: Long,
        end: Long,
        excludeTypes: List<VisitType>
    ): Map<Page, List<Visit>>
}