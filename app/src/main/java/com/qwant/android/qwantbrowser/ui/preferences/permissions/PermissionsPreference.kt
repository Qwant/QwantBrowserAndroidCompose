package com.qwant.android.qwantbrowser.ui.preferences.permissions

import androidx.compose.runtime.Composable
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.preferences.PreferencesViewModel
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceSelectionPopup

@Composable
fun PermissionsPreference(
    viewModel: PreferencesViewModel
) {
    PreferenceSelectionPopup(
        label = R.string.permissions_settings_label,
        popupContent = {
            PermissionsSettingsList(viewModel)
        },
        fullscreenPopup = true,
        disableScreenHeader = true
    )
}