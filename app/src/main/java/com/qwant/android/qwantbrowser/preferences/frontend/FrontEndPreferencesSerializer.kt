package com.qwant.android.qwantbrowser.preferences.frontend

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object FrontEndPreferencesSerializer : Serializer<FrontEndPreferences> {
    private val defaultRegionMap = mapOf(
        "en" to "en_GB",
        "fr" to "fr_FR",
        "de" to "de_DE",
        "it" to "it_IT",
        "es" to "es_ES"
    )
    private val defaultLanguage = AppCompatDelegate.getApplicationLocales().getFirstMatch(arrayOf(
        "en", "fr", "de", "it", "es"
    ))?.language ?: "fr"

    override val defaultValue: FrontEndPreferences = FrontEndPreferences.getDefaultInstance()
        .toBuilder()
        .setAppearance(Appearance.SYSTEM_SETTINGS)
        .setCustomPageColor(CustomPageColor.BLUE)
        .setCustomPageCharacter(CustomPageCharacter.NO_CHARACTER)
        .setShowNews(false)
        .setShowSponsor(true)
        .setShowFavicons(true)
        .setOpenResultsInNewTab(false)
        .setAdultFilter(AdultFilter.MODERATE)
        .setInterfaceLanguage(defaultLanguage)
        .setSearchResultRegion(defaultRegionMap[defaultLanguage])
        .build()

    override suspend fun readFrom(input: InputStream): FrontEndPreferences {
        try {
            return FrontEndPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read protobuf.", exception)
        }
    }

    override suspend fun writeTo(t: FrontEndPreferences, output: OutputStream) = t.writeTo(output)
}