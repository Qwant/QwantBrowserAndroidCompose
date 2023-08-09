package com.qwant.android.qwantbrowser.suggest.providers

import android.content.Context
import android.util.Log
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.SuggestionProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import javax.inject.Inject

class DomainProvider @Inject constructor(
    @ApplicationContext val context: Context
) : SuggestionProvider {
    val domains: List<String> = try {
        context.assets.open("domains.txt").bufferedReader().use(BufferedReader::readLines)
    } catch (e: Exception) {
        Log.e("QB_DOMAIN_PROVIDER", "Failed to load domains from file")
        listOf()
    }

    override suspend fun getSuggestions(text: String) = domains
        .firstOrNull { it.startsWith(text) }
        ?.let { listOf(Suggestion.SearchSuggestion(this, text, it)) }
        ?: listOf()
}