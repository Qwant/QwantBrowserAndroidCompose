package com.qwant.android.qwantbrowser.ui.browser.suggest

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mozilla.components.concept.awesomebar.AwesomeBar

@Composable
fun Suggest(
    suggestions: Map<AwesomeBar.SuggestionProviderGroup, List<AwesomeBar.Suggestion>>,
    onSuggestionClicked: (suggestion: AwesomeBar.Suggestion) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        suggestions.keys.forEach { group ->
            suggestions[group]?.let {
                items(items = it) { suggestion ->
                    SuggestItem(
                        suggestion = suggestion,
                        group = group,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSuggestionClicked(suggestion) }
                    )
                }
            }
        }
    }
}