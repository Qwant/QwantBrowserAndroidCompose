package com.qwant.android.qwantbrowser.suggest

interface SuggestionProvider {
    suspend fun getSuggestions(text: String): List<Suggestion>
}