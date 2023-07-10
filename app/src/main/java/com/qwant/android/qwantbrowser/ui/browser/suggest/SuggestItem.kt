package com.qwant.android.qwantbrowser.ui.browser.suggest

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mozilla.components.concept.awesomebar.AwesomeBar

@Composable
fun SuggestItem(
    suggestion: AwesomeBar.Suggestion,
    group: AwesomeBar.SuggestionProviderGroup,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(4.dp)
    ) {
        // TODO replace suggest icons
        when (group.title) {
            "Opensearch" -> Icon(Icons.Outlined.Search, contentDescription = "Logo opensearch")
            "Clipboard" -> Icon(Icons.Outlined.Person, contentDescription = "Logo clipboard")
            else -> Icon(Icons.Outlined.Search, contentDescription = "Logo unknown")
        }

        Text(
            text = suggestion.title ?: "No title",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp)
        )
    }
}