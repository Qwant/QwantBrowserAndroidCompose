package com.qwant.android.qwantbrowser.ui.browser.menu

import android.app.DownloadManager
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.activity
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.browser.BrowserScreenViewModel
import com.qwant.android.qwantbrowser.ui.nav.NavDestination
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import mozilla.components.support.ktx.android.content.share

@Composable
fun BrowserMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel,
    applicationViewModel: QwantApplicationViewModel
) {
    val showQuitApp by applicationViewModel.zapOnQuit.collectAsState()

    Dropdown(
        expanded = expanded,
        onDismissRequest= onDismissRequest
    ) {
        BrowserNavigation(viewModel)
        Divider()
        NewTabs(viewModel)
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        AppNavigation(navigateTo)
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        PageActions(viewModel, onDismissRequest)
        Divider(modifier = Modifier.padding(horizontal = 16.dp))

        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.settings)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_settings), contentDescription = "settings") },
            onClick = { navigateTo(NavDestination.Preferences) }
        )
        if (showQuitApp) {
            val activity = (LocalContext.current.activity)
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.menu_quit_app)) },
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_close), contentDescription = "quit") },
                onClick = {
                    applicationViewModel.zap { success ->
                        if (success) {
                            activity?.quit()
                        } else {
                            // TODO handle clear on quit fails
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun BrowserNavigation(viewModel: BrowserScreenViewModel) {
    val canGoBack by viewModel.canGoBack.collectAsState()
    val canGoForward by viewModel.canGoForward.collectAsState()
    val loadingProgress by viewModel.toolbarState.loadingProgress.collectAsState()

    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { viewModel.goBack() }, enabled = canGoBack) {
            Icon(painter = painterResource(id = R.drawable.icons_arrow_backward), contentDescription = "back")
        }
        IconButton(onClick = { viewModel.goForward() }, enabled = canGoForward) {
            Icon(painter = painterResource(id = R.drawable.icons_arrow_forward), contentDescription = "forward")
        }

        if (viewModel.isUrlBookmarked) {
            IconButton(onClick = { viewModel.removeBookmark() }) {
                Icon(painter = painterResource(id = R.drawable.icons_check), contentDescription = "delete bookmark")
            }
        } else {
            IconButton(onClick = { viewModel.addBookmark() }) {
                Icon(painter = painterResource(id = R.drawable.icons_add_bookmark), contentDescription = "add bookmark")
            }
        }


        if (loadingProgress != 1f) {
            IconButton(onClick = { viewModel.stopLoading() }) {
                Icon(painter = painterResource(id = R.drawable.icons_close), contentDescription = "stop loading")
            }
        } else {
            IconButton(onClick = { viewModel.reloadUrl() }) {
                Icon(painter = painterResource(id = R.drawable.icons_reload), contentDescription = "reload")
            }
        }
    }
}

@Composable
fun NewTabs(viewModel: BrowserScreenViewModel) {
    Column {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.browser_new_tab)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_add_tab), contentDescription = "new tab") },
            onClick = { viewModel.openNewQwantTab(private = false) }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.browser_new_tab_private)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_privacy_mask), contentDescription = "new private tab") },
            onClick = { viewModel.openNewQwantTab(private = true)  }
        )
    }
}

@Composable
fun AppNavigation(navigateTo: (NavDestination) -> Unit) {
    val context = LocalContext.current
    Column {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.history)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_history), contentDescription = "History") },
            onClick = { navigateTo(NavDestination.History) }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.bookmarks)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_bookmark), contentDescription = "Bookmarks") },
            onClick = { navigateTo(NavDestination.Bookmarks) }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.browser_downloads)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_download), contentDescription = "Downloads") },
            onClick = { context.startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)) }
        )
    }
}

@Composable
fun PageActions(
    viewModel: BrowserScreenViewModel,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current

    val currentUrl by viewModel.currentUrl.collectAsState()

    val desktopSite by viewModel.desktopMode.collectAsState()
    val onDesktopSiteClicked = { checked: Boolean ->
        viewModel.requestDesktopSite(checked)
    }

    Column {
        currentUrl?.let { url ->
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.share)) },
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_share), contentDescription = "share") },
                onClick = { context.share(url) }
            )
        }
        if (viewModel.isShortcutSupported) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.menu_add_to_homescreen)) },
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_add_screen), contentDescription = "add to homescreen") },
                onClick = { viewModel.addShortcutToHomeScreen() }
            )
        }
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.menu_request_desktop_site)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_laptop), contentDescription = "request desktop site") },
            trailingIcon = { Switch(checked = desktopSite, onCheckedChange = onDesktopSiteClicked) },
            onClick = { onDesktopSiteClicked(!desktopSite) }
        )
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.menu_find_in_page)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_search), contentDescription = "find in page") },
            onClick = {
                viewModel.updateShowFindInPage(true)
                onDismissRequest()
            }
        )
    }
}