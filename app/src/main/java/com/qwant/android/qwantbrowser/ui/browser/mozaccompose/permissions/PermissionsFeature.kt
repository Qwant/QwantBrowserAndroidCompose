package com.qwant.android.qwantbrowser.ui.browser.mozaccompose.permissions

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.qwant.android.qwantbrowser.mozac.permissions.PermissionsFeature
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.ComposeFeatureWrapper
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.engine.permission.SitePermissionsStorage


@Composable
fun PermissionsFeature(
    store: BrowserStore,
    storage: SitePermissionsStorage
) {
    val context = LocalContext.current
    var feature: PermissionsFeature? = null
    var dialogData: PermissionDialogData? by remember { mutableStateOf(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        feature?.onPermissionsResult(
            result.keys.toTypedArray(),
            result.values.map { granted ->
                if (granted) PackageManager.PERMISSION_GRANTED
                else PackageManager.PERMISSION_DENIED
            }.toIntArray()
        )
    }

    feature = remember(context, store, storage) {
        PermissionsFeature(
            context = context,
            store = store,
            storage = storage,
            onPrompt = { permissionRequest, host ->
                dialogData = PermissionDialogData(permissionRequest, host)
            },
            onNeedToRequestPermissions = {
                launcher.launch(it)
            }
        )
    }

    ComposeFeatureWrapper(feature = feature)

    dialogData?.let { data ->
        PermissionsDialog(
            data = data,
            onClose = { allowed: Boolean, shouldStore: Boolean ->
                if (allowed) {
                    feature.onPositiveButtonPress(data.permissionRequest.id, shouldStore)
                } else {
                    feature.onNegativeButtonPress(data.permissionRequest.id, shouldStore)
                }
                dialogData = null
            }
        )
    }
}