package com.qwant.android.qwantbrowser.preferences.frontend

import android.util.Log
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject


private const val LOGTAG: String = "FrontEndPreferencesRepo"

// Not needed, while this class remains stateless
// @Module
// @InstallIn(ActivityRetainedComponent::class)
class FrontEndPreferencesRepository @Inject constructor(
    private val datastore: DataStore<FrontEndPreferences>
) {
    companion object {
        private const val QwantBaseUrl = "https://www.qwant.com/"
    }

    val flow: Flow<FrontEndPreferences> = datastore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(LOGTAG, "Error reading frontend preferences.", exception)
                emit(FrontEndPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    val homeUrl = flow.map { prefs ->
        val client = "qwantbrowsercompose" // TODO get real client - getClient(context, prefs)
        buildString {
            append(QwantBaseUrl)
            append("?client=").append(client)
            append("&theme=").append(when(prefs.appearance) {
                Appearance.LIGHT -> 0
                Appearance.DARK -> 1
                Appearance.SYSTEM_SETTINGS -> -1
                else -> -1
            })
            append("&c=").append(when(prefs.customPageColor) {
                CustomPageColor.BLUE -> "blue"
                CustomPageColor.GREEN -> "green"
                CustomPageColor.PINK -> "pink"
                CustomPageColor.PURPLE -> "purple"
                else -> "blue"
            })
            append("&ch=").append(when(prefs.customPageCharacter) {
                CustomPageCharacter.NO_CHARACTER -> "none"
                CustomPageCharacter.RANDOM_CHARACTER -> "random"
                CustomPageCharacter.DOUDOUNE -> "puffa"
                CustomPageCharacter.FOOTBALL -> "football"
                CustomPageCharacter.TURTLENECK -> "turtleneck"
                CustomPageCharacter.GLASSES -> "glasses"
                CustomPageCharacter.CAT -> "cat"
                CustomPageCharacter.BALLOON -> "balloon"
                CustomPageCharacter.PULL -> "pull"
                CustomPageCharacter.HAT -> "hat"
                CustomPageCharacter.HANDS -> "hands"
                else -> "random"
            })
            if (prefs.showNews) append("&hc=1") else append("&hc=0")
            if (prefs.showSponsor) append("&hti=1") else append("&hti=0")
            append("&s=").append(when(prefs.adultFilter) {
                AdultFilter.NO_FILTER -> 0
                AdultFilter.MODERATE -> 1
                AdultFilter.STRICT -> 2
                else -> 0
            })
            if (prefs.showFavicons) append("&si=1") else append("&si=0")
            if (prefs.openResultsInNewTab) append("&b=1") else append("&b=0")
            append("&l=").append(prefs.interfaceLanguage)
            append("&locale=").append(prefs.searchResultRegion)
            // TODO add cl preference
            // TODO f=1 in the first url

            append("&qbc=1") // Certifies validity for the url interceptor
        }
    }

    suspend fun updateShowNews(show: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setShowNews(show).build()
        }
    }

    suspend fun updateInterfaceLanguage(language: String) {
        // TODO update also search region with interface language ?
        datastore.updateData { preferences ->
            preferences.toBuilder().setInterfaceLanguage(language).build()
        }
    }

    suspend fun updateAppearance(appearance: Appearance) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setAppearance(appearance).build()
        }
    }

    suspend fun updateCustomPageColor(color: CustomPageColor) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setCustomPageColor(color).build()
        }
    }

    suspend fun updateCustomPageCharacter(character: CustomPageCharacter) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setCustomPageCharacter(character).build()
        }
    }

    suspend fun updateShowSponsor(show: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setShowSponsor(show).build()
        }
    }

    suspend fun updateSearchResultRegion(region: String) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setSearchResultRegion(region).build()
        }
    }

    suspend fun updateAdultFilter(filter: AdultFilter) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setAdultFilter(filter).build()
        }
    }

    suspend fun updateShowFavicons(show: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setShowFavicons(show).build()
        }
    }

    suspend fun updateOpenResultsInNewTab(openInNewTab: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setOpenResultsInNewTab(openInNewTab).build()
        }
    }

    // TODO add VideosOnQwant frontend setting
    suspend fun updateVideosOnQwant(playOnQwant: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setVideosOnQwant(playOnQwant).build()
        }
    }
}