package com.qwant.android.qwantbrowser.suggest.providers

import android.util.Log
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.SuggestionProvider
import mozilla.components.concept.fetch.Client
import mozilla.components.concept.fetch.Request
import mozilla.components.support.ktx.android.org.json.toList
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class QwantOpensearchProvider(
    private val client: Client
): SuggestionProvider {
    override suspend fun getSuggestions(text: String): List<Suggestion> {
        if (text.isNotEmpty()) {
            try {
                val request = Request(url = OpenSearchBaseUrl + text)
                client.fetch(request).use { response ->
                    if (response.status == 200) {
                        response.body.use { body ->
                            try {
                                val mainArray = JSONArray(body.string())
                                val suggestionsArray = mainArray.getJSONArray(1)
                                return suggestionsArray.toList<String>().map { s ->
                                    Suggestion.SearchSuggestion(this, text, s)
                                }.take(6) // TODO make this suggestion limit a parameter
                            } catch (e: JSONException) {
                                Log.e(LOGTAG, "Error decoding JSON of opensearch results")
                                return listOf()
                            }
                        }
                    } else {
                        Log.e(
                            LOGTAG,
                            "Error requesting opensearch results: status not 200 (${response.status})"
                        )
                        return listOf()
                    }
                }
            } catch (e: IOException) {
                Log.e(LOGTAG, "Error decoding JSON of opensearch results: No internet ($e)")
                return listOf()
            }
        }
        return listOf()
    }

    companion object {
        private const val LOGTAG = "QB_SEARCH_PROVIDER"
        private const val OpenSearchBaseUrl = "https://api.qwant.com/v3/suggest/?client=opensearch&q="
    }
}

