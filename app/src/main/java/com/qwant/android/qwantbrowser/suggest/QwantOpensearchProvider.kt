package com.qwant.android.qwantbrowser.suggest

import android.util.Log
import mozilla.components.concept.fetch.Client
import mozilla.components.concept.fetch.Request
import mozilla.components.support.ktx.android.org.json.toList
import org.json.JSONArray
import org.json.JSONException


class QwantOpensearchProvider(
    private val client: Client
): SuggestionProvider {
    companion object {
        private const val LOGTAG = "QB_SEARCH_PROVIDER"
        private const val OpenSearchBaseUrl = "https://api.qwant.com/v3/suggest/?client=opensearch&q="
    }

    override fun getSuggestions(search: String): List<Suggestion> {
        if (search.isNotEmpty()) {
            val request = Request(url = OpenSearchBaseUrl + search)
            client.fetch(request).use { response ->
                if (response.status == 200) {
                    response.body.use { body ->
                        try {
                            val mainArray = JSONArray(body.string())
                            val suggestionsArray = mainArray.getJSONArray(1)
                            return suggestionsArray.toList<String>().map { s ->
                                Suggestion(SuggestionType.OPENSEARCH, s)
                            }
                        } catch (e: JSONException) {
                            Log.e(LOGTAG, "Error decoding JSON of opensearch results")
                            return listOf()
                        }
                    }
                } else {
                    Log.e(LOGTAG, "Error requesting opensearch results: status not 200 (${response.status})")
                }
            }
        }
        return listOf()
    }
}