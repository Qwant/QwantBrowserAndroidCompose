package com.qwant.android.qwantbrowser.suggest

import android.util.Log
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import mozilla.components.concept.awesomebar.AwesomeBar
import mozilla.components.concept.fetch.Client
import mozilla.components.concept.fetch.Request
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.support.ktx.android.org.json.toList
import org.json.JSONArray
import org.json.JSONException
import java.util.*


class QwantOpensearchProvider(
    private val client: Client,
    private val loadSERPPageUseCase: QwantUseCases.LoadSERPPageUseCase
): AwesomeBar.SuggestionProvider {
    override val id: String = UUID.randomUUID().toString()

    override suspend fun onInputChanged(text: String): List<AwesomeBar.Suggestion> {
        if (text.isNotEmpty()) {
            val request = Request(url = OpenSearchBaseUrl + text)
            client.fetch(request).use { response ->
                if (response.status == 200) {
                    response.body.use { body ->
                        try {
                            val mainArray = JSONArray(body.string())
                            val suggestionsArray = mainArray.getJSONArray(1)
                            return suggestionsArray.toList<String>().map { s ->
                                AwesomeBar.Suggestion(
                                    provider = this,
                                    title = s,
                                    onSuggestionClicked = {
                                        MainScope().launch {
                                            loadSERPPageUseCase(s)
                                        }
                                    }
                                )
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

    companion object {
        private const val LOGTAG = "QB_SEARCH_PROVIDER"
        private const val OpenSearchBaseUrl = "https://api.qwant.com/v3/suggest/?client=opensearch&q="
    }
}