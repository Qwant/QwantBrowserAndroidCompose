package com.qwant.android.qwantbrowser.ui.browser.mozaccompose

import android.util.Log
import android.view.View
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import com.qwant.android.qwantbrowser.ui.widgets.DropdownItem
import mozilla.components.browser.state.selector.findTabOrCustomTabOrSelectedTab
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.fetch.Client
import mozilla.components.feature.contextmenu.ContextMenuCandidate
import mozilla.components.feature.contextmenu.ContextMenuUseCases
import mozilla.components.feature.downloads.temporary.CopyDownloadFeature
import mozilla.components.feature.downloads.temporary.ShareDownloadFeature
import mozilla.components.feature.tabs.TabsUseCases
import mozilla.components.lib.state.ext.observeAsComposableState
import mozilla.components.ui.widgets.SnackbarDelegate

@Composable
fun ContextMenuFeature(
    store: BrowserStore,
    client: Client,
    tabsUseCases: TabsUseCases,
    contextMenuUseCases: ContextMenuUseCases,
    showSnackbar: (String, QwantApplicationViewModel.SnackbarAction?, Boolean, SnackbarDuration) -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current

    val shareDownloadFeature = remember {
        ShareDownloadFeature(
            context = context,
            store = store,
            httpClient = client,
            tabId = null,
        )
    }
    val copyDownloadFeature = remember {
        CopyDownloadFeature(
            context = context,
            store = store,
            httpClient = client,
            tabId = null,
            onCopyConfirmation = { showSnackbar("Copied", null, true, SnackbarDuration.Short) }
        )
    }
    ComposeFeatureWrapper(feature = shareDownloadFeature)
    ComposeFeatureWrapper(feature = copyDownloadFeature)

    val candidates = remember(context, view) {
        ContextMenuCandidate.defaultCandidates(
            context = context,
            tabsUseCases = tabsUseCases,
            contextMenuUseCases = contextMenuUseCases,
            snackBarParentView = view, // Useless with compose, but keeps compatibility with mozac
            snackbarDelegate = object : SnackbarDelegate {
                override fun show(
                    snackBarParentView: View,
                    text: Int,
                    duration: Int,
                    action: Int,
                    listener: ((v: View) -> Unit)?
                ) {
                    if (text != 0) {
                        showSnackbar(
                            context.getString(text),
                            if (action != 0) {
                                QwantApplicationViewModel.SnackbarAction(context.getString(action)) {
                                    listener?.invoke(view)
                                }
                            } else null,
                            false,
                            when (duration) {
                                SnackbarDuration.Short.ordinal -> SnackbarDuration.Short
                                SnackbarDuration.Long.ordinal -> SnackbarDuration.Long
                                SnackbarDuration.Indefinite.ordinal -> SnackbarDuration.Indefinite
                                else -> SnackbarDuration.Short
                            }
                        )
                    }
                }
            }
        )
    }
    val tab by store.observeAsComposableState { state -> state.findTabOrCustomTabOrSelectedTab() }
    val hitResult by store.observeAsComposableState { state -> state.selectedTab?.content?.hitResult }
    val validCandidates: List<ContextMenuCandidate> by remember(tab, hitResult) { mutableStateOf(
        tab?.let { session ->
            hitResult?.let { hit ->
                candidates.filter { candidate -> candidate.showFor(session, hit) }
            }
        } ?: listOf()
    )}

    tab?.let { session ->
        hitResult?.let { hit ->
            if (validCandidates.isNotEmpty()) {
                LocalHapticFeedback.current.performHapticFeedback(HapticFeedbackType.LongPress)
                Dropdown(
                    expanded = validCandidates.isNotEmpty(),
                    onDismissRequest = { contextMenuUseCases.consumeHitResult(session.id) },
                ) {
                    validCandidates.forEach { candidate ->
                        DropdownItem(text = candidate.label, onClick = {
                            candidate.action.invoke(session, hit)
                            contextMenuUseCases.consumeHitResult(session.id)
                        })
                    }
                }
            } else {
                contextMenuUseCases.consumeHitResult(session.id)
            }
        }
    }
}