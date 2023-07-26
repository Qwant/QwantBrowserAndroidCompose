package com.qwant.android.qwantbrowser.ui.preferences

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.openAppStorePage
import com.qwant.android.qwantbrowser.preferences.app.TabsViewOption
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.preferences.frontend.AdultFilter
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.ui.preferences.widgets.*
import com.qwant.android.qwantbrowser.ui.widgets.ScreenHeader
import org.webrtc.voiceengine.BuildInfo

@Composable
fun PreferencesScreen(
    onClose: () -> Unit,
    viewModel: PreferencesViewModel = hiltViewModel()
) {
    val appPrefs by viewModel.appPreferences.collectAsState()
    val frontEndPrefs by viewModel.frontEndPreferences.collectAsState()

    val scrollState = rememberScrollState()

    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(title = stringResource(id = R.string.settings), scrollableState = scrollState)

        Column(modifier = Modifier.verticalScroll(scrollState)) {
            // Make default browser
            DefaultBrowserPreference()

            PreferenceGroupLabel(label = R.string.settings_group_general)

            // Toolbar position
            PreferenceRadioSelectionPopup(
                label = R.string.toolbar_position_label,
                options = remember { mapOf(
                    ToolbarPosition.BOTTOM to R.string.available_toolbar_position_bottom,
                    ToolbarPosition.TOP to R.string.available_toolbar_position_top
                ) },
                value = appPrefs.toolbarPosition,
                onValueChange = { viewModel.updateToolbarPosition(it) }
            )
            // Hide toolbar on scroll ? // TODO confirm presence
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
            // Appearance
            PreferenceRadioSelectionPopup(
                label = R.string.appearance_label,
                options =  remember { mapOf(
                    Appearance.LIGHT to R.string.available_appearance_light,
                    Appearance.DARK to R.string.available_appearance_dark,
                    Appearance.SYSTEM_SETTINGS to R.string.available_appearance_system
                ) },
                value = frontEndPrefs.appearance,
                onValueChange = { viewModel.updateAppearance(it) }
            )
            // Custom page color popup
            CustomPageColorPreference(
                appearance = frontEndPrefs.appearance,
                value = frontEndPrefs.customPageColor,
                onValueChange = { viewModel.updateCustomPageColor(it) },
            )
            // Custom page character popup
            CustomPageCharacterPreference(
                value = frontEndPrefs.customPageCharacter,
                onValueChange = { viewModel.updateCustomPageCharacter(it) }
            )
            // News on home
            PreferenceToggle(
                label = R.string.show_news_label,
                value = frontEndPrefs.showNews,
                onValueChange = { viewModel.updateShowNews(it) }
            )
            // sponsor tiles
            PreferenceToggle(
                label = R.string.show_sponsor_label,
                value = frontEndPrefs.showSponsor,
                onValueChange = { viewModel.updateShowSponsor(it) }
            )
            // Filtre de contenu
            PreferenceRadioSelectionPopup(
                label = R.string.adult_filter_label,
                options =  remember { mapOf(
                    AdultFilter.NO_FILTER to R.string.available_adult_filter_none,
                    AdultFilter.MODERATE to R.string.available_adult_filter_moderate,
                    AdultFilter.STRICT to R.string.available_adult_filter_strict
                ) },
                value = frontEndPrefs.adultFilter,
                onValueChange = { viewModel.updateAdultFilter(it) }
            )
            // Display site icons
            PreferenceToggle(
                label = R.string.show_favicon_label,
                value = frontEndPrefs.showFavicons,
                onValueChange = { viewModel.updateShowFavicons(it) }
            )
            // Open search result in new tab
            PreferenceToggle(
                label = R.string.open_results_in_new_tab_label,
                value = frontEndPrefs.openResultsInNewTab,
                onValueChange = { viewModel.updateOpenResultsInNewTab(it) }
            )
            // Open links in app
            PreferenceToggle(
                label = R.string.open_links_in_app_label,
                value = appPrefs.openLinksInApp,
                onValueChange = { viewModel.updateOpenLinksInApp(it) }
            )

            PreferenceGroupLabel(label = R.string.settings_group_langue)

            // Interface language
            PreferenceRadioSelectionPopup(
                label = R.string.interface_language_label,
                options =  remember { mapOf(
                    "en" to R.string.available_language_english,
                    "fr" to R.string.available_language_french,
                    "de" to R.string.available_language_german,
                    "it" to R.string.available_language_italian,
                    "es" to R.string.available_language_spanish
                ) },
                value = frontEndPrefs.interfaceLanguage,
                onValueChange = { viewModel.updateInterfaceLanguage(it) }
            )

            // Search region
            PreferenceRadioSelectionPopup(
                label = R.string.search_region_label,
                options =  remember { mapOf(
                    "fr_FR" to R.string.available_region_france,
                    "en_GB" to R.string.available_region_great_britain,
                    "de_DE" to R.string.available_region_germany,
                    "it_IT" to R.string.available_region_italy,
                    "es_AR" to R.string.available_region_argentina,
                    "en_AU" to R.string.available_region_australia,
                    "en_US" to R.string.available_region_usa,
                    "es_ES" to R.string.available_region_spain_es,
                    "ca_ES" to R.string.available_region_spain_ca,
                    "cs_CZ" to R.string.available_region_czech_republic,
                    "ro_RO" to R.string.available_region_romania,
                    "el_GR" to R.string.available_region_greece,
                    "zh_CN" to R.string.available_region_china,
                    "zh_HK" to R.string.available_region_hong_kong,
                    "en_NZ" to R.string.available_region_new_zealand,
                    "th_TH" to R.string.available_region_thailand,
                    "ko_KR" to R.string.available_region_south_korea,
                    "sv_SE" to R.string.available_region_sweden,
                    "nb_NO" to R.string.available_region_norway,
                    "da_DK" to R.string.available_region_denmark,
                    "hu_HU" to R.string.available_region_hungary,
                    "et_EE" to R.string.available_region_estonia,
                    "es_MX" to R.string.available_region_mexico,
                    "es_CL" to R.string.available_region_chile,
                    "en_CA" to R.string.available_region_canada_en,
                    "fr_CA" to R.string.available_region_canada_fr,
                    "en_MY" to R.string.available_region_malaysia,
                    "bg_BG" to R.string.available_region_bulgaria,
                    "fi_FI" to R.string.available_region_finland,
                    "pl_PL" to R.string.available_region_poland,
                    "nl_NL" to R.string.available_region_netherlands,
                    "pt_PT" to R.string.available_region_portugal,
                    "de_CH" to R.string.available_region_switzerland_de,
                    "fr_CH" to R.string.available_region_switzerland_fr,
                    "it_CH" to R.string.available_region_switzerland_it,
                    "de_AT" to R.string.available_region_austria,
                    "fr_BE" to R.string.available_region_belgium_fr,
                    "nl_BE" to R.string.available_region_belgium_nl,
                    "en_IE" to R.string.available_region_ireland
                ) },
                value = frontEndPrefs.searchResultRegion,
                onValueChange = { viewModel.updateSearchResultRegion(it) }
            )

            PreferenceGroupLabel(label = R.string.settings_group_privacy)

            // Paramètres de suppression de navigation
            ClearDataPreference(viewModel)
            // Supprimer les données à la fermeture
            PreferenceToggle(
                label = R.string.clear_data_on_quit_label,
                value = appPrefs.clearDataOnQuit,
                onValueChange = { viewModel.updateClearDataOnQuit(it) }
            )

            PreferenceGroupLabel(label = R.string.settings_group_about)

            // App details
            AppDetailsPreference()
            // Politique de confidentialité
            val privacyPolicyUrl = stringResource(R.string.privacy_policy_url)
            PreferenceRow(
                label = R.string.privacy_policy_label,
                icon = { Icon(
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
                popupContent = {
                    Text("Licence ici") // TODO set true licence text
                },
                fullscreenPopup = true
            )
            // Rate us
            PreferenceRow(
                label = R.string.rate_us_label,
                icon = { Icon(
                    painter = painterResource(id = R.drawable.icons_open),
                    contentDescription = "Open"
                )},
                onClicked = { context.openAppStorePage() }
            )

            // Log.d("QB_BUILDTYPE", BuildInfo.getBuildType())
            // TODO test links only in debug builds
            // if (BuildInfo.getBuildType() == "debug") {
                PreferenceGroupLabel(label = R.string.settings_tests_label)

                // Tests prompts feature
                PreferenceRow(
                    label = R.string.settings_tests_prompts_label,
                    icon = { Icon(
                        painter = painterResource(id = R.drawable.icons_open),
                        contentDescription = "Open"
                    )},
                    onClicked = {
                        viewModel.openTestTabUseCase("prompts")
                        onClose()
                    }
                )
            // }
        }
    }
}