package com.qwant.android.qwantbrowser.ui.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.BuildConfig
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.openAppStorePage
import com.qwant.android.qwantbrowser.preferences.app.Appearance
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.preferences.widgets.*
import com.qwant.android.qwantbrowser.ui.widgets.HtmlText
import com.qwant.android.qwantbrowser.ui.widgets.ScreenHeader

@Composable
fun PreferencesScreen(
    onClose: () -> Unit,
    viewModel: PreferencesViewModel = hiltViewModel(),
    applicationViewModel: QwantApplicationViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val appPrefs by viewModel.appPreferences.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        ScreenHeader(title = stringResource(id = R.string.settings), scrollableState = scrollState)

        Column(modifier = Modifier.verticalScroll(scrollState)) {
            // Make default browser
            DefaultBrowserPreference()

            PreferenceGroupLabel(label = R.string.settings_group_general)

            // Toolbar position
            PreferenceRadioSelectionPopup(
                label = R.string.toolbar_position_label,
                options = remember { listOf(
                    RadioButtonOption(ToolbarPosition.TOP, R.string.available_toolbar_position_top),
                    RadioButtonOption(ToolbarPosition.BOTTOM, R.string.available_toolbar_position_bottom)
                ) },
                value = appPrefs.toolbarPosition,
                onValueChange = { viewModel.updateToolbarPosition(it) }
            )
            // Hide toolbar on scroll
            PreferenceToggle(
                label = R.string.hide_toolbar_on_scroll_label,
                value = appPrefs.hideToolbarOnScroll,
                onValueChange = { viewModel.updateHideToolbarOnScroll(it) }
            )
            // Tabs view
            TabsViewPreference(
                value = appPrefs.tabsView,
                onValueChange = { viewModel.updateTabsView(it) }
            )
            // Appearance // TODO move appearance to app preferences
            PreferenceRadioSelectionPopup(
                label = R.string.appearance_label,
                options =  remember { listOf(
                    RadioButtonOption(Appearance.LIGHT, R.string.available_appearance_light),
                    RadioButtonOption(Appearance.DARK, R.string.available_appearance_dark),
                    RadioButtonOption(Appearance.SYSTEM_SETTINGS, R.string.available_appearance_system)
                ) },
                value = appPrefs.appearance,
                onValueChange = { viewModel.updateAppearance(it) }
            )
            // Open links in app
            PreferenceToggle(
                label = R.string.open_links_in_app_label,
                value = appPrefs.openLinksInApp,
                onValueChange = { viewModel.updateOpenLinksInApp(it) }
            )

            PreferenceGroupLabel(label = R.string.settings_group_privacy)

            // Paramètres de suppression de navigation
            ClearDataPreference(viewModel, applicationViewModel)
            // Supprimer les données à la fermeture
            PreferenceToggle(
                label = R.string.clear_data_on_quit_label,
                description = if (appPrefs.clearDataOnQuit) R.string.clear_data_on_quit_description else null,
                value = appPrefs.clearDataOnQuit,
                onValueChange = { viewModel.updateClearDataOnQuit(it) }
            )

            PreferenceToggle(
                label = R.string.piwik_optout_title,
                description = R.string.piwik_optout_description,
                value = !appPrefs.piwikOptout,
                onValueChange = { viewModel.updatePiwikOptout(!it) }
            )

            PreferenceGroupLabel(label = R.string.settings_group_about)

            // App details
            AppDetailsPreference()
            // Politique de confidentialité
            val privacyPolicyUrl = stringResource(R.string.privacy_policy_url)
            PreferenceRow(
                label = R.string.privacy_policy_label,
                trailing = { Icon(
                    painter = painterResource(id = R.drawable.icons_open),
                    contentDescription = "Open"
                )},
                onClicked = {
                    viewModel.addTabsUseCase(privacyPolicyUrl)
                    onClose()
                }
            )
            // Licence MPL 2.0
            PreferenceSelectionPopup(
                label = R.string.licence_label,
                popupContent = { HtmlText(
                    html = stringResource(id = R.string.settings_licence_content),
                    modifier = Modifier
                        .padding(16.dp)
                ) },
                fullscreenPopup = true
            )
            // Rate us
            PreferenceRow(
                label = R.string.rate_us_label,
                trailing = { Icon(
                    painter = painterResource(id = R.drawable.icons_open),
                    contentDescription = "Open"
                )},
                onClicked = { context.openAppStorePage() }
            )

            // TODO adjust test files by buildtype
            //   using dedicated src path etc ...
            if (BuildConfig.DEBUG) {
                PreferenceGroupLabel(label = R.string.settings_tests_label)

                // Tests prompts feature
                PreferenceRow(
                    label = R.string.settings_tests_prompts_label,
                    trailing = { Icon(
                        painter = painterResource(id = R.drawable.icons_open),
                        contentDescription = "Open"
                    )},
                    onClicked = {
                        viewModel.openTestTabUseCase("prompts")
                        onClose()
                    }
                )
            }
        }
    }
}