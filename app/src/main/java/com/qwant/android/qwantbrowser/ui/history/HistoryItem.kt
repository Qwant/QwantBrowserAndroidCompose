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
import com.qwant.android.qwantbrowser.ui.theme.GreenUrl


data class MenuItem(val title: String, @DrawableRes val icon: Int, val onClick: () -> Unit)

@Composable
fun HistoryItem(
    visit: VisitInfo,
    onItemSelected: (visit: VisitInfo, private: Boolean) -> Unit,
    menuItems: List<MenuItem> = listOf()
) {
    var showMenu by remember { mutableStateOf(false) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
    ) {
        Column(modifier = Modifier
            .weight(2f)
            .align(Alignment.CenterVertically)
            .clickable { onItemSelected(visit, false) }
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
        if (menuItems.isNotEmpty()) {
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(painter = painterResource(id = R.drawable.icons_more_vertical), contentDescription = "more")
                }
                DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                    menuItems.forEach { menuItem ->
                        DropdownMenuItem(
                            text = { Text(menuItem.title) },
                            onClick = menuItem.onClick,
                            leadingIcon = { Icon(painter = painterResource(id = menuItem.icon), contentDescription = menuItem.title) }
                        )
                    }
                    /* DropdownMenuItem(
                        text = { Text("Ouvrir le lien dans un nouvel onglet") },
                        onClick = { onItemSelected(visit, false) },
                        leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_search), contentDescription = "Open in new tab")}
                    )
                    DropdownMenuItem(
                        text = { Text("Ouvrir le lien dans un nouvel onglet privé") },
                        onClick = { onItemSelected(visit, true) },
                        leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_search), contentDescription = "Open in new tab")}
                    )
                    DropdownMenuItem(
                        text = { Text("Copier le lien") },
                        onClick = { /* TODO */ },
                        leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_search), contentDescription = "Open in new tab")}
                    )
                    DropdownMenuItem(
                        text = { Text("Supprimer") },
                        onClick = { /* TODO */ },
                        leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_search), contentDescription = "Open in new tab")}
                    ) */
                }
            }
        }
    }
}