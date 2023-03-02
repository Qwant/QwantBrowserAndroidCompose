package com.qwant.android.qwantbrowser.ui.browser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.EngineView
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.SessionFeature
import com.qwant.android.qwantbrowser.ui.browser.suggest.Suggest
import com.qwant.android.qwantbrowser.ui.browser.toolbar.Toolbar
import com.qwant.android.qwantbrowser.ui.widgets.IconAction


@Composable
fun BrowserScreen(
    viewModel: BrowserScreenViewModel = hiltViewModel()
) {
    val currentUrl by viewModel.currentUrl.collectAsState()
    val loadingProgress by viewModel.loadingProgress.collectAsState()
    val suggestions by viewModel.suggestions.collectAsState()
    val canGoBack by viewModel.canGoBack.collectAsState()

    val focusManager = LocalFocusManager.current

    if (currentUrl != null) {
        Column {
            Toolbar(
                url = currentUrl!!,
                onTextChanged = { text -> viewModel.updateSearchTerms(text) },
                onTextCommit = { text -> viewModel.commitSearch(text) },
                hasFocus = viewModel.toolbarFocus,
                onFocusChanged = { hasFocus -> viewModel.changeToolbarFocus(hasFocus) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
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
                    IconAction(
                        label = "Qwant VIP",
                        icon = Icons.Default.Radar
                    ) { /* TODO open webextension */ }
                }
            )

            Box(modifier = Modifier.weight(2f)) {
                EngineView(
                    engine = viewModel.engine,
                    modifier = Modifier.fillMaxSize()
                ) { engineView ->
                    SessionFeature(
                        engineView = engineView,
                        store = viewModel.store,
                        canGoBack = canGoBack,
                        goBackUseCase = viewModel.goBack
                    )
                }

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
