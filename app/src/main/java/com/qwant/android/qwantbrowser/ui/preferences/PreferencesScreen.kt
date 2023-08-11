package com.qwant.android.qwantbrowser.ui.preferences

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.BuildConfig
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.openAppStorePage
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.preferences.frontend.AdultFilter
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.preferences.widgets.*
import com.qwant.android.qwantbrowser.ui.widgets.HtmlText
import com.qwant.android.qwantbrowser.ui.widgets.ScreenHeader
import java.util.Locale

@Composable
fun PreferencesScreen(
    onClose: () -> Unit,
    viewModel: PreferencesViewModel = hiltViewModel(),
    applicationViewModel: QwantApplicationViewModel = hiltViewModel()
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
                options = remember { listOf(
                    RadioButtonOption(ToolbarPosition.BOTTOM, R.string.available_toolbar_position_bottom),
                    RadioButtonOption(ToolbarPosition.TOP, R.string.available_toolbar_position_top)
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
            // Appearance
            PreferenceRadioSelectionPopup(
                label = R.string.appearance_label,
                options =  remember { listOf(
                    RadioButtonOption(Appearance.LIGHT, R.string.available_appearance_light),
                    RadioButtonOption(Appearance.DARK, R.string.available_appearance_dark),
                    RadioButtonOption(Appearance.SYSTEM_SETTINGS, R.string.available_appearance_system)
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
                options =  remember { listOf(
                    RadioButtonOption(AdultFilter.NO_FILTER, R.string.available_adult_filter_none, icon = { Text(text = "\uD83D\uDC40")}),
                    RadioButtonOption(AdultFilter.MODERATE, R.string.available_adult_filter_moderate, icon = { Text(text = "\uD83D\uDE07")}),
                    RadioButtonOption(AdultFilter.STRICT, R.string.available_adult_filter_strict, icon = { Text(text = "\uD83D\uDE48")})
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
                options =  remember { listOf(
                    RadioButtonOption("en", R.string.available_language_english, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_great_britain), contentDescription = "flag")
                    }),
                    RadioButtonOption("fr", R.string.available_language_french, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_france), contentDescription = "flag")
                    }),
                    RadioButtonOption("de", R.string.available_language_german, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_germany), contentDescription = "flag")
                    }),
                    RadioButtonOption("it", R.string.available_language_italian, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_italia), contentDescription = "flag")
                    }),
                    RadioButtonOption("es", R.string.available_language_spanish, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_spain), contentDescription = "flag")
                    })
                ) },
                value = AppCompatDelegate.getApplicationLocales()[0]?.language ?: Locale.getDefault().language,
                onValueChange = { AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.create(Locale(it))
                ) }
            )

            // Search region
            PreferenceRadioSelectionPopup(
                label = R.string.search_region_label,
                options =  remember { listOf(
                    RadioButtonOption("fr_FR", R.string.available_region_france, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_france), contentDescription = "flag")
                    }),
                    RadioButtonOption("en_GB", R.string.available_region_great_britain, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_great_britain), contentDescription = "flag")
                    }),
                    RadioButtonOption("de_DE", R.string.available_region_germany, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_germany), contentDescription = "flag")
                    }),
                    RadioButtonOption("it_IT", R.string.available_region_italy, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_italia), contentDescription = "flag")
                    }),
                    RadioButtonOption("es_ES", R.string.available_region_spain_es, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_spain), contentDescription = "flag")
                    }),
                    RadioButtonOption("ca_ES", R.string.available_region_spain_ca, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_spain), contentDescription = "flag")
                    }),
                    RadioButtonOption("es_AR", R.string.available_region_argentina, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_argentina), contentDescription = "flag")
                    }),
                    RadioButtonOption("en_AU", R.string.available_region_australia, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_australia), contentDescription = "flag")
                    }),
                    RadioButtonOption("en_US", R.string.available_region_usa, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_usa), contentDescription = "flag")
                    }),
                    RadioButtonOption("cs_CZ", R.string.available_region_czech_republic, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_czech_republic), contentDescription = "flag")
                    }),
                    RadioButtonOption("ro_RO", R.string.available_region_romania, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_romania), contentDescription = "flag")
                    }),
                    RadioButtonOption("el_GR", R.string.available_region_greece, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_greece), contentDescription = "flag")
                    }),
                    RadioButtonOption("zh_CN", R.string.available_region_china, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_china), contentDescription = "flag")
                    }),
                    RadioButtonOption("zh_HK", R.string.available_region_hong_kong, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_hong_kong), contentDescription = "flag")
                    }),
                    RadioButtonOption("en_NZ", R.string.available_region_new_zealand, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_new_zealand), contentDescription = "flag")
                    }),
                    RadioButtonOption("th_TH", R.string.available_region_thailand, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_thailand), contentDescription = "flag")
                    }),
                    RadioButtonOption("ko_KR", R.string.available_region_south_korea, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_south_korea), contentDescription = "flag")
                    }),
                    RadioButtonOption("sv_SE", R.string.available_region_sweden, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_sweden), contentDescription = "flag")
                    }),
                    RadioButtonOption("nb_NO", R.string.available_region_norway, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_norway), contentDescription = "flag")
                    }),
                    RadioButtonOption("da_DK", R.string.available_region_denmark, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_denmark), contentDescription = "flag")
                    }),
                    RadioButtonOption("hu_HU", R.string.available_region_hungary, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_hungary), contentDescription = "flag")
                    }),
                    RadioButtonOption("et_EE", R.string.available_region_estonia, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_estonia), contentDescription = "flag")
                    }),
                    RadioButtonOption("es_MX", R.string.available_region_mexico, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_mexico), contentDescription = "flag")
                    }),
                    RadioButtonOption("es_CL", R.string.available_region_chile, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_chile), contentDescription = "flag")
                    }),
                    RadioButtonOption("en_CA", R.string.available_region_canada_en, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_canada), contentDescription = "flag")
                    }),
                    RadioButtonOption("fr_CA", R.string.available_region_canada_fr, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_canada), contentDescription = "flag")
                    }),
                    RadioButtonOption("en_MY", R.string.available_region_malaysia, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_malaysia), contentDescription = "flag")
                    }),
                    RadioButtonOption("bg_BG", R.string.available_region_bulgaria, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_bulgaria), contentDescription = "flag")
                    }),
                    RadioButtonOption("fi_FI", R.string.available_region_finland, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_finland), contentDescription = "flag")
                    }),
                    RadioButtonOption("pl_PL", R.string.available_region_poland, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_poland), contentDescription = "flag")
                    }),
                    RadioButtonOption("nl_NL", R.string.available_region_netherlands, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_netherlands), contentDescription = "flag")
                    }),
                    RadioButtonOption("pt_PT", R.string.available_region_portugal, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_portugal), contentDescription = "flag")
                    }),
                    RadioButtonOption("de_CH", R.string.available_region_switzerland_de, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_switzerland), contentDescription = "flag")
                    }),
                    RadioButtonOption("fr_CH", R.string.available_region_switzerland_fr, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_switzerland), contentDescription = "flag")
                    }),
                    RadioButtonOption("it_CH", R.string.available_region_switzerland_it, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_switzerland), contentDescription = "flag")
                    }),
                    RadioButtonOption("de_AT", R.string.available_region_austria, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_austria), contentDescription = "flag")
                    }),
                    RadioButtonOption("fr_BE", R.string.available_region_belgium_fr, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_belgium), contentDescription = "flag")
                    }),
                    RadioButtonOption("nl_BE", R.string.available_region_belgium_nl, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_belgium), contentDescription = "flag")
                    }),
                    RadioButtonOption("en_IE", R.string.available_region_ireland, icon = {
                        Image(painter = painterResource(R.drawable.icons_flags_ireland), contentDescription = "flag")
                    })
                ) },
                value = frontEndPrefs.searchResultRegion,
                onValueChange = { viewModel.updateSearchResultRegion(it) }
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
            // if (BuildConfig.DEBUG) {
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
            // }
        }
    }
}