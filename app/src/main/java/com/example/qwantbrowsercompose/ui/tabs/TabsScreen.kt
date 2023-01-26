package com.example.qwantbrowsercompose.ui.tabs

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qwantbrowsercompose.PrivacyMode
import mozilla.components.browser.state.selector.selectedTab
import com.example.qwantbrowsercompose.R
import com.example.qwantbrowsercompose.tabs.TabsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsScreen(
    homepageUrl: String,
    onPrivacyChange: (privacyMode: PrivacyMode) -> Unit = {},
    onClose: () -> Unit = {},
    viewModel: TabsScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current // TODO remove this replacing toast with snackbar message

    var private by remember { mutableStateOf(viewModel.mozac.store.state.selectedTab?.content?.private ?: false) }

    DisposableEffect(private) {
        onPrivacyChange(if (private) PrivacyMode.PRIVATE else PrivacyMode.NORMAL)
        onDispose {
            onPrivacyChange(PrivacyMode.SELECTED_TAB_PRIVACY)
        }
    }


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
                    store = viewModel.mozac.store,
                    private = private,
                    onPrivateChange = { p -> private = p },
                    modifier = Modifier.align(Alignment.Center)
                )

                DeleteTabsButton(
                    private = private,
                    onDeleteConfirmed = {
                        if (private) {
                            viewModel.useCases.tabsUseCases.removePrivateTabs.invoke()
                            private = false
                        } else {
                            viewModel.useCases.tabsUseCases.removeNormalTabs.invoke()
                            viewModel.useCases.tabsUseCases.addTab.invoke(homepageUrl, selectTab = true)
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
        Box(modifier = Modifier.padding(padding)) {
            TabList(
                store = viewModel.mozac.store,
                private = private,
                // thumbnailStorage = thumbnailStorage,
                modifier = Modifier.fillMaxHeight(),
                onTabSelected = { tab ->
                    viewModel.useCases.tabsUseCases.selectTab.invoke(tab.id)
                    onClose()
                },
                onTabDeleted = { tab -> viewModel.useCases.tabsUseCases.removeTab.invoke(tab.id) }
            )

            Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
            ) {
                NewTabButton(
                    private = private,
                    onClick = {
                        viewModel.useCases.tabsUseCases.addTab.invoke(homepageUrl, selectTab = true, private = private)
                        onClose()
                    },
                    modifier = Modifier.height(36.dp)
                )
            }
        }
    }
}