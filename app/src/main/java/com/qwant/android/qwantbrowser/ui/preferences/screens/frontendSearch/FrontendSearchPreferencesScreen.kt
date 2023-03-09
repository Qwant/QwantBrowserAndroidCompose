package com.qwant.android.qwantbrowser.ui.preferences.screens.frontendSearch

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.ui.preferences.viewmodels.FrontEndPreferencesViewModel
import com.qwant.android.qwantbrowser.ui.preferences.PreferenceNavDestination
import com.qwant.android.qwantbrowser.ui.preferences.screens.BasePreferenceScreen
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceGroup
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceToggle


@Composable
fun FrontendSearchPreferencesScreen(
    frontEndPreferencesViewModel: FrontEndPreferencesViewModel = hiltViewModel()
) {
    val prefs by frontEndPreferencesViewModel.preferencesState.collectAsState()

    BasePreferenceScreen(destination = PreferenceNavDestination.FrontendSearch) {
        PreferenceGroup(
            title = "Region",
            icon = Icons.Default.Flag,
        ) {
            SearchRegionDropdown(
                value = prefs.searchResultRegion,
                onValueChange = { frontEndPreferencesViewModel.updateSearchResultRegion(it) }
            )
        }

        PreferenceGroup(
            title = "Adult filter",
            icon = Icons.Default.FilterAlt,
        ) {
            AdultFilterDropdown(
                value = prefs.adultFilter,
                onValueChange = { frontEndPreferencesViewModel.updateAdultFilter(it) }
            )
        }

        PreferenceGroup {
            PreferenceToggle(
                label = "Display site icons",
                icon = rememberVectorPainter(image = Icons.Default.Wallpaper),
                value = prefs.showFavicons,
                onValueChange = { frontEndPreferencesViewModel.updateShowFavicons(it) }
            )
            PreferenceToggle(
                label = "Open outgoing links in a new tab",
                icon = rememberVectorPainter(image = Icons.Default.AddCircleOutline),
                value = prefs.openResultsInNewTab,
                onValueChange = { frontEndPreferencesViewModel.updateOpenResultsInNewTab(it) }
            )
            PreferenceToggle(
                label = "Always play videos on Qwant.com",
                icon = rememberVectorPainter(image = Icons.Default.PlayCircleOutline),
                value = prefs.videosOnQwant,
                onValueChange = { frontEndPreferencesViewModel.updateVideosOnQwant(it) }
            )
        }
    }
}