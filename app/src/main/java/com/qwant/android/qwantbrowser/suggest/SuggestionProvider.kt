package com.qwant.android.qwantbrowser.suggest


interface SuggestionProvider {
    fun getSuggestions(search: String): List<Suggestion>
}

class GroupedSuggestionProvider(
    private val providers: List<SuggestionProvider>
) : SuggestionProvider {
    override fun getSuggestions(search: String): List<Suggestion> {
        val suggestions = mutableListOf<Suggestion>()
        providers.forEach { provider ->
            suggestions.addAll(provider.getSuggestions(search))
        }
        return suggestions.toList()
    }
}