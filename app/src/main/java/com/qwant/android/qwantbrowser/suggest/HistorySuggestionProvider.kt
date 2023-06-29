package com.qwant.android.qwantbrowser.suggest

import mozilla.components.concept.awesomebar.AwesomeBar
import mozilla.components.concept.storage.HistoryStorage
import mozilla.components.concept.storage.SearchResult
import mozilla.components.feature.session.SessionUseCases
import java.util.UUID

/**
 * Return 20 history suggestions by default.
 */
const val DEFAULT_HISTORY_SUGGESTION_LIMIT = 20

/**
 * A [AwesomeBar.SuggestionProvider] implementation that provides suggestions based on the browsing
 * history stored in the [HistoryStorage].
 *
 * @param historyStorage and instance of the [HistoryStorage] used
 * to query matching history records.
 * @param loadUrlUseCase the use case invoked to load the url when the
 * user clicks on the suggestion.
 * @param maxNumberOfSuggestions optional parameter to specify the maximum number of returned suggestions,
 * defaults to [DEFAULT_HISTORY_SUGGESTION_LIMIT]
 * @param showEditSuggestion optional parameter to specify if the suggestion should show the edit button
 * @param suggestionsHeader optional parameter to specify if the suggestion should have a header
 */
class HistorySuggestionProvider(
    private val historyStorage: HistoryStorage,
    private val loadUrlUseCase: SessionUseCases.LoadUrlUseCase,
    private var maxNumberOfSuggestions: Int = DEFAULT_HISTORY_SUGGESTION_LIMIT,
    private val showEditSuggestion: Boolean = true,
    private val suggestionsHeader: String? = null,
) : AwesomeBar.SuggestionProvider {
    override val id: String = UUID.randomUUID().toString()

    override fun groupTitle(): String? {
        return suggestionsHeader
    }

    override suspend fun onInputChanged(text: String): List<AwesomeBar.Suggestion> {
        historyStorage.cancelReads()

        if (text.isEmpty()) {
            return emptyList()
        }

        val suggestions = historyStorage.getSuggestions(text, maxNumberOfSuggestions)
            .sortedByDescending { it.score }
            .distinctBy { it.id }
            .take(maxNumberOfSuggestions)

        return suggestions.into(this, loadUrlUseCase, showEditSuggestion)
    }
}

internal fun Iterable<SearchResult>.into(
    provider: AwesomeBar.SuggestionProvider,
    loadUrlUseCase: SessionUseCases.LoadUrlUseCase,
    showEditSuggestion: Boolean = true,
): List<AwesomeBar.Suggestion> {
    return this.map { result ->
        AwesomeBar.Suggestion(
            provider = provider,
            id = result.id,
            // icon = icon?.await()?.bitmap,
            title = result.title,
            description = result.url,
            editSuggestion = if (showEditSuggestion) result.url else null,
            score = result.score,
            onSuggestionClicked = {
                loadUrlUseCase.invoke(result.url)
            },
        )
    }
}
