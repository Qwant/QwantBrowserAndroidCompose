@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.qwantbrowsercompose.ui.browser

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qwantbrowsercompose.browser.BrowserScreenViewModel
import com.example.qwantbrowsercompose.browser.toolbar.BrowserToolbar
import mozilla.components.browser.state.helper.Target

@Composable
fun BrowserScreen() {
   Scaffold(
       topBar = { BrowserHeader() }
   ) { innerPadding ->
        EngineViewWrapper(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
   }
}


@Composable
fun BrowserHeader(
    viewModel: BrowserScreenViewModel = hiltViewModel()
) {
    var editMode by remember { mutableStateOf(false) }
    var editText by remember { mutableStateOf("") }

    BrowserToolbar(
        store = viewModel.mozac.store,
        target = Target.SelectedTab,
        onDisplayMenuClicked = { },
        onTextEdit = { t -> editText = t },
        onTextCommit = { t ->
            editMode = false
            viewModel.useCases.sessionUseCases.loadUrl(t)
            // TODO do something more clever: URL check, search, ...
       },
        onDisplayToolbarClick = {
            editMode = true
        },
        hint = "hint",
        editMode = editMode,
        editText = editText
    )
}