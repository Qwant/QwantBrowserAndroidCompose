package com.qwant.android.qwantbrowser.ui.preferences.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.ui.preferences.BasePreferenceScreen
import com.qwant.android.qwantbrowser.ui.preferences.PreferenceNavDestination
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceGroup
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceToggle
import com.qwant.android.qwantbrowser.ui.widgets.DropdownSelector
import com.qwant.android.qwantbrowser.ui.widgets.DropdownSelectorItem


@Composable
fun AppPreferencesScreen(
    appPreferencesViewModel: AppPreferencesViewModel = hiltViewModel()
) {
    val prefs by appPreferencesViewModel.preferencesState.collectAsState()

    BasePreferenceScreen(destination = PreferenceNavDestination.FrontendSearch) {
        PreferenceGroup(
            title = "Toolbar position",
            icon = Icons.Default.RestaurantMenu,
        ) {
            val availablePositions = listOf(
                DropdownSelectorItem(ToolbarPosition.TOP, R.string.available_toolbar_position_top),
                DropdownSelectorItem(ToolbarPosition.BOTTOM, R.string.available_toolbar_position_bottom)
            )
            val selectedPosition by remember(prefs.toolbarPosition) { derivedStateOf {
                availablePositions.find { it.value == prefs.toolbarPosition } ?: availablePositions[0]
            } }
            DropdownSelector(
                items = availablePositions,
                selectedItem = selectedPosition,
                onItemSelected = { appPreferencesViewModel.updateToolbarPosition(it.value) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        PreferenceGroup {
            PreferenceToggle(
                label = "Hide toolbar on scroll",
                icon = rememberVectorPainter(image = Icons.Default.KeyboardHide),
                value = prefs.hideToolbarOnScroll,
                onValueChange = { appPreferencesViewModel.updateHideToolbarOnScroll(it) }
            )
        }
    }
}