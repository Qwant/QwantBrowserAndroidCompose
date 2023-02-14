package com.example.qwantbrowsercompose.ui.browser.suggest

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.qwantbrowsercompose.suggest.Suggestion
import com.example.qwantbrowsercompose.suggest.SuggestionType

@Composable
fun SuggestItem(
    suggestion: Suggestion,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(4.dp)
    ) {
        when (suggestion.type) {
            SuggestionType.OPENSEARCH -> Icon(Icons.Outlined.Search, contentDescription = "Logo opensearch")
            SuggestionType.CLIPBOARD -> Icon(Icons.Outlined.ContentPaste, contentDescription = "Logo clipboard")
            else -> Icon(Icons.Outlined.ContentPaste, contentDescription = "Logo clipboard")
        }

        Text(
            text = suggestion.label,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp)
        )
    }
}