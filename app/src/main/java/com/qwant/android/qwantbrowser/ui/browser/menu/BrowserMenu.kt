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
import com.qwant.android.qwantbrowser.ui.widgets.DropdownItem
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
        onDismissRequest = onDismissRequest
    ) {
        BrowserNavigation(viewModel)
        Divider()
        NewTabs(viewModel, onDismissRequest)
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        AppNavigation(navigateTo, onDismissRequest)
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        PageActions(viewModel, onDismissRequest)
        Divider(modifier = Modifier.padding(horizontal = 16.dp))

        DropdownItem(
            text = stringResource(id = R.string.settings),
            icon = R.drawable.icons_settings,
            onClick = {
                onDismissRequest()
                navigateTo(NavDestination.Preferences)
            }
        )
        if (showQuitApp) {
            val activity = (LocalContext.current.activity)
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            DropdownItem(
                text = stringResource(id = R.string.menu_quit_app),
                icon = R.drawable.icons_close,
                onClick = {
                    applicationViewModel.zap { success, _ ->
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
                Icon(painter = painterResource(id = R.drawable.icons_delete_bookmark), contentDescription = "delete bookmark")
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
fun NewTabs(viewModel: BrowserScreenViewModel, onDismissRequest: () -> Unit) {
    Column {
        DropdownItem(
            text = stringResource(id = R.string.browser_new_tab),
            icon = R.drawable.icons_add_tab,
            onClick = {
                onDismissRequest()
                viewModel.openNewQwantTab(private = false)
            }
        )
        DropdownItem(
            text = stringResource(id = R.string.browser_new_tab_private),
            icon = R.drawable.icons_privacy_mask,
            onClick = {
                onDismissRequest()
                viewModel.openNewQwantTab(private = true)
            }
        )
    }
}

@Composable
fun AppNavigation(
    navigateTo: (NavDestination) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    Column {
        DropdownItem(
            text = stringResource(id = R.string.history),
            icon = R.drawable.icons_history,
            onClick = {
                onDismissRequest()
                navigateTo(NavDestination.History)
            }
        )
        DropdownItem(
            text = stringResource(id = R.string.bookmarks),
            icon = R.drawable.icons_bookmark,
            onClick = {
                onDismissRequest()
                navigateTo(NavDestination.Bookmarks)
            }
        )
        DropdownItem(
            text = stringResource(id = R.string.browser_downloads),
            icon = R.drawable.icons_download,
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
            DropdownItem(
                text = stringResource(id = R.string.share),
                icon = R.drawable.icons_share,
                onClick = { context.share(url) }
            )
        }
        if (viewModel.isShortcutSupported) {
            DropdownItem(
                text = stringResource(id = R.string.menu_add_to_homescreen),
                icon = R.drawable.icons_add_screen,
                onClick = { viewModel.addShortcutToHomeScreen() }
            )
        }
        DropdownItem(
            text = stringResource(id = R.string.menu_request_desktop_site),
            icon = R.drawable.icons_laptop,
            trailing = { Switch(checked = desktopSite, onCheckedChange = onDesktopSiteClicked) },
            onClick = { onDesktopSiteClicked(!desktopSite) }
        )
        DropdownItem(
            text = stringResource(id = R.string.menu_find_in_page),
            icon = R.drawable.icons_search,
            onClick = {
                viewModel.updateShowFindInPage(true)
                onDismissRequest()
            }
        )
    }
}