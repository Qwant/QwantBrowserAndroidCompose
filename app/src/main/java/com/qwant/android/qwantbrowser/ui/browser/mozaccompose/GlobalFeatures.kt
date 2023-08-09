package com.qwant.android.qwantbrowser.ui.browser.mozaccompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.legacy.ClFeature
import com.qwant.android.qwantbrowser.mozac.downloads.openDownloadedFile
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.browser.BrowserScreenViewModel
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.downloads.DownloadFeature
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.permissions.PermissionsFeature
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.PromptFeature
import mozilla.components.browser.state.state.content.DownloadState
import mozilla.components.feature.downloads.R as mozacR


@Composable
fun GlobalFeatures(
    appViewModel: QwantApplicationViewModel = hiltViewModel(),
    viewModel: BrowserScreenViewModel = hiltViewModel(),
) {
    FullScreenFeature(
        store = viewModel.store,
        toolbarState = viewModel.toolbarState,
        sessionUseCases = viewModel.sessionUseCases
    )

    WindowFeature(
        store = viewModel.store,
        tabsUseCases = viewModel.tabsUseCases
    )

    ContextMenuFeature(
        store = viewModel.store,
        tabsUseCases = viewModel.tabsUseCases,
        contextMenuUseCases = viewModel.contextMenuUseCases,
        showSnackbar = { message, action, dismiss, duration ->
            appViewModel.showSnackbar(message, action, dismiss, duration)
        }
    )

    PermissionsFeature(
        store = viewModel.store,
        storage = viewModel.permissionStorage
    )

    val context = LocalContext.current
    val completedDownloadText = stringResource(id = mozacR.string.mozac_feature_downloads_completed_notification_text2)
    val failedDownloadText = stringResource(id = mozacR.string.mozac_feature_downloads_failed_notification_text2)
    DownloadFeature(
        store = viewModel.store,
        useCases = viewModel.downloadUseCases,
        downloadManager = viewModel.downloadManager,
        onDownloadStopped = { state, s, status ->
            if (status == DownloadState.Status.COMPLETED) {
                appViewModel.showSnackbar(
                    completedDownloadText,
                    QwantApplicationViewModel.SnackbarAction("Open") {
                        context.openDownloadedFile(state.filePath, state.contentType)
                    }
                )
            } else if (status == DownloadState.Status.FAILED) {
                appViewModel.showSnackbar(failedDownloadText)
            }
        },
        showSnackbar = { message, action -> appViewModel.showSnackbar(message, action) }
    )

    PromptFeature(
        store = viewModel.store,
        exitFullscreenUseCase = viewModel.sessionUseCases.exitFullscreen
    )

    ClFeature()
}

