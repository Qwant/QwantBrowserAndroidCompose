package com.qwant.android.qwantbrowser.suggest.providers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import com.qwant.android.qwantbrowser.storage.QwantClientProvider
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.SuggestionProvider
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import mozilla.components.concept.fetch.Client
import mozilla.components.concept.fetch.Request
import mozilla.components.support.ktx.android.org.json.toList
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.min

@Singleton
class QwantSuggestProvider @Inject constructor(
    private val client: Client,
    private val frontEndPreferencesRepository: FrontEndPreferencesRepository,
    private val qwantClientProvider: QwantClientProvider
): SuggestionProvider {
    private val scope = MainScope()
    private var locale: String? = null

    init {
        scope.launch {
            frontEndPreferencesRepository.flow
                .map { it.searchResultRegion } // TODO use this or interfaceLanguage ?
                .collect {
                    locale = it
                }
        }
    }

    override suspend fun getSuggestions(text: String): List<Suggestion> {
        if (text.isNotEmpty()) {
            try {
                val request = Request(SuggestFormatUrl.format(qwantClientProvider.client, locale, text))
                client.fetch(request).use { response ->
                    if (response.status == 200) {
                        response.body.use { body ->
                            val jsonResponse = JSONObject(body.string())
                            val data = jsonResponse.getJSONObject("data")
                            val standardSuggestions = data.getJSONArray("items")
                            val brandSuggestions = data.getJSONArray("special")

                            return brandSuggestions
                                .toList<JSONObject>()
                                .filter { it.getString("type") == "brand_suggest" }
                                .take(2) // TODO make this suggestion limit a parameter
                                .mapIndexed { index, jsonObject ->
                                    // TODO Add the option to request any icon to BrowserIcons mozilla component and contribute
                                    var favicon: Bitmap? = null
                                    try {
                                        client.fetch(Request(jsonObject.getString("favicon_url"))).use { response ->
                                            if (response.status == 200) {
                                                response.body.useStream { stream ->
                                                    favicon = BitmapFactory.decodeStream(stream)
                                                }
                                            }
                                        }
                                    } catch (e: IOException) {
                                        Log.e(LOGTAG, "Error fetching ad favicon", e)
                                    } catch (e: Exception) {
                                        Log.e(LOGTAG, "Error decoding ad favicon stream", e)
                                    }

                                    Suggestion.BrandSuggestion(
                                        provider = this,
                                        search = text,
                                        title = jsonObject.getString("name"),
                                        url = jsonObject.getString("url"),
                                        favicon = favicon,
                                        brand = jsonObject.getString("brand"),
                                        domain = jsonObject.getString("domain"),
                                        rank = index + 1,
                                        suggestType = jsonObject.getInt("suggestType")
                                    )
                                }
                                .plus(
                                    standardSuggestions.toList<JSONObject>()
                                        .take(6) // TODO make this suggestion limit a parameter
                                        .map { Suggestion.SearchSuggestion(
                                            provider = this,
                                            search = text,
                                            text = it.getString("value")
                                        ) }
                                )
                        }
                    } else {
                        Log.e(LOGTAG, "Error requesting opensearch results: status not 200 (${response.status})")
                        return listOf()
                    }
                }
            } catch (e: JSONException) {
                Log.e(LOGTAG, "Error decoding JSON of opensearch results")
                return listOf()
            } catch (e: IOException) {
                Log.e(LOGTAG, "Error decoding JSON of opensearch results: No internet ?", e)
                return listOf()
            }
        }
        return listOf()
    }

    companion object {
        private const val LOGTAG = "QB_SEARCH_PROVIDER"
        private const val SuggestFormatUrl = "https://api.qwant.com/v3/suggest?client=%s&locale=%s&version=2&q=%s"
    }
}

