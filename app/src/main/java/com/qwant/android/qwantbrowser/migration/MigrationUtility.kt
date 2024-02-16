package com.qwant.android.qwantbrowser.migration

import android.content.Context
import android.util.Log
import com.qwant.android.qwantbrowser.migration.datastore.MigrationData
import com.qwant.android.qwantbrowser.migration.datastore.MigrationDataRepository
import com.qwant.android.qwantbrowser.preferences.frontend.CustomPageCharacter
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import com.qwant.android.qwantbrowser.storage.history.HistoryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import mozilla.components.concept.storage.PageObservation
import mozilla.components.concept.storage.PageVisit
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.ObjectInputStream
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MigrationUtility @Inject constructor(
    @ApplicationContext private val context: Context,
    private val migrationDataRepository: MigrationDataRepository,
    private val frontEndPreferencesRepository: FrontEndPreferencesRepository,
    private val historyRepository: HistoryRepository
) {
    private val backgroundScope = CoroutineScope(Dispatchers.IO + Job())

    fun checkMigrations() {
        MainScope().launch {
            val migrationData = migrationDataRepository.flow.first()
            migration503(migrationData)
            migration504(migrationData)
        }
    }

    private suspend fun migration503(migrationData: MigrationData) {
        if (migrationData.migration503) {
            frontEndPreferencesRepository.updateShowNews(false)
            frontEndPreferencesRepository.updateCustomPageCharacter(CustomPageCharacter.NO_CHARACTER)
            val interfaceLanguage =
                frontEndPreferencesRepository.flow.map { it.interfaceLanguage }.first()
            if (interfaceLanguage == "fr") {
                frontEndPreferencesRepository.updateSearchResultRegion("fr_FR")
            }
            migrationDataRepository.migration503Done()
        }
    }

    // Linter is wrong there. It does execute on the background IO thread
    @Suppress("BlockingMethodInNonBlockingContext")
    private fun migration504(migrationData: MigrationData) {
        backgroundScope.launch {
            if (migrationData.migration504) {
                val historyV4Filename = "qwant_history"
                var file: File? = null
                var fileInputStream: FileInputStream? = null
                var objectInputStream: ObjectInputStream? = null
                var shouldDeleteFile = false

                try {
                    file = context.getFileStreamPath(historyV4Filename)
                    if (file.exists()) {
                        fileInputStream = context.openFileInput(historyV4Filename)
                        objectInputStream = ObjectInputStream(fileInputStream)
                        val pages = objectInputStream.readObject() as HashMap<*, *>
                        val pageMeta = objectInputStream.readObject() as HashMap<*, *>
                        pages.forEach { map ->
                            val url = map.key as String
                            val value = map.value as List<*>
                            value.forEach { v ->
                                val visit = v as com.qwant.android.qwantbrowser.legacy.history.Visit
                                historyRepository.recordVisitWithTimestamp(url, PageVisit(visit.type), visit.timestamp)
                            }
                        }
                        pageMeta.forEach { map ->
                            val url = map.key as String
                            val observation = map.value as com.qwant.android.qwantbrowser.legacy.history.PageObservation
                            historyRepository.recordObservation(url, PageObservation(observation.title))
                        }
                        migrationDataRepository.migration504Done()
                        shouldDeleteFile = true
                    } else {
                        migrationDataRepository.migration504Done()
                    }
                } catch (e: FileNotFoundException) {
                    Log.e("QWANT_MIGRATE", "Failed reading history v4 file: File not found: ", e)
                } catch (e: IOException) {
                    Log.e("QWANT_MIGRATE", "Failed reading history v4 file: IO exception: ", e)
                } catch (e: ClassNotFoundException) {
                    Log.e("QWANT_MIGRATE", "Failed reading history v4 file: Class not found: ", e)
                } catch (e: Exception) {
                    Log.e("QWANT_MIGRATE", "Failed reading history v4 file: ", e)
                } finally {
                    objectInputStream?.close()
                    fileInputStream?.close()
                    if (shouldDeleteFile) { file?.delete() }
                }
            }
        }
    }
}