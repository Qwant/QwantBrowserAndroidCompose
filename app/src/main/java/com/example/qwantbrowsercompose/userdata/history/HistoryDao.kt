package com.example.qwantbrowsercompose.userdata.history

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert suspend fun insert(page: Page)
    @Update suspend fun update(page: Page)
    @Delete suspend fun delete(page: Page)

    @Query("SELECT * from pages")
    fun getAllPages(): Flow<List<Page>>

    @Insert suspend fun insert(visit: Visit)
}