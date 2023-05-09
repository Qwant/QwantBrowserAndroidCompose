package com.qwant.android.qwantbrowser.ui.browser

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.EngineView
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.SessionFeature
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.ThumbnailFeature
import com.qwant.android.qwantbrowser.ui.browser.toolbar.*
import com.qwant.android.qwantbrowser.ui.nav.NavDestination
import com.qwant.android.qwantbrowser.ui.widgets.IconAction


@Composable
fun BrowserScreen(
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel = hiltViewModel()
) {
    val currentUrl by viewModel.currentUrl.collectAsState()
    val canGoBack by viewModel.canGoBack.collectAsState()

    val shouldHideToolbarOnScroll by viewModel.toolbarState.shouldHideOnScroll.collectAsState()

    if (currentUrl != null) {
        // TODO Modifier.pullRefresh(pullRefreshState, enabled = true)
        // val pullRefreshState = rememberPullRefreshState(refreshing = false, onRefresh = { Log.d("QWANT_PULLREFRESH", "refresh") })
        // PullRefreshIndicator(false, pullRefreshState, Modifier.align(Alignment.TopCenter))

        HideOnScrollToolbar(
            toolbarState = viewModel.toolbarState,
            toolbar = { modifier ->
                Toolbar(
                    onTextCommit = { text -> viewModel.commitSearch(text) },
                    modifier = modifier,
                    toolbarState = viewModel.toolbarState,
                    beforeTextField = { QwantVIPAction() },
                    afterTextField = { AfterActions(navigateTo, viewModel) }
                )
            },
            height = 56.dp,
            modifier = Modifier.fillMaxSize()
        ) { modifier ->
            EngineView(
                engine = viewModel.engine,
                modifier = modifier
            ) { engineView ->
                SessionFeature(
                    engineView = engineView,
                    store = viewModel.store,
                    canGoBack = canGoBack,
                    goBackUseCase = viewModel.goBack,
                    backEnabled = { !viewModel.toolbarState.hasFocus }
                )
                ThumbnailFeature(
                    engineView = engineView,
                    store = viewModel.store
                )
                DynamicToolbarFeature(
                    engineView = engineView,
                    toolbarState = viewModel.toolbarState,
                    enabled = shouldHideToolbarOnScroll
                )
            }
        }
    } else {
        Icon(
            painter = painterResource(id = R.drawable.qwant_logo),
            contentDescription = "Logo qwant",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxSize()
                .padding(64.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QwantVIPAction() {
    Box(
        modifier = Modifier
            // .minimumInteractiveComponentSize()
            .size(24.dp)
            // .clip(IconButtonTokens.StateLayerShape.toShape())
            // .background(color = colors.containerColor(enabled).value)
            .clickable(
                onClick = { },
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = 20.dp
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        BadgedBox(badge = { Badge { Text("8") } }) {
            Image(
                painter = painterResource(id = R.drawable.icons_flags_china),
                contentDescription = "Qwant VIP"
            )
        }
    }
}

@Composable
fun AfterActions(
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel
) {
    val tabCount by viewModel.tabCount.collectAsState()

    Row {
        IconAction(
            label = "Zap",
            icon = Icons.Default.StrikethroughS
        ) { navigateTo(NavDestination.Preferences) }

        IconButton(onClick = { navigateTo(NavDestination.Tabs) }) {
            Text(
                text = if (tabCount > 99) "∞" else tabCount.toString(),
                fontSize = 12.sp,
                modifier = Modifier
                    .border(width = 2.dp, color = LocalContentColor.current)
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }

        BrowserMenu()
    }
}

@Composable
fun BrowserMenu() {
    Box {
        var showMenu by remember { mutableStateOf(false) }

        IconAction(
            label = "Menu",
            icon = Icons.Default.Menu
        ) { showMenu = true }
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest= { showMenu = false }
        ) {
            Text("coucou")
            Text("coucou 2")
            Text("coucou 3")
        }
    }
}
