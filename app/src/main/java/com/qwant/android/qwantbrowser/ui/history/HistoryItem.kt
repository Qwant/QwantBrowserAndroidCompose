package com.qwant.android.qwantbrowser.ui.history

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mozilla.components.concept.storage.VisitInfo
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.browser.suggest.WebsiteRowWithIcon
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import com.qwant.android.qwantbrowser.ui.widgets.DropdownItem
import mozilla.components.browser.icons.BrowserIcons

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
                        DropdownItem(
                            text = menuItem.title,
                            icon = menuItem.icon,
                            onClick = {
                                menuItem.onClick()
                                showMenu = false
                            }
                        )
                    }
                }
            }
        }
    }
}