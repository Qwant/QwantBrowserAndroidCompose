package com.qwant.android.qwantbrowser.suggest


enum class SuggestionType {
    OPENSEARCH, HISTORY, CLIPBOARD
}

data class Suggestion(
    val type: SuggestionType,
    val label: String,
    val url: String? = null,
    val description: String? = null,
)


