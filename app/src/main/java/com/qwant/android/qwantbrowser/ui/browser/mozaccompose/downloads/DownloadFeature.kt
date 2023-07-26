package com.qwant.android.qwantbrowser.ui.browser.mozaccompose.downloads

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.activity
import com.qwant.android.qwantbrowser.ext.openAppSystemSettings
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.state.SessionState
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.feature.downloads.*
import mozilla.components.feature.downloads.manager.DownloadManager
import mozilla.components.feature.downloads.manager.onDownloadStopped
import mozilla.components.lib.state.ext.observeAsComposableState
import mozilla.components.support.ktx.kotlin.isSameOriginAs

// TODO Dialog translations

@Composable
fun DownloadFeature(
    store: BrowserStore,
    useCases: DownloadsUseCases,
    downloadManager: DownloadManager,
    onDownloadStopped: onDownloadStopped = { _, _, _ -> },
    showSnackbar: (String, QwantApplicationViewModel.SnackbarAction?) -> Unit
) {
    val context = LocalContext.current
    val tab by store.observeAsComposableState { state -> state.selectedTab }
    val downloadState = remember(tab) { tab?.content?.download }
    val url = remember(tab) { tab?.content?.url }

    var showDownloadRequest by remember { mutableStateOf(false) }
    var showAskPermissionAgain by remember { mutableStateOf(false) }
    var showPermissionRefused by remember { mutableStateOf(false) }
    var previousTab: SessionState? by remember { mutableStateOf(null) }

    val startDownload = {
        tab?.let { t ->
            downloadState?.let { d ->
                useCases.consumeDownload(t.id, d.id)
                if (downloadManager.download(d) == null) {
                    showSnackbar("Download not supported", null)
                }
            }
        }
    }

    val cancelDownload = {
        showDownloadRequest = false
        showAskPermissionAgain = false
        tab?.let { t ->
            downloadState?.let { d ->
                useCases.cancelDownloadRequest(t.id, d.id)
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        // TODO improve download notification permission flow
        if (result.all { it.key == POST_NOTIFICATIONS || it.value }) {
            startDownload()
        } else {
            context.activity?.let { activity ->
                if (downloadManager.permissions.any {
                    !ActivityCompat.shouldShowRequestPermissionRationale(activity, it)
                }) {
                    cancelDownload()
                    showPermissionRefused = true
                } else {
                    showAskPermissionAgain = true
                }
            }
        }
    }

    val checkPermissionAndStartDownload = {
        showDownloadRequest = false
        if (downloadManager.permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }) {
            startDownload()
        } else {
            permissionLauncher.launch(downloadManager.permissions)
        }
    }

    LaunchedEffect(onDownloadStopped) {
        downloadManager.onDownloadStopped = onDownloadStopped
    }

    // Cancel download and close popup if changing website
    // not sure why this is needed as when there is a dialog user should not be able to navigate.
    // However, kept it as Mozilla must have good reasons to do so.
    LaunchedEffect(url) {
        url?.let { newUrl ->
            previousTab?.let { oldTab ->
                if (!oldTab.content.url.isSameOriginAs(newUrl)) {
                    oldTab.content.download?.let { oldDownload ->
                        useCases.cancelDownloadRequest.invoke(oldTab.id, oldDownload.id)
                        showDownloadRequest = false
                        previousTab = null
                    }
                }
            }
        }
    }

    LaunchedEffect(downloadState) {
        if (downloadState != null) {
            previousTab = tab
            if (downloadState.skipConfirmation) {
                checkPermissionAndStartDownload()
            } else {
                showDownloadRequest = true
            }
        } else {
            showDownloadRequest = false
        }
    }

    if (showDownloadRequest) {
        YesNoDialog(
            onDismissRequest = { cancelDownload() },
            onYes = { checkPermissionAndStartDownload() },
            onNo = { cancelDownload() },
            title = "Download (${downloadState?.contentLength?.toMegabyteOrKilobyteString()})",
            description = "${downloadState?.fileName}",
            icon = R.drawable.icons_download,
            yesText = "Download",
            noText = "Cancel"
        )
    }

    if (showAskPermissionAgain) {
        YesNoDialog(
            onDismissRequest = { cancelDownload() },
            onYes = { checkPermissionAndStartDownload() },
            onNo = { cancelDownload() },
            description = "Downloader really needs this permissions: ${downloadManager.permissions.joinToString(", ")}",
            icon = R.drawable.icons_download,
            yesText = "Ask me again",
            noText = "Cancel"
        )
    }

    if (showPermissionRefused) {
        YesNoDialog(
            onDismissRequest = { showPermissionRefused = false },
            onYes = { context.openAppSystemSettings() },
            onNo = { showPermissionRefused = false },
            description = "You permanently refused permissions for the downloader. You need to enable them through system settings now",
            icon = R.drawable.icons_download,
            yesText = "Take me there",
            noText = "Cancel"
        )
    }
}