package com.qwant.android.qwantbrowser.preferences.frontend

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.preference.PreferenceManager
import com.qwant.android.qwantbrowser.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


private const val LOGTAG: String = "FrontEndPreferencesRepo"

// TODO move saved client to internal datastore instead of shared prefs
@Singleton
class ClientHolder @Inject constructor(
    @ApplicationContext val context: Context
) {
    private var instance: String? = null

    private val prefKey = "pref_key_saved_client"
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getClient(): String {
        return instance
            ?: prefs.getString(prefKey, null)?.also {
                instance = it
            }
            ?: context.getString(R.string.app_client_string).also {
                instance = it
                with(prefs.edit()) {
                    putString(prefKey, it)
                    apply()
                }
            }
    }
}

// TODO move saved cl to internal datastore instead of shared prefs
@Singleton
class ClHolder @Inject constructor(
    @ApplicationContext val context: Context
) {
    private val prefKey = "pref_key_utm_campaign"
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getCl(): String? {
        return prefs.getString(prefKey, null)
    }
}

// Not needed, while this class remains stateless
// @Module
// @InstallIn(ActivityRetainedComponent::class)
class FrontEndPreferencesRepository @Inject constructor(
    private val datastore: DataStore<FrontEndPreferences>,
    private val clientHolder: ClientHolder,
    private val clHolder: ClHolder
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
        buildString {
            append(QwantBaseUrl)
            append("?client=").append(clientHolder.getClient())
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

            clHolder.getCl()?.let { append("&cl=").append(it) }

            append("&qbc=1") // Certifies validity for the url interceptor

            append("&omnibar=1")
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