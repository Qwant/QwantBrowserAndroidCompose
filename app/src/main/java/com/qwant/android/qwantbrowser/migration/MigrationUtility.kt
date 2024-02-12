package com.qwant.android.qwantbrowser.migration

import com.qwant.android.qwantbrowser.migration.datastore.MigrationDataRepository
import com.qwant.android.qwantbrowser.preferences.frontend.CustomPageCharacter
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MigrationUtility @Inject constructor(
    private val migrationDataRepository: MigrationDataRepository,
    private val frontEndPreferencesRepository: FrontEndPreferencesRepository
) {
    fun checkMigrations() {
        MainScope().launch {
            migration503()
        }
    }

    private suspend fun migration503() {
        migrationDataRepository.flow
            .map { it.migration503 }
            .collect { shouldMigrate ->
                if (shouldMigrate) {
                    frontEndPreferencesRepository.updateShowNews(false)
                    frontEndPreferencesRepository.updateCustomPageCharacter(CustomPageCharacter.NO_CHARACTER)
                    val interfaceLanguage = frontEndPreferencesRepository.flow.map { it.interfaceLanguage }.first()
                    if (interfaceLanguage == "fr") {
                        frontEndPreferencesRepository.updateSearchResultRegion("fr_FR")
                    }
                    migrationDataRepository.migration503Done()
                }
            }
    }
}