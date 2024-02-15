package com.qwant.android.qwantbrowser.stats

import android.content.Context
import android.util.Log
import com.qwant.android.qwantbrowser.ext.UAQwant
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import com.qwant.android.qwantbrowser.storage.QwantClientProvider
import com.qwant.android.qwantbrowser.suggest.Suggestion
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import mozilla.components.concept.fetch.Client
import mozilla.components.concept.fetch.MutableHeaders
import mozilla.components.concept.fetch.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Datahub @Inject constructor(
    @ApplicationContext private val context: Context,
    private val client: Client,
    private val clientProvider: QwantClientProvider,
    frontEndPreferencesRepository: FrontEndPreferencesRepository,
) {
    private val scope = CoroutineScope(Dispatchers.IO + Job())

    private var interfaceLanguage: String = ""
    private var searchRegion: String = ""

    init {
        scope.launch {
            frontEndPreferencesRepository.flow
                .map { it.interfaceLanguage }
                .collect { interfaceLanguage = it }
        }
        scope.launch {
            frontEndPreferencesRepository.flow
                .map { it.searchResultRegion }
                .collect { searchRegion = it }
        }
    }

    fun brandSuggestClicked(suggestion: Suggestion.BrandSuggestion) {
        call("suggest", BRAND_SUGGEST_PAYLOAD.format(
            clientProvider.client,
            interfaceLanguage,
            searchRegion,
            suggestion.brand,
            suggestion.search.length,
            suggestion.rank,
            suggestion.search,
            suggestion.suggestType,
            suggestion.url
        ))
    }

    private fun call(endpoint: String, payload: String) {
        scope.launch {
            val request = Request(
                url = BASE_URL + endpoint,
                method = Request.Method.POST,
                headers = MutableHeaders(
                    Pair("Content-Type", "application/json"),
                    Pair("User-Agent", context.UAQwant)
                ),
                body = Request.Body.fromString(payload)
            )
            try {
                client.fetch(request).use { response ->
                    if (response.status != 204) {
                        Log.e("QWANT_DATAHUB", "Error sending datahub request:\n" +
                                "endpoint: $endpoint\n" +
                                "payload: $payload\n" +
                                "status: ${response.status}\n" +
                                "body: ${response.body.string()}")
                    }
                }
            } catch (e: Exception) {
                Log.e("QWANT_DATAHUB", "Error sending datahub request:\n" +
                        "url: ${BASE_URL + endpoint}\n" +
                        "payload: $payload\n" +
                        "exception: $e")
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://www.qwant.com/action/" // BuildConfig.DATAHUB_BASE_URL
        private const val BRAND_SUGGEST_PAYLOAD = "{" +
            "\"client\": \"%s\"," +                         // 0: client
            "\"interface_language\": \"%s\"," +             // 1: interface_language
            "\"tgp\": 0," +
            "\"uri\": \"\"," +
            "\"data\": {" +
                "\"ad_type\": \"brand-suggest\"," +
                "\"ad_version\": \"customadserver\"," +
                "\"locale\": \"%s\"," +                     // 2: locale
                "\"brand\": \"%s\"," +                      // 3: ad brand
                "\"count\": %d," +                          // 4: query length
                "\"device\": \"smartphone\"," +
                "\"position\": %d," +                       // 5: ad position
                "\"query\": \"%s\"," +                      // 6: query
                "\"suggest_type\": %d," +                   // 7: ad suggestType
                "\"tgp\": 0," +
                "\"type\": \"ad\"," +
                "\"url\": \"%s\"" +                         // 8: ad URL
            "}" +
        "}"
    }
}