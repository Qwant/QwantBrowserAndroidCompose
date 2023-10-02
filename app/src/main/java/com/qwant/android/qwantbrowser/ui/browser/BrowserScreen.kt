package com.qwant.android.qwantbrowser.ui.browser

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState
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
import androidx.preference.PreferenceManager
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.*
import com.qwant.android.qwantbrowser.legacy.onboarding.Onboarding
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.browser.home.HomePrivateBrowsing
import com.qwant.android.qwantbrowser.ui.browser.menu.BrowserMenu
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.*
import com.qwant.android.qwantbrowser.ui.browser.toolbar.*
import com.qwant.android.qwantbrowser.ui.nav.NavDestination
import com.qwant.android.qwantbrowser.ui.theme.LocalQwantTheme
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import com.qwant.android.qwantbrowser.ui.widgets.DropdownItem
import com.qwant.android.qwantbrowser.ui.widgets.TabCounter
import com.qwant.android.qwantbrowser.vip.VipSessionObserver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    /* // TODO Pull To Refresh
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

    KeyboardObserver(toolbarState = viewModel.toolbarState)

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
                GlobalFeatures(appViewModel, viewModel)

                // Box(modifier.pullRefresh(pullRefreshState, enabled = true)) {
                    EngineView(
                        engine = viewModel.engine,
                        modifier = modifier
                    ) { engineView ->
                        EngineViewFeatures(engineView, viewModel)
                    }

                //    PullRefreshIndicator(false, pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
                // }
            }
        } else {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.qwant_logo),
                    contentDescription = "logo qwant",
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    modifier = Modifier
                        .size(64.dp)
                )
            }
            /* Icon(
                painter = painterResource(id = R.drawable.qwant_logo),
                contentDescription = "logo qwant",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(64.dp)
            ) */
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
                indication = rememberRipple(
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
    }
}

@Composable
fun AfterActions(
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel,
    appViewModel: QwantApplicationViewModel
) {
    Row {
        ZapButton(appViewModel, afterZap = { success, tabsCleared ->
            if (tabsCleared || (appViewModel.isPrivate.value && success)) {
                viewModel.openNewQwantTab(appViewModel.isPrivate.value)
            }
        })
        TabsButton(navigateTo, viewModel)
        BrowserMenuButton(navigateTo, viewModel, appViewModel)
    }
}

@Composable
fun ZapButton(
    appViewModel: QwantApplicationViewModel,
    afterZap: ((success: Boolean, tabsCleared: Boolean) -> Unit)? = null
) {
    // TODO move from sharedprefs to internal datastore
    val context = LocalContext.current
    val prefkey = stringResource(id = R.string.pref_key_zap_highlight)
    val prefs: SharedPreferences = remember { PreferenceManager.getDefaultSharedPreferences(context) }
    var shouldHighlightZapPref by remember { mutableStateOf(prefs.getBoolean(prefkey, true)) }

    val listener = SharedPreferences.OnSharedPreferenceChangeListener { p, key ->
        key?.takeIf { it == prefkey }?.let {
            shouldHighlightZapPref = p.getBoolean(it, true)
        }
    }
    DisposableEffect(prefs) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
        onDispose {
            prefs.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }

    val zapDoneString = stringResource(id = R.string.cleardata_done)
    val onZapDone: (Boolean, Boolean) -> Unit = { success, tabsCleared ->
        if (success) appViewModel.showSnackbar(zapDoneString)
        afterZap?.invoke(success, tabsCleared)
    }
    if (shouldHighlightZapPref && appViewModel.hasHistory) {
        AnimatedZapButton(zap = {
            with (prefs.edit()) {
                putBoolean(prefkey, false)
                apply()
            }
            appViewModel.zap(then = onZapDone)
        })
    } else {
        StaticZapButton(zap = { appViewModel.zap(then = onZapDone) })
    }
}

@Composable
fun StaticZapButton(
    zap: () -> Unit
) {
    ToolbarAction(onClick = { zap() }) {
        Image(
            painter = painterResource(id = LocalQwantTheme.current.icons.zap),
            contentDescription = "zap",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun AnimatedZapButton(
    zap: () -> Unit
) {
    // Hacky way to employ AnimatedDrawable
    //  maybe there is something better to do

    var atEnd by remember { mutableStateOf(false) }
    var staticOverlayVisible by remember { mutableStateOf(true) }

    val qwantTheme = LocalQwantTheme.current
    val animatedImage = AnimatedImageVector.animatedVectorResource(id = qwantTheme.icons.zapAnimated)
    val animatedPainter = rememberAnimatedVectorPainter(animatedImage, atEnd)
    val staticPainter = painterResource(id = qwantTheme.icons.zap)

    var runCount = remember { 0 }

    suspend fun runAnimation() {
        delay(1000)
        while (true) {
            if (atEnd) {
                staticOverlayVisible = true
                delay(50)
                atEnd = false
                runCount = (runCount + 1) % 4
                if (runCount == 0) {
                    delay(4000)
                } else {
                    delay(animatedImage.totalDuration.toLong() + 50)
                }
            } else {
                staticOverlayVisible = false
                delay(50)
                atEnd = true
                delay(animatedImage.totalDuration.toLong() + 50)
            }
        }
    }

    LaunchedEffect(animatedImage) {
        runAnimation()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(40.dp)
            .fillMaxHeight()
            .clickable { zap() }
    ) {
        if (staticOverlayVisible) {
            Image(
                painter = staticPainter,
                contentDescription = "zap",
                modifier = Modifier.fillMaxSize().padding(8.dp)
            )
        } else {
            Image(
                painter = animatedPainter,
                contentDescription = "zap",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TabsButton(
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel
) {
    val tabCount by viewModel.tabCount.collectAsState()
    var showTabsDropdown by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(40.dp)
            .fillMaxHeight()
            .combinedClickable(
                onClick = { navigateTo(NavDestination.Tabs) },
                onLongClick = { showTabsDropdown = true },
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = 20.dp
                )
            )
            .padding(horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        BadgedBox(
            badge = {
                if (LocalQwantTheme.current.private) {
                    Badge(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .size(20.dp)
                            .offset(x = (-8).dp, y = 6.dp)
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

        ToolbarAction(onClick = { showMenu = true }) {
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
