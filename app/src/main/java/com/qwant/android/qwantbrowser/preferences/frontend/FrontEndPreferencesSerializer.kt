package com.qwant.android.qwantbrowser.preferences.frontend

import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object FrontEndPreferencesSerializer : Serializer<FrontEndPreferences> {
    override val defaultValue: FrontEndPreferences = FrontEndPreferences.getDefaultInstance()
        .toBuilder()
        .setAppearance(Appearance.SYSTEM_SETTINGS)
        .setCustomPageColor(CustomPageColor.BLUE)
        .setCustomPageCharacter(CustomPageCharacter.RANDOM_CHARACTER)
        .setShowNews(true)
        .setShowSponsor(true)
        .setShowFavicons(true)
        .setOpenResultsInNewTab(false)
        .setAdultFilter(AdultFilter.MODERATE)
        .setInterfaceLanguage(AppCompatDelegate.getApplicationLocales().getFirstMatch(arrayOf(
            "en", "fr", "de", "it", "es"
        ))?.language ?: "en")
        .setSearchResultRegion(AppCompatDelegate.getApplicationLocales().getFirstMatch(
            availableSearchRegions
        )?.toLanguageTag() ?: "en_GB")
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