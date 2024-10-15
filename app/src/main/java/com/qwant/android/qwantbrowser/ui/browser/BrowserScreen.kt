package com.qwant.android.qwantbrowser.ui.browser

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.*
import com.qwant.android.qwantbrowser.contentBlocker.ContentBlockerOverlay
import com.qwant.android.qwantbrowser.contentBlocker.ContentBlockerState
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.browser.home.HomePrivateBrowsing
import com.qwant.android.qwantbrowser.ui.browser.menu.BrowserMenu
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.*
import com.qwant.android.qwantbrowser.ui.browser.pullToRefresh.PullToRefreshBox
import com.qwant.android.qwantbrowser.ui.browser.toolbar.*
import com.qwant.android.qwantbrowser.ui.nav.NavDestination
import com.qwant.android.qwantbrowser.ui.theme.LocalQwantTheme
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import com.qwant.android.qwantbrowser.ui.widgets.DropdownItem
import com.qwant.android.qwantbrowser.ui.widgets.TabCounter
import com.qwant.android.qwantbrowser.ui.zap.ZapButton
import com.qwant.android.qwantbrowser.vip.VipSessionObserver
import kotlinx.coroutines.delay
import mozilla.components.concept.engine.EngineView
import com.qwant.android.qwantbrowser.BuildConfig

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

    var engineViewHolder: EngineView? by remember { mutableStateOf(null) }

    LaunchedEffect(true) {
        when (openNewTab) {
            TabOpening.NORMAL -> viewModel.openNewQwantTab(private = false)
            TabOpening.PRIVATE -> viewModel.openNewQwantTab(private = true)
            else -> {}
        }
    }
    LaunchedEffect(true) {
        if (tabCount == 0) {
            viewModel.openSafetyTabIfNeeded()
        }
    }

    /* val activity = LocalContext.current.activity
    Onboarding { success ->
        if (success) {
            viewModel.toolbarState.updateFocus(true)
        } else {
            activity?.quit()
        }
    } */

    KeyboardObserver(toolbarState = viewModel.toolbarState)

    HideOnScrollToolbar(
        toolbarState = viewModel.toolbarState,
        toolbar = { modifier ->
            Toolbar(
                onTextCommit = { text -> viewModel.commitSearch(text, currentUrl?.getQwantSERPCategory()) },
                modifier = modifier,
                toolbarState = viewModel.toolbarState,
                browserIcons = viewModel.browserIcons,
                beforeTextField = { QwantVIPAction(viewModel) },
                beforeTextFieldVisible = { !viewModel.toolbarState.hasFocus && currentUrl?.isNotBlank() == true && currentUrl?.isQwantUrl() == false && currentUrl != "about:blank" },
                afterTextField = { AfterActions(navigateTo, viewModel, appViewModel) },
                afterTextFieldVisible = { !viewModel.toolbarState.hasFocus },
                onQwantIconClicked = { viewModel.goToHomepage() }
            )
        },
        engineView = engineViewHolder,
        height = 56.dp,
        modifier = Modifier.fillMaxSize(),
        lock = { viewModel.showFindInPage }
    ) { modifier ->
        if (currentUrl != null) {
            if (currentUrl == "" && private) {
                HomePrivateBrowsing(modifier)
            } else {
                GlobalFeatures(appViewModel, viewModel)

                PullToRefreshBox(
                    onRefresh = { viewModel.reloadUrl() },
                    enabled = {
                        engineViewHolder?.canScrollVerticallyUp() == false &&
                        engineViewHolder?.getInputResultDetail()?.let {
                            it.canOverscrollTop() &&
                            it.canOverscrollLeft() &&
                            it.canOverscrollRight()
                        } == true
                    },
                    modifier = modifier
                ) {
                    val contentBlockerStatus = viewModel.contentBlockerState.getStatus()
                    if (contentBlockerStatus != ContentBlockerState.Status.ALLOWED) {
                        ContentBlockerOverlay(
                            contentBlockerStatus,
                            viewModel.contentBlockerState.getBlockReason(),
                            imageModifier = Modifier.width(300.dp),
                            imageScale = ContentScale.FillWidth,
                            alignment = Alignment.TopCenter
                        )
                    } else {
                        EngineView(
                            engine = viewModel.engine,
                            modifier = Modifier.fillMaxSize()
                        ) { engineView ->
                            engineViewHolder = engineView
                            EngineViewFeatures(engineView, viewModel)
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
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
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = false,
                    radius = 20.dp
                )
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


// TODO Clean up and separate code for Qwant VIP UI
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

    var badgeVisible by remember { mutableStateOf(false) }
    LaunchedEffect(viewModel.toolbarState.visible) {
        badgeVisible = if (viewModel.toolbarState.visible) {
            delay(200)
            true
        } else false
    }

    Box {
        ToolbarAction(onClick = { state?.browserAction?.onClick?.invoke() }) {
            BadgedBox(
                badge = {
                    if (counter?.isNotEmpty() == true && badgeVisible) {
                        Badge(
                            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentColor = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.outline,
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
    }
}

@Composable
fun AfterActions(
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel,
    appViewModel: QwantApplicationViewModel
) {
    val private = appViewModel.isPrivate.collectAsState()
    val privateBeforeClick = private.value
    Row {
        if (BuildConfig.FLAVOR_target == "canaltoys") {
            val canGoBack by viewModel.canGoBack.collectAsState()
            val canGoForward by viewModel.canGoForward.collectAsState()
            IconButton(
                onClick = { viewModel.goBack() },
                enabled = canGoBack,
                modifier = Modifier
                    .width(40.dp)
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.icons_arrow_backward), contentDescription = "back")
            }
            IconButton(
                onClick = { viewModel.goForward() },
                enabled = canGoForward,
                modifier = Modifier
                    .width(40.dp)
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                Icon(painter = painterResource(id = R.drawable.icons_arrow_forward), contentDescription = "forward")
            }
        }
        ZapButton(appViewModel, afterZap = { success ->
            if (success) {
                viewModel.openNewQwantTab(privateBeforeClick)
            }
        })
        TabsButton(navigateTo, viewModel)
        BrowserMenuButton(navigateTo, viewModel, appViewModel)
        if (BuildConfig.FLAVOR_target == "canaltoys") {
            ExitButton(appViewModel = appViewModel)
        }
    }
}

@Composable
fun ExitButton(
    appViewModel: QwantApplicationViewModel
) {
    val shouldZapOnQuit by appViewModel.zapOnQuit.collectAsState()
    val activity = LocalContext.current.activity

    ToolbarAction(onClick = {
        if (shouldZapOnQuit) {
            appViewModel.zap(skipConfirmation = true) { success ->
                if (success) {
                    activity?.quit()
                } else {
                    // TODO handle clear on quit fails
                }
            }
        } else {
            activity?.quit()
        }
    }) {
        Icon(
            painter = painterResource(id = R.drawable.icons_close),
            contentDescription = "Close app",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsButton(
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel
) {
    val tabCount by viewModel.tabCount.collectAsState()
    var showTabsDropdown by remember { mutableStateOf(false) }

    val private = LocalQwantTheme.current.private

    var badgeVisible by remember { mutableStateOf(false) }
    LaunchedEffect(private, viewModel.toolbarState.visible) {
        badgeVisible = if (private && viewModel.toolbarState.visible) {
            delay(200)
            true
        } else {
            false
        }
    }

    Box(
        modifier = Modifier
            .width(40.dp)
            .fillMaxHeight()
            .combinedClickable(
                onClick = {
                    viewModel.piwik.event("Tab", "Tap", "Open tray")
                    navigateTo(NavDestination.Tabs)
                },
                onLongClick = { showTabsDropdown = true },
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = false,
                    radius = 20.dp
                )
            )
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        BadgedBox(
            badge = {
                if (badgeVisible) {
                    Badge(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .size(18.dp)
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
            modifier = Modifier.size(40.dp)
        ) {
            TabCounter(tabCount)
        }

        Dropdown(
            expanded = showTabsDropdown,
            onDismissRequest = { showTabsDropdown = false },
            modifier = Modifier.defaultMinSize(minWidth = 240.dp)
        ) {
            DropdownItem(
                text = stringResource(id = R.string.browser_close_tab),
                icon = R.drawable.icons_close,
                onClick = {
                    viewModel.closeCurrentTab()
                    showTabsDropdown = false
                }
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            DropdownItem(
                text = stringResource(id = R.string.browser_new_tab),
                icon = R.drawable.icons_add_tab,
                onClick = {
                    viewModel.openNewQwantTab(false)
                    showTabsDropdown = false
                }
            )
            DropdownItem(
                text = stringResource(id = R.string.browser_new_tab_private),
                icon = R.drawable.icons_privacy_mask,
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

        ToolbarAction(onClick = {
            viewModel.piwik.event("Menu", "Tap", "Omnibar")
            showMenu = true
        }) {
            Icon(
                painter = painterResource(id = R.drawable.icons_more_vertical),
                contentDescription = "menu",
                modifier = Modifier.fillMaxSize()
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
