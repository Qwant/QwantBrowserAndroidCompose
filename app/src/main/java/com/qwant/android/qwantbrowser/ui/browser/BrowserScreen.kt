package com.qwant.android.qwantbrowser.ui.browser

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.*
import com.qwant.android.qwantbrowser.legacy.onboarding.Onboarding
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.browser.home.HomePrivateBrowsing
import com.qwant.android.qwantbrowser.ui.browser.menu.BrowserMenu
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.*
import com.qwant.android.qwantbrowser.ui.browser.toolbar.*
import com.qwant.android.qwantbrowser.ui.nav.NavDestination
import com.qwant.android.qwantbrowser.ui.theme.LocalQwantTheme
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import com.qwant.android.qwantbrowser.ui.widgets.TabCounter
import com.qwant.android.qwantbrowser.vip.VipSessionObserver
import mozilla.components.concept.engine.EngineView

enum class TabOpening {
    NONE, NORMAL, PRIVATE
}

@Composable
fun BrowserScreen(
    navigateTo: (NavDestination) -> Unit,
    appViewModel: QwantApplicationViewModel = hiltViewModel(),
    viewModel: BrowserScreenViewModel = hiltViewModel(),
    openNewTab: TabOpening = TabOpening.NONE
) {
    val currentUrl by viewModel.currentUrl.collectAsState()
    val tabCount by viewModel.tabCount.collectAsState()
    val private by appViewModel.isPrivate.collectAsState()


    /* TODO Pull To Refresh
    var refreshing by remember { mutableStateOf(false) }
    val refreshScope = rememberCoroutineScope()
    fun refresh() = refreshScope.launch {
        Log.d("QWANT_PULLREFRESH", "refresh")
        refreshing = true
        delay(1500)
        refreshing = false
    }
    val pullRefreshState = rememberPullRefreshState(refreshing, ::refresh) */

    LaunchedEffect(true) {
        when (openNewTab) {
            TabOpening.NORMAL -> viewModel.openNewQwantTab(private = false)
            TabOpening.PRIVATE -> viewModel.openNewQwantTab(private = true)
            else -> {}
        }
    }
    LaunchedEffect(tabCount) {
        if (tabCount == 0) {
            viewModel.openSafetyTabIfNeeded()
        }
    }

    val activity = LocalContext.current.activity
    Onboarding { success ->
        if (success) {
            viewModel.toolbarState.updateFocus(true)
        } else {
            activity?.quit()
        }
    }

    HideOnScrollToolbar(
        toolbarState = viewModel.toolbarState,
        toolbar = { modifier ->
            Toolbar(
                onTextCommit = { text -> viewModel.commitSearch(text) },
                modifier = modifier,
                toolbarState = viewModel.toolbarState,
                browserIcons = viewModel.browserIcons,
                beforeTextField = { QwantVIPAction(viewModel) },
                beforeTextFieldVisible = { !viewModel.toolbarState.hasFocus && currentUrl?.isNotBlank() ?: false && !(currentUrl?.isQwantUrl() ?: false) },
                afterTextField = { AfterActions(navigateTo, viewModel, appViewModel) },
                afterTextFieldVisible = { !viewModel.toolbarState.hasFocus }
            )
        },
        height = 56.dp,
        modifier = Modifier.fillMaxSize(),
        lock = { viewModel.showFindInPage }
    ) { modifier ->
        if (currentUrl != null) {
            if (currentUrl == "" && private) {
                HomePrivateBrowsing(modifier)
            } else {
                // Box(modifier.pullRefresh(pullRefreshState, enabled = true)) {
                GlobalFeatures(appViewModel, viewModel)

                EngineView(
                    engine = viewModel.engine,
                    modifier = modifier
                ) { engineView ->
                    EngineViewFeatures(engineView, viewModel)
                }

                // PullRefreshIndicator(false, pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
                //}
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToolbarAction( // TODO rename ToolbarAction to SmallIconButton and move to global widgets
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .width(40.dp)
            .fillMaxHeight()
            .padding(8.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
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
        content()
    }
}


// TODO Clean up and separate code for Qwant VIP UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QwantVIPAction(
    viewModel: BrowserScreenViewModel//  = hiltViewModel(),
    // state: WebExtensionState
) {
    val theme = LocalQwantTheme.current
    val imageID = when {
        theme.dark || theme.private -> R.drawable.icons_webext_vip_night
        else -> R.drawable.icons_webext_vip
    }

    val state by viewModel.vipExtensionState.collectAsState()
    val iconPainter by viewModel.vipIcon.collectAsState()
    val counter by viewModel.vipCounter.collectAsState()

    Box {
        ToolbarAction(onClick = { state?.browserAction?.onClick?.invoke() }) {
            BadgedBox(
                badge = {
                    if (counter?.isNotEmpty() == true) {
                        Badge(
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentColor = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primaryContainer,
                                    RoundedCornerShape(50)
                                )
                        ) {
                            counter?.let {
                                Text(text = it, maxLines = 1)
                            }
                        }
                    }
                }
            ) {
                Image(
                    painter = iconPainter ?: painterResource(id = imageID),
                    contentDescription = "Qwant VIP",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        if (state?.popupSession != null) {
            Dialog(
                properties = DialogProperties(usePlatformDefaultWidth = false),
                onDismissRequest = { viewModel.closeQwantVIPPopup() }
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var engineView: EngineView? by remember { mutableStateOf(null) }
                    val sessionObserver = remember {
                        VipSessionObserver(
                            closePopup = { viewModel.closeQwantVIPPopup() },
                            loadUrl = {
                                viewModel.tabsUseCases.addTab(it, selectTab = true)
                                viewModel.closeQwantVIPPopup()
                            }
                        )
                    }

                    val configuration = LocalConfiguration.current

                    DisposableEffect(engineView, state?.popupSession) {
                        state?.popupSession?.let {
                            it.register(sessionObserver)
                            engineView?.render(it)
                        }
                        onDispose {
                            state?.popupSession?.unregister(sessionObserver)
                            engineView?.release()
                        }
                    }

                    AndroidView(
                        modifier = Modifier.size(
                            width = configuration.screenWidthDp.dp.times(0.75f),
                            height = configuration.screenHeightDp.dp.times(0.75f)
                        ),
                        factory = { context ->
                            viewModel.engine.createView(context).asView()
                        },
                        update = { view ->
                            engineView = view as EngineView
                        }
                    )
                }
            }
        }
        /* Dropdown(
            expanded = state?.popupSession != null,
            onDismissRequest = { viewModel.closeQwantVIPPopup() },
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 8.dp)
        ) {

        } */
    }
}

@Composable
fun AfterActions(
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel,
    appViewModel: QwantApplicationViewModel
) {
    Row {
        ZapButton { appViewModel.zap() }
        TabsButton(navigateTo, viewModel)
        BrowserMenuButton(navigateTo, viewModel, appViewModel)
    }
}

@Composable fun ZapButton(
    zap: () -> Unit
) {
    val theme = LocalQwantTheme.current
    val zapImageID = when {
        theme.dark || theme.private -> R.drawable.icons_zap_night
        else -> R.drawable.icons_zap
    }

    ToolbarAction(onClick = { zap() }) {
        Image(
            painter = painterResource(id = zapImageID),
            contentDescription = "Zap",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsButton(
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel
) {
    val tabCount by viewModel.tabCount.collectAsState()
    var showTabsDropdown by remember { mutableStateOf(false) }

    Box {
        ToolbarAction(
            onClick = { navigateTo(NavDestination.Tabs) },
            onLongClick = { showTabsDropdown = true }
        ) {
            BadgedBox(
                badge = {
                    if (LocalQwantTheme.current.private) {
                        Badge(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier
                                .offset(x = (-4).dp, y = 2.dp)
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primaryContainer,
                                    CircleShape
                                )
                        ) {
                            Icon(
                                painterResource(R.drawable.icons_privacy_mask_small),
                                contentDescription = "private navigation indicator"
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp)
            ) {
                TabCounter(tabCount)
            }
        }
        Dropdown(
            expanded = showTabsDropdown,
            onDismissRequest = { showTabsDropdown = false },
        ) {
            DropdownMenuItem(
                text = { Text("Fermer cet onglet") }, // TODO text
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_close), contentDescription = "close this tab")},
                onClick = {
                    viewModel.closeCurrentTab()
                    showTabsDropdown = false
                }
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            DropdownMenuItem(
                text = { Text("Open tab") }, // TODO text
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_add_tab), contentDescription = "add tab")},
                onClick = {
                    viewModel.openNewQwantTab(false)
                    showTabsDropdown = false
                }
            )
            DropdownMenuItem(
                text = { Text("Open private tab") }, // TODO text
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.icons_privacy_mask), contentDescription = "add private tab")},
                onClick = {
                    viewModel.openNewQwantTab(true)
                    showTabsDropdown = false
                }
            )
        }
    }
}

@Composable
fun BrowserMenuButton(
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel,
    appViewModel: QwantApplicationViewModel
) {
    Box {
        var showMenu by remember { mutableStateOf(false) }

        ToolbarAction(onClick = { showMenu = true }) {
            Icon(
                painter = painterResource(id = R.drawable.icons_more_vertical),
                contentDescription = "Menu",
                modifier = Modifier.fillMaxSize(),
                // tint = MaterialTheme.colorScheme.onBackground
            )
        }

        BrowserMenu(
            expanded = showMenu,
            onDismissRequest= { showMenu = false },
            navigateTo = navigateTo,
            viewModel = viewModel,
            applicationViewModel = appViewModel
        )
    }
}
