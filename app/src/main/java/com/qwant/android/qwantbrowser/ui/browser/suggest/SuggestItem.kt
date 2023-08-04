package com.qwant.android.qwantbrowser.ui.browser.suggest

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.legacy.bookmarks.BookmarksStorage
import com.qwant.android.qwantbrowser.legacy.history.History
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.suggest.providers.QwantOpensearchProvider
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.providers.SessionTabsProvider
import com.qwant.android.qwantbrowser.ui.widgets.UrlIcon
import mozilla.components.browser.icons.BrowserIcons


fun <T> IntRange.toAnnotatedStringRange(item: T) : AnnotatedString.Range<T> =
    AnnotatedString.Range(item, this.first, this.last + 1)

@Composable internal fun String.toSuggestAnnotatedString(
    search: String
): AnnotatedString {
    val color = LocalContentColor.current
    return AnnotatedString(
        text = this,
        spanStyles = Regex(search, setOf(RegexOption.IGNORE_CASE, RegexOption.LITERAL))
            .find(input = this)?.let { listOf(
                it.range.toAnnotatedStringRange(SpanStyle(
                    color = color.copy(0.8f),
                    fontWeight = FontWeight.Normal
                ))
            )}
            ?: listOf()
    )
}

@Composable
fun SuggestItem(
    suggestion: Suggestion,
    toolbarPosition: ToolbarPosition,
    browserIcons: BrowserIcons,
    modifier: Modifier = Modifier
) {
    WebsiteRow(
        title = when (suggestion) {
            is Suggestion.SearchSuggestion -> suggestion.text
            is Suggestion.OpenTabSuggestion -> suggestion.title ?: ""
            is Suggestion.SelectTabSuggestion -> suggestion.title
        },
        subtitle = when (suggestion) {
            is Suggestion.SearchSuggestion -> null
            is Suggestion.OpenTabSuggestion -> suggestion.url
            is Suggestion.SelectTabSuggestion -> stringResource(id = R.string.browser_go_to_tab)
        },
        leading = {
            when (suggestion.provider) {
                is QwantOpensearchProvider -> SuggestIcon(R.drawable.icons_search)
                is BookmarksStorage ->
                    SuggestUrlIcon(
                        browserIcons = browserIcons,
                        url = (suggestion as Suggestion.OpenTabSuggestion).url,
                        miniature = R.drawable.icons_bookmark
                    )
                is History ->
                    SuggestUrlIcon(
                        browserIcons = browserIcons,
                        url = (suggestion as Suggestion.OpenTabSuggestion).url,
                        miniature = R.drawable.icons_history
                    )
                is SessionTabsProvider ->
                    SuggestUrlIcon(
                        browserIcons = browserIcons,
                        url = (suggestion as Suggestion.SelectTabSuggestion).url,
                        miniature = R.drawable.icons_checkbox_unchecked
                    )
                // "Clipboard" -> Icon(Icons.Outlined.Person, contentDescription = "Logo clipboard")
                // "Domains" -> ...
                // "Search history" -> ...
                else -> SuggestIcon(R.drawable.icons_search)
            }
        },
        trailing = {
            Icon(
                painter = painterResource(id = when (suggestion) {
                    is Suggestion.SearchSuggestion, is Suggestion.OpenTabSuggestion -> when (toolbarPosition) {
                        ToolbarPosition.TOP -> R.drawable.icons_arrow_backward_up
                        else -> R.drawable.icons_arrow_backward_down
                    }
                    is Suggestion.SelectTabSuggestion -> R.drawable.icons_arrow_tab
                }),
                contentDescription = "Go",
                tint = LocalContentColor.current.copy(0.6f),
                modifier = Modifier.size(24.dp)
            )
        },
        highlight = suggestion.search,
        modifier = modifier
    )
}

@Composable
fun WebsiteRow(
    title: String?,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    leading: @Composable RowScope.() -> Unit = {},
    trailing: @Composable RowScope.() -> Unit = {},
    highlight: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        leading()

        Column(modifier = Modifier
            .weight(2f)
            .padding(start = 12.dp)
        ) {
            Text(
                text = highlight?.let {
                    title?.toSuggestAnnotatedString(it)
                } ?: AnnotatedString(title ?: ""),
                fontSize = 16.sp,
                fontWeight = if (highlight != null) FontWeight.Bold else FontWeight.Normal,
                lineHeight = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            subtitle?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    lineHeight = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        trailing()
    }
}

@Composable
fun WebsiteRowWithIcon(
    title: String?,
    url: String,
    browserIcons: BrowserIcons,
    modifier: Modifier = Modifier,
    trailing: @Composable RowScope.() -> Unit = {}
) {
    WebsiteRow(
        title = title,
        subtitle = url,
        modifier = modifier,
        trailing = trailing,
        leading = {
            UrlIcon(
                browserIcons = browserIcons,
                url = url,
                modifier = Modifier
                    .size(32.dp)
                    .clip(
                        RoundedCornerShape(4.dp)
                    )
            )
        }
    )
}

@Composable
fun SuggestIcon(
    @DrawableRes icon: Int
) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "Suggest icon",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(4.dp)
            .size(24.dp)

    )
}

@Composable
fun SuggestUrlIcon(
    browserIcons: BrowserIcons,
    url: String?,
    @DrawableRes miniature: Int
) {
    Box(modifier = Modifier.size(32.dp)) {
        UrlIcon(
            browserIcons = browserIcons,
            url = url,
            modifier = Modifier.clip(RoundedCornerShape(4.dp))
        )
        Icon(
            painter = painterResource(id = miniature),
            contentDescription = "miniature",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(18.dp)
                .align(Alignment.BottomEnd)
                .offset(4.dp, 4.dp)
                .background(MaterialTheme.colorScheme.background, CircleShape)
                .padding(2.dp)
        )
    }
}