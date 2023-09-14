package com.qwant.android.qwantbrowser.ui.tabs

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.preferences.app.TabsViewOption
import com.qwant.android.qwantbrowser.ui.PrivacyMode
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.browser.TabOpening
import com.qwant.android.qwantbrowser.ui.browser.ToolbarAction
import com.qwant.android.qwantbrowser.ui.browser.ZapButton
import com.qwant.android.qwantbrowser.ui.preferences.TabsViewPreferenceSelector
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import com.qwant.android.qwantbrowser.ui.widgets.DropdownItem
import com.qwant.android.qwantbrowser.ui.widgets.TabCounter
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog
import mozilla.components.browser.state.state.SessionState
import mozilla.components.browser.state.state.TabSessionState

@Composable
fun TabsScreen(
    onClose: (openNewTab: TabOpening) -> Unit = {},
    appViewModel: QwantApplicationViewModel = hiltViewModel(),
    tabsViewModel: TabsScreenViewModel = hiltViewModel()
) {
    val private by appViewModel.isPrivate.collectAsState()
    val tabs by tabsViewModel.tabs.collectAsState()
    val tabsViewOption by tabsViewModel.tabsViewOption.collectAsState()

    val normalTabsCount by remember(tabs) { derivedStateOf { tabs.count { !it.content.private } } }

    BackHandler {
        onClose(TabOpening.NONE)
    }

    DisposableEffect(true) {
        onDispose {
            appViewModel.setPrivacyMode(PrivacyMode.SELECTED_TAB_PRIVACY)
        }
    }

    Column  {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)) {
            Row(modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
            ) {
                TabIconButton(
                    onClick = { appViewModel.setPrivacyMode(PrivacyMode.NORMAL) },
                    icon = {
                        Box(modifier = Modifier.size(30.dp)) {
                            TabCounter(tabCount = normalTabsCount)
                        }
                    },
                    selected = !private,
                    modifier = Modifier.size(48.dp, 56.dp)
                )
                TabIconButton(
                    onClick = { appViewModel.setPrivacyMode(PrivacyMode.PRIVATE) },
                    icon = { Icon(painter = painterResource(id = R.drawable.icons_privacy_mask), contentDescription = "Tabs") },
                    selected = private,
                    modifier = Modifier.size(48.dp, 56.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                ZapButton(appViewModel)
                ToolbarAction(onClick = { onClose(if (private) TabOpening.PRIVATE else TabOpening.NORMAL) }) {
                    Icon(painter = painterResource(id = R.drawable.icons_add_tab), contentDescription = "Add tab")
                }
                TabsMenuMore(
                    tabsViewOption = tabsViewOption,
                    private = private,
                    onTabsViewOptionChange = { tabsViewModel.updateTabsViewOption(it) },
                    onRemoveTabs = {
                        tabsViewModel.removeTabs(private)
                        if (private) {
                            appViewModel.setPrivacyMode(PrivacyMode.NORMAL)
                        } else {
                            onClose(TabOpening.NORMAL)
                        }
                    }
                )
            }
        }

        Divider()

        AnimatedTabList(tabs = tabs, private = private, onClose = onClose, tabsViewModel = tabsViewModel, tabsViewOption)
    }
}

@Composable
fun TabsMenuMore(
    tabsViewOption: TabsViewOption,
    private: Boolean,
    onTabsViewOptionChange: (TabsViewOption) -> Unit,
    onRemoveTabs: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var showViewOptionPopup by remember { mutableStateOf(false) }

    Box {
        ToolbarAction(onClick = { showMenu = true }) {
            Icon(
                painter = painterResource(id = R.drawable.icons_more_vertical),
                contentDescription = "More"
            )
        }
        Dropdown(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownItem(
                text = stringResource(id = if (private) R.string.browser_close_private_tabs else R.string.browser_close_all_tabs),
                icon = R.drawable.icons_close,
                onClick = {
                    showMenu = false
                    onRemoveTabs()
                }
            )
            DropdownItem(
                text = stringResource(id = R.string.tabs_view_label),
                icon = R.drawable.icons_grid,
                onClick = {
                    showMenu = false
                    showViewOptionPopup = true
                }
            )
        }
    }
    if (showViewOptionPopup) {
        YesNoDialog(
            onDismissRequest = { showViewOptionPopup = false },
            onYes = { showViewOptionPopup = false },
            onNo = { showViewOptionPopup = false },  // TODO revert to settings before opening popup on cancel
            title = stringResource(id = R.string.tabs_view_label),
            additionalContent = {
                Box(modifier = Modifier.padding(top = 8.dp)) {
                    TabsViewPreferenceSelector(
                        value = tabsViewOption,
                        onValueChange = onTabsViewOptionChange
                    )
                }
            }
        )
    }
}

@Composable
fun AnimatedTabList(
    tabs: List<TabSessionState>,
    private: Boolean,
    onClose: (openNewTab: TabOpening) -> Unit,
    tabsViewModel: TabsScreenViewModel,
    tabsViewOption: TabsViewOption
) {
    val selectedTabId by tabsViewModel.selectedTabId.collectAsState()
    val normalTabs by remember(tabs) { derivedStateOf { tabs.filter { !it.content.private }.reversed() } }
    val privateTabs by remember(tabs) { derivedStateOf { tabs.filter { it.content.private }.reversed() } }


    Box(Modifier.fillMaxSize()) {
        val onTabSelected = { tab: SessionState ->
            tabsViewModel.selectTab(tab.id)
            onClose(TabOpening.NONE)
        }
        val onTabDeleted: (TabSessionState) -> Unit = { tab: SessionState -> tabsViewModel.removeTab(tab.id) }

        AnimatedVisibility(
            visible = private,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it })
        ) {
            TabView(
                tabs = privateTabs,
                selectedTabId = selectedTabId,
                thumbnailStorage = tabsViewModel.thumbnailStorage,
                browserIcons = tabsViewModel.browserIcons,
                modifier = Modifier.fillMaxHeight(),
                onTabSelected = onTabSelected,
                onTabDeleted = onTabDeleted,
                tabsViewOption = tabsViewOption
            )
        }

        AnimatedVisibility(
            visible = !private,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            TabView(
                tabs = normalTabs,
                selectedTabId = selectedTabId,
                thumbnailStorage = tabsViewModel.thumbnailStorage,
                browserIcons = tabsViewModel.browserIcons,
                modifier = Modifier.fillMaxHeight(),
                onTabSelected = onTabSelected,
                onTabDeleted = onTabDeleted,
                tabsViewOption = tabsViewOption
            )
        }
    }
}

@Composable
fun TabIconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .minimumInteractiveComponentSize()
        .clickable(onClick = onClick)
    ) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            val contentColor = if (selected) MaterialTheme.colorScheme.primary else LocalContentColor.current
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                icon()
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            AnimatedVisibility(visible = selected) {
                Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}