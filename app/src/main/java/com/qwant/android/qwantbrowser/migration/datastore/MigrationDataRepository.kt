package com.qwant.android.qwantbrowser.migration.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.*
import mozilla.components.concept.engine.Engine
import java.io.IOException
import javax.inject.Inject

private const val LOGTAG: String = "MigrationDataRepo"


class MigrationDataRepository @Inject constructor(
    private val datastore: DataStore<MigrationData>
) {
    val flow: Flow<MigrationData> = datastore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(LOGTAG, "Error reading frontend preferences.", exception)
                emit(MigrationData.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun migration503Done() {
        datastore.updateData { data ->
            data.toBuilder().setMigration503(false).build()
        }
    }
}