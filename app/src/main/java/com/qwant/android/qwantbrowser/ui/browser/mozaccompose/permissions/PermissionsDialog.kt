package com.qwant.android.qwantbrowser.ui.browser.mozaccompose.permissions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog
import mozilla.components.concept.engine.permission.Permission
import mozilla.components.concept.engine.permission.PermissionRequest
import mozilla.components.feature.sitepermissions.R
import mozilla.components.support.ktx.kotlin.stripDefaultPort
import java.security.InvalidParameterException
import mozilla.components.ui.icons.R as iconsR

data class PermissionDialogData(val permissionRequest: PermissionRequest, val host: String)
private data class PermissionsDialogDisplayData(@StringRes val title: Int, @DrawableRes val icon: Int)

@Composable
fun PermissionsDialog(
    data: PermissionDialogData,
    onClose: (allowed: Boolean, shouldStore: Boolean) -> Unit
) {
    val permission by remember(data) { derivedStateOf { data.permissionRequest.permissions.first() } }

    var shouldStore by remember { mutableStateOf(
        permission is Permission.ContentNotification
    ) }

    val displayData by remember(data) { derivedStateOf {
        if (data.permissionRequest.containsVideoAndAudioSources()) {
            PermissionsDialogDisplayData(
                R.string.mozac_feature_sitepermissions_camera_and_microphone,
                iconsR.drawable.mozac_ic_microphone
            )
        } else {
            when (permission) {
                is Permission.ContentGeoLocation -> PermissionsDialogDisplayData(
                    R.string.mozac_feature_sitepermissions_location_title,
                    iconsR.drawable.mozac_ic_location,
                )
                is Permission.ContentNotification -> PermissionsDialogDisplayData(
                    R.string.mozac_feature_sitepermissions_notification_title,
                    iconsR.drawable.mozac_ic_notification,
                )
                is Permission.ContentAudioCapture, is Permission.ContentAudioMicrophone -> PermissionsDialogDisplayData(
                    R.string.mozac_feature_sitepermissions_microfone_title,
                    iconsR.drawable.mozac_ic_microphone,
                )
                is Permission.ContentVideoCamera, is Permission.ContentVideoCapture -> PermissionsDialogDisplayData(
                    R.string.mozac_feature_sitepermissions_camera_title,
                    iconsR.drawable.mozac_ic_settings,
                )
                is Permission.ContentPersistentStorage -> PermissionsDialogDisplayData(
                    R.string.mozac_feature_sitepermissions_persistent_storage_title,
                    iconsR.drawable.mozac_ic_storage,
                )
                is Permission.ContentMediaKeySystemAccess -> PermissionsDialogDisplayData(
                    R.string.mozac_feature_sitepermissions_media_key_system_access_title,
                    iconsR.drawable.mozac_ic_link,
                )
                is Permission.ContentCrossOriginStorageAccess ->
                    TODO("ContentCrossOriginStorageAccess permission. Not implemented yet. Need more data than others")
                    /* Allow %1$s to use its cookies on %2$s? */
                else -> throw InvalidParameterException("$permission is not a valid permission.")
            }
        }
    }}

    YesNoDialog(
        onDismissRequest = { onClose(false, false) },
        onYes = { onClose(false, shouldStore) },
        onNo = { onClose(false, shouldStore) },
        description = stringResource(id = displayData.title, data.host.stripDefaultPort()),
        icon = displayData.icon,
        yesText = stringResource(id = R.string.mozac_feature_sitepermissions_allow),
        noText = stringResource(id = R.string.mozac_feature_sitepermissions_not_allow),
        additionalContent = {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 16.dp)) {
                Checkbox(checked = shouldStore, onCheckedChange = { shouldStore = !shouldStore })
                Text(text = stringResource(id = R.string.mozac_feature_sitepermissions_do_not_ask_again_on_this_site2))
            }
        }
    )
}