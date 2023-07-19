package com.qwant.android.qwantbrowser.ui.history

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mozilla.components.concept.storage.VisitInfo
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.browser.suggest.WebsiteRow
import com.qwant.android.qwantbrowser.ui.browser.suggest.WebsiteRowWithIcon
import com.qwant.android.qwantbrowser.ui.theme.GreenUrl
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import mozilla.components.browser.icons.BrowserIcons
import java.text.DecimalFormat
import java.util.Calendar
import java.util.Date


data class MenuItem(val title: String, @DrawableRes val icon: Int, val onClick: () -> Unit)

@Composable
fun HistoryItem(
    visit: VisitInfo,
    browserIcons: BrowserIcons,
    onItemSelected: (visit: VisitInfo, private: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    menuItems: List<MenuItem> = listOf()
) {
    var showMenu by remember { mutableStateOf(false) }

    WebsiteRowWithIcon(
        title = visit.title,
        url = visit.url,
        browserIcons = browserIcons,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemSelected(visit, false) }
            .padding(start = 16.dp)
    ) {
        if (menuItems.isNotEmpty()) {
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(painter = painterResource(id = R.drawable.icons_more_vertical), contentDescription = "more")
                }
                Dropdown(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    menuItems.forEach { menuItem ->
                        DropdownMenuItem(
                            text = { Text(menuItem.title) },
                            onClick = {
                                menuItem.onClick()
                                showMenu = false
                            },
                            leadingIcon = { Icon(painter = painterResource(id = menuItem.icon), contentDescription = menuItem.title) }
                        )
                    }
                }
            }
        }
    }

    /* Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {
        Column(modifier = Modifier
            .weight(2f)
            .clickable { onItemSelected(visit, false) }
            .padding(end = 8.dp)
        ) {
            if (visit.title?.isNotEmpty() == true) {
                Text(visit.title ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Text(
                text = visit.url,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = GreenUrl
                )
            )
        }

        val calendar = Calendar.getInstance().apply { time = Date(visit.visitTime) }
        Text(text = "${calendar.get(Calendar.HOUR_OF_DAY)}:${DecimalFormat("00").format(calendar.get(Calendar.MINUTE))}")

        if (menuItems.isNotEmpty()) {
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(painter = painterResource(id = R.drawable.icons_more_vertical), contentDescription = "more")
                }
                Dropdown(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    menuItems.forEach { menuItem ->
                        DropdownMenuItem(
                            text = { Text(menuItem.title) },
                            onClick = {
                                menuItem.onClick()
                                showMenu = false
                            },
                            leadingIcon = { Icon(painter = painterResource(id = menuItem.icon), contentDescription = menuItem.title) }
                        )
                    }
                }
            }
        }
    } */
}