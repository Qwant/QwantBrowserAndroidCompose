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
        val client = "qwantbrowsercompose" // getClient(context, prefs)
        buildString {
            append(QwantBaseUrl)
            append("?client=").append(client)
            if (prefs.showNews) append("&hc=1") else append("&hc=0")
            append("&theme=").append(when(prefs.appearance) {
                Appearance.LIGHT -> 0
                Appearance.DARK -> 1
                Appearance.SYSTEM_SETTINGS -> -1
                else -> -1
            })
            append("&qbc=1")
        }
        // TODO add back bouygues f parameter here, or not if done with another endpoint
    }

    suspend fun getQwantUrl(
        query: String? = null,
        widget: Boolean = false
    ) : String {
        var url = homeUrl.first()

        if (query != null) url += "&q=$query"
        if (widget) url += "&widget=1"

        return url

            /* val l = interface_language ?: prefs.getString(context.getString(R.string.pref_key_general_language_interface), "en_GB")
            val r = search_language ?: prefs.getString(context.getString(R.string.pref_key_general_language_search), "en")
            val sr = search_region ?: prefs.getString(context.getString(R.string.pref_key_general_region_search), "GB")
            val s = adult_content ?: prefs.getString(context.getString(R.string.pref_key_general_adultcontent), "0")
            val hc = news_on_home ?: prefs.getBoolean(context.getString(R.string.pref_key_general_newsonhome), true)
            val b = results_in_new_tab ?: prefs.getBoolean(context.getString(R.string.pref_key_general_resultsinnewtab), false)
            val si = favicon_on_serp ?: prefs.getBoolean(context.getString(R.string.pref_key_general_favicononserp), true)
            val c = custom_color ?: prefs.getString(context.getString(R.string.pref_key_general_custom_color), "blue")
            val ch = custom_character ?: prefs.getString(context.getString(R.string.pref_key_general_custom_character), "random")
            val hti = tiles ?: prefs.getBoolean(context.getString(R.string.pref_key_general_tiles), true)
            var theme = dark_theme ?: prefs.getString(context.getString(R.string.pref_key_general_dark_theme), "2")
            if (theme == "2") {
                theme = if ((context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) "1" else "0"
            } */

            // val localeSplit = sr.split("_")

            // builder.append("?client=").append(client)
                /* .append("&l=").append(l?.toLowerCase(Locale.getDefault()))
                .append("&sr=").append(sr)
                .append("&r=").append(r)
                .append("&s=").append(s)
                .append("&hc=").append(if (hc) "1" else "0")
                .append("&si=").append(if (si) "1" else "0")
                .append("&b=").append(if (b) "1" else "0")
                .append("&theme=").append(theme)
                .append("&c=").append(c)
                .append("&ch=").append(ch)
                .append("&hti=").append(if (hti) "1" else "0") */

            /* if (BuildConfig.BUILD_TYPE == "bouygues") {
                val firstRequestKey = context.getString(R.string.pref_key_first_request)
                if (prefs.getBoolean(firstRequestKey, true)) {
                    prefs.edit().putBoolean(firstRequestKey, false).apply()
                    builder.append("&f=1")
                }
            } */
    }

    suspend fun updateShowNews(show: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setShowNews(show).build()
        }
    }

    suspend fun updateInterfaceLanguage(language: String) {
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

    suspend fun updateVideosOnQwant(playOnQwant: Boolean) {
        datastore.updateData { preferences ->
            preferences.toBuilder().setVideosOnQwant(playOnQwant).build()
        }
    }
}