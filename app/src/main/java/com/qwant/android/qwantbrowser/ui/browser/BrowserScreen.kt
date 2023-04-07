package com.qwant.android.qwantbrowser.ui.browser

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.EngineView
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.SessionFeature
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.ThumbnailFeature
import com.qwant.android.qwantbrowser.ui.browser.suggest.Suggest
import com.qwant.android.qwantbrowser.ui.browser.toolbar.Toolbar
import com.qwant.android.qwantbrowser.ui.browser.toolbar.rememberThresholdNestedScrollConnection
import com.qwant.android.qwantbrowser.ui.nav.NavDestination
import com.qwant.android.qwantbrowser.ui.widgets.IconAction
import kotlin.math.roundToInt


@Composable
fun BrowserScreen(
    navigateTo: (NavDestination) -> Unit,
    viewModel: BrowserScreenViewModel = hiltViewModel()
) {
    val currentUrl by viewModel.currentUrl.collectAsState()
    val loadingProgress by viewModel.loadingProgress.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val canGoBack by viewModel.canGoBack.collectAsState()
    val tabCount by viewModel.tabCount.collectAsState()

    val focusManager = LocalFocusManager.current

    val toolbarHeightDp = 56.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeightDp.roundToPx() }

    var toolbarVisible by remember { mutableStateOf(true) }
    val toolbarOffset by animateFloatAsState(
        targetValue = if (toolbarVisible) {
            0f
        } else {
            // -toolbarHeightPx.toFloat()   // Top bar
            toolbarHeightPx.toFloat()       // Bottom bar
        },
        animationSpec = tween(durationMillis = 500)
    )

    val toolbarTrueHeight by remember(toolbarHeightPx, toolbarOffset) {
        derivedStateOf {
            // toolbarHeightPx + toolbarOffset.roundToInt()     // Top bar
            toolbarHeightPx - toolbarOffset.roundToInt()        // Bottom bar
        }
    }

    if (currentUrl != null) {
        Box(modifier = Modifier.nestedScroll(rememberThresholdNestedScrollConnection(
            onScroll = { sign -> toolbarVisible = (sign == 1f) },
            scrollThreshold = 1,        // Bottom bar (top bar use default)
            consecutiveThreshold = 1    // Bottom bar (top bar use default)
        ))) {
            EngineView(
                engine = viewModel.engine,
                modifier = Modifier
                    .fillMaxSize()
                    // .offset { IntOffset(x = 0, y = toolbarTrueHeight) },  // Top bar
            ) { engineView ->
                SessionFeature(
                    engineView = engineView,
                    store = viewModel.store,
                    canGoBack = canGoBack,
                    goBackUseCase = viewModel.goBack
                )
                ThumbnailFeature(engineView = engineView, store = viewModel.store)

                LaunchedEffect(engineView, toolbarTrueHeight) {
                    engineView.setDynamicToolbarMaxHeight(toolbarTrueHeight)
                }
            }

            Toolbar(
                url = currentUrl!!,
                onTextChanged = { text -> viewModel.updateSearchTerms(text) },
                onTextCommit = { text -> viewModel.commitSearch(text) },
                hasFocus = viewModel.toolbarFocus,
                onFocusChanged = { hasFocus -> viewModel.changeToolbarFocus(hasFocus) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(toolbarHeightDp)
                    .align(Alignment.BottomCenter)
                    .offset { IntOffset(x = 0, y = toolbarOffset.roundToInt()) },
                pageActions = {
                    if (loadingProgress == 1f) {
                        IconAction(
                            label = "Reload",
                            icon = Icons.Default.Autorenew
                        ) { viewModel.reloadUrl() }
                    } else {
                        IconAction(
                            label = "Cancel",
                            icon = Icons.Default.Cancel
                        ) { viewModel.stopLoading() }
                    }
                },
                browserActions = {
                    Row {
                        IconAction(
                            label = "Settings",
                            icon = Icons.Default.Radar
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
                    }
                }
            )

            ProgressBar(
                loadingProgress = loadingProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            )

            if (viewModel.toolbarFocus) {
                Suggest(
                    suggestions = suggestions,
                    onSuggestionClicked = { suggestion ->
                        viewModel.commitSuggestion(suggestion)
                        focusManager.clearFocus()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
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
