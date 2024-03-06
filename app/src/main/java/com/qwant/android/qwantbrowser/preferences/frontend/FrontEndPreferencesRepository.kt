package com.qwant.android.qwantbrowser.preferences.frontend

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.preference.PreferenceManager
import com.qwant.android.qwantbrowser.storage.QwantClientProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val LOGTAG: String = "FrontEndPreferencesRepo"

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
    private val clHolder: ClHolder,
    private val qwantClientProvider: QwantClientProvider
) {
    companion object {
        // TODO Use gradle to set qwant base URL so we can easily use pre-prod
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

    val homeUrl = flow.combine(qwantClientProvider.clientState) { prefs, client ->
        buildString {
            append(QwantBaseUrl)
            append("?client=").append(client)
            append("&theme=").append(getThemeUrlValue(prefs.appearance))
            append("&c=").append(getCustomPageColorUrlValue(prefs.customPageColor))
            append("&ch=").append(getCustomPageCharacterUrlValue(prefs.customPageCharacter))
            append("&hc=").append(getBooleanUrlValue(prefs.showNews))
            append("&hti=").append(getBooleanUrlValue(prefs.showSponsor))
            append("&s=").append(getAdultFilterUrlValue(prefs.adultFilter))
            append("&si=").append(getBooleanUrlValue(prefs.showFavicons))
            append("&b=").append(getBooleanUrlValue(prefs.openResultsInNewTab))
            append("&l=").append(prefs.interfaceLanguage)
            append("&locale=").append(prefs.searchResultRegion)

            clHolder.getCl()?.let { append("&cl=").append(it) }

            append("&qbc=1") // Certifies validity for the url interceptor

            append("&omnibar=1")
        }
    }

    suspend fun checkUrlParameters(url: String): Boolean {
        val prefs = flow.first()
        // TODO use URL(url).query.split("&") to avoid regexs and check directly parameters without special chars
        return url.contains(("[\\?&]client=" + qwantClientProvider.clientState.value).toRegex())
                && url.contains(("[\\?&]theme=" + getThemeUrlValue(prefs.appearance)).toRegex())
                && url.contains(("[\\?&]c=" + getCustomPageColorUrlValue(prefs.customPageColor)).toRegex())
                && url.contains(("[\\?&]ch=" + getCustomPageCharacterUrlValue(prefs.customPageCharacter)).toRegex())
                && url.contains(("[\\?&]hc=" + getBooleanUrlValue(prefs.showNews)).toRegex())
                && url.contains(("[\\?&]hti=" + getBooleanUrlValue(prefs.showSponsor)).toRegex())
                && url.contains(("[\\?&]s=" + getAdultFilterUrlValue(prefs.adultFilter)).toRegex())
                && url.contains(("[\\?&]si=" + getBooleanUrlValue(prefs.showFavicons)).toRegex())
                && url.contains(("[\\?&]b=" + getBooleanUrlValue(prefs.openResultsInNewTab)).toRegex())
                && url.contains("""[\\?&]omnibar=1""".toRegex())
    }

    private fun getThemeUrlValue(appearance: Appearance) = when(appearance) {
        Appearance.LIGHT -> 0
        Appearance.DARK -> 1
        Appearance.SYSTEM_SETTINGS -> -1
        else -> -1
    }

    private fun getCustomPageColorUrlValue(color: CustomPageColor) = when(color) {
        CustomPageColor.BLUE -> "blue"
        CustomPageColor.GREEN -> "green"
        CustomPageColor.PINK -> "pink"
        CustomPageColor.PURPLE -> "purple"
        else -> "blue"
    }

    private fun getCustomPageCharacterUrlValue(character: CustomPageCharacter) = when(character) {
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
    }

    private fun getAdultFilterUrlValue(filter: AdultFilter) = when(filter) {
        AdultFilter.NO_FILTER -> 0
        AdultFilter.MODERATE -> 1
        AdultFilter.STRICT -> 2
        else -> 0
    }

    private fun getBooleanUrlValue(b: Boolean) = when (b) {
        true -> 1
        false -> 0
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