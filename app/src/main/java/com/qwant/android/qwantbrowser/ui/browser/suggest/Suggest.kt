package com.qwant.android.qwantbrowser.ui.browser.suggest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.qwant.android.qwantbrowser.suggest.Suggestion

@Composable
fun Suggest(
    suggestions: List<Suggestion>,
    onSuggestionClicked: (suggestion: Suggestion) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        items(items = suggestions) { suggestion ->
            SuggestItem(
                suggestion = suggestion,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionClicked(suggestion) }
            )
        }
    }
}