package com.example.qwantbrowsercompose.ui.tabs

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qwantbrowsercompose.ui.PrivacyMode
import com.example.qwantbrowsercompose.ui.QwantApplicationViewModel
import com.example.qwantbrowsercompose.ui.utils.BigButton
import mozilla.components.browser.state.state.SessionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsScreen(
    onClose: () -> Unit = {},
    appViewModel: QwantApplicationViewModel = hiltViewModel(),
    tabsViewModel: TabsScreenViewModel = hiltViewModel()
) {
    val private by appViewModel.isPrivate.collectAsState()
    val tabs by tabsViewModel.tabs.collectAsState()
    val selectedTabId by tabsViewModel.selectedTabId.collectAsState()

    val normalTabs by remember(tabs) { derivedStateOf { tabs.filter { !it.content.private } } }
    val privateTabs by remember(tabs) { derivedStateOf { tabs.filter { it.content.private } } }
    val normalTabsCount by remember(normalTabs) { derivedStateOf { normalTabs.count() } }

    DisposableEffect(true) {
        onDispose {
            appViewModel.setPrivacyMode(PrivacyMode.SELECTED_TAB_PRIVACY)
        }
    }

    // TODO remove this replacing toast with snackbar message
    val context = LocalContext.current
    val deleteSuccessToast = if (private) "private delete OK" else "delete OK"

    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
            ) {
                Icon( /* Back Button */
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .size(48.dp)
                        .padding(12.dp)
                        .clickable { onClose() }
                )

                TabPrivacySwitch(
                    tabCount = normalTabsCount,
                    private = private,
                    onPrivateChange = { p ->
                        appViewModel.setPrivacyMode(when (p) {
                            true -> PrivacyMode.PRIVATE
                            false -> PrivacyMode.NORMAL
                        })
                    },
                    modifier = Modifier.align(Alignment.Center)
                )

                DeleteTabsButton(
                    private = private,
                    onDeleteConfirmed = {
                        tabsViewModel.removeTabs(private)
                        if (private) {
                            appViewModel.setPrivacyMode(PrivacyMode.NORMAL)
                        } else {
                            onClose()
                        }
                        // TODO replace toast with snackbar message
                        Toast.makeText(context, deleteSuccessToast, Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(48.dp)
                        .padding(12.dp)
                )
            }
        }
    ) { padding ->
        val onTabSelected = { tab: SessionState ->
            tabsViewModel.selectTab(tab.id)
            onClose()
        }
        val onTabDeleted = { tab: SessionState -> tabsViewModel.removeTab(tab.id) }

        Box(modifier = Modifier.padding(padding)) {
            AnimatedVisibility(
                visible = private,
                enter = slideInHorizontally(initialOffsetX = { it }),
                exit = slideOutHorizontally(targetOffsetX = { it })
            ) {
                TabList(
                    list = privateTabs,
                    selectedTabId = selectedTabId,
                    modifier = Modifier.fillMaxHeight(),
                    onTabSelected = onTabSelected,
                    onTabDeleted = onTabDeleted
                )
            }

            AnimatedVisibility(
                visible = !private,
                enter = slideInHorizontally(initialOffsetX = { -it }),
                exit = slideOutHorizontally(targetOffsetX = { -it })
            ) {
                TabList(
                    list = normalTabs,
                    selectedTabId = selectedTabId,
                    modifier = Modifier.fillMaxHeight(),
                    onTabSelected = onTabSelected,
                    onTabDeleted = onTabDeleted
                )
            }

            Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
            ) {
                BigButton(
                    text = if (private) "add private tab" else "add tab",
                    icon = Icons.Outlined.AddCircle,
                    onClick = {
                        tabsViewModel.openNewQwantTab(private = private)
                        onClose()
                    },
                    modifier = Modifier.height(36.dp)
                )
            }
        }
    }
}