package com.qwant.android.qwantbrowser.preferences.frontend

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView

object FrontEndPreferencesMigration {
    private const val SHARED_PREFS_NAME = "com.qwant.liberty_preferences"
    private val keysToMigrate = setOf(
        "pref_key_general_dark_theme",
        "pref_key_general_custom_color",
        "pref_key_general_custom_character",
        "pref_key_general_newsonhome",
        "pref_key_general_tiles",
        "pref_key_general_favicononserp",
        "pref_key_general_resultsinnewtab",
        "pref_key_general_adultcontent",
        "pref_key_general_language_interface",
        "pref_key_general_language_search",
        "pref_key_general_region_search"
    )

    fun create(context: Context) : SharedPreferencesMigration<FrontEndPreferences> {
        return SharedPreferencesMigration(
            context, SHARED_PREFS_NAME, keysToMigrate
        ) { sharedPrefs: SharedPreferencesView, currentData: FrontEndPreferences ->
            with (currentData.toBuilder()) {
                appearance = when (sharedPrefs.getString("pref_key_general_dark_theme", "2")) {
                    "0" -> Appearance.LIGHT
                    "1" -> Appearance.DARK
                    "2" -> Appearance.SYSTEM_SETTINGS
                    else -> Appearance.SYSTEM_SETTINGS
                }
                customPageColor = when (sharedPrefs.getString("pref_key_general_custom_color", "blue")) {
                    "blue" -> CustomPageColor.BLUE
                    "green" -> CustomPageColor.GREEN
                    "pink" -> CustomPageColor.PINK
                    "purple" -> CustomPageColor.PURPLE
                    else -> CustomPageColor.BLUE
                }
                customPageCharacter = when (sharedPrefs.getString("pref_key_general_custom_character", "random")) {
                    "random" -> CustomPageCharacter.RANDOM_CHARACTER
                    "puffa" -> CustomPageCharacter.DOUDOUNE
                    "football" -> CustomPageCharacter.FOOTBALL
                    "turtleneck" -> CustomPageCharacter.TURTLENECK
                    "glasses" -> CustomPageCharacter.GLASSES
                    "cat" -> CustomPageCharacter.CAT
                    "balloon" -> CustomPageCharacter.BALLOON
                    "pull" -> CustomPageCharacter.PULL
                    "hat" -> CustomPageCharacter.HAT
                    "hands" -> CustomPageCharacter.HANDS
                    "none" -> CustomPageCharacter.NO_CHARACTER
                    else -> CustomPageCharacter.RANDOM_CHARACTER
                }
                showNews = sharedPrefs.getBoolean("pref_key_general_newsonhome", true)
                showSponsor = sharedPrefs.getBoolean("pref_key_general_tiles", true)
                showFavicons = sharedPrefs.getBoolean("pref_key_general_favicononserp", true)
                openResultsInNewTab = sharedPrefs.getBoolean("pref_key_general_resultsinnewtab", false)
                adultFilter = when (sharedPrefs.getString("pref_key_general_adultcontent", "1")) {
                    "0" -> AdultFilter.NO_FILTER
                    "1" -> AdultFilter.MODERATE
                    "2" -> AdultFilter.STRICT
                    else -> AdultFilter.MODERATE
                }

                val oldLanguage = sharedPrefs.getString("pref_key_general_language_interface", null)
                if (oldLanguage != null) {
                    val newLanguage = when (oldLanguage) {
                        "en_GB" -> "en"
                        "fr_FR" -> "fr"
                        "de_DE" -> "de"
                        "it_IT" -> "it"
                        "es_ES" -> "es"
                        else -> "en"
                    }
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(newLanguage))
                    interfaceLanguage = newLanguage
                }

                val oldSearchLanguage = sharedPrefs.getString("pref_key_general_language_search", null) ?: ""
                val oldSearchRegion = sharedPrefs.getString("pref_key_general_region_search", null) ?: ""
                val regionTag = "${oldSearchLanguage}_${oldSearchRegion}"
                if (availableSearchRegions.contains(regionTag)) {
                    searchResultRegion = regionTag
                } else if (oldLanguage != null) {
                    searchResultRegion = oldLanguage
                }

                build()
            }
        }
    }
}

val availableSearchRegions = arrayOf(
    "en_GB", "fr_FR", "de_DE", "it_IT", "es_ES", "ca_ES", "es_AR", "en_AU", "en_US", "cs_CZ",
    "ro_RO", "el_GR", "zh_CN", "zh_HK", "en_NZ", "th_TH", "ko_KR", "sv_SE", "nb_NO", "da_DK",
    "hu_HU", "et_EE", "es_MX", "es_CL", "en_CA", "fr_CA", "en_MY", "bg_BG", "fi_FI", "pl_PL",
    "nl_NL", "pt_PT", "de_CH", "fr_CH", "it_CH", "de_AT", "fr_BE", "nl_BE", "en_IE"
)



