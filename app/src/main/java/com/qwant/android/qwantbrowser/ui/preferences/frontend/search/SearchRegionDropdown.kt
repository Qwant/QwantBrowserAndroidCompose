package com.qwant.android.qwantbrowser.ui.preferences.frontend.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.widgets.DropdownSelector
import com.qwant.android.qwantbrowser.ui.widgets.DropdownSelectorItem

@Composable
fun SearchRegionDropdown(
    value: String,
    onValueChange: (String) -> Unit
) {
    val availableSearchRegions = listOf(
        DropdownSelectorItem("fr_FR", R.string.available_region_france, icon = painterResource(id = R.drawable.icons_flags_france)),
        DropdownSelectorItem("en_GB", R.string.available_region_great_britain, icon = painterResource(id = R.drawable.icons_flags_great_britain)),
        DropdownSelectorItem("de_DE", R.string.available_region_germany, icon = painterResource(id = R.drawable.icons_flags_germany)),
        DropdownSelectorItem("it_IT", R.string.available_region_italy, icon = painterResource(id = R.drawable.icons_flags_italia)),
        DropdownSelectorItem("es_AR", R.string.available_region_argentina, icon = painterResource(id = R.drawable.icons_flags_argentina)),
        DropdownSelectorItem("en_AU", R.string.available_region_australia, icon = painterResource(id = R.drawable.icons_flags_australia)),
        DropdownSelectorItem("en_US", R.string.available_region_usa, icon = painterResource(id = R.drawable.icons_flags_usa)),
        DropdownSelectorItem("es_ES", R.string.available_region_spain_es, icon = painterResource(id = R.drawable.icons_flags_spain)),
        DropdownSelectorItem("ca_ES", R.string.available_region_spain_ca, icon = painterResource(id = R.drawable.icons_flags_spain)),
        DropdownSelectorItem("cs_CZ", R.string.available_region_czech_republic, icon = painterResource(id = R.drawable.icons_flags_czech_republic)),
        DropdownSelectorItem("ro_RO", R.string.available_region_romania, icon = painterResource(id = R.drawable.icons_flags_romania)),
        DropdownSelectorItem("el_GR", R.string.available_region_greece, icon = painterResource(id = R.drawable.icons_flags_greece)),
        DropdownSelectorItem("zh_CN", R.string.available_region_china, icon = painterResource(id = R.drawable.icons_flags_china)),
        DropdownSelectorItem("zh_HK", R.string.available_region_hong_kong), // TODO missing "hong kong" flag icon
        DropdownSelectorItem("en_NZ", R.string.available_region_new_zealand, icon = painterResource(id = R.drawable.icons_flags_new_zealand)),
        DropdownSelectorItem("th_TH", R.string.available_region_thailand), // TODO missing "thailand" flag icon
        DropdownSelectorItem("ko_KR", R.string.available_region_south_korea, icon = painterResource(id = R.drawable.icons_flags_south_korea)),
        DropdownSelectorItem("sv_SE", R.string.available_region_sweden, icon = painterResource(id = R.drawable.icons_flags_sweden)),
        DropdownSelectorItem("nb_NO", R.string.available_region_norway, icon = painterResource(id = R.drawable.icons_flags_norway)),
        DropdownSelectorItem("da_DK", R.string.available_region_denmark, icon = painterResource(id = R.drawable.icons_flags_denmark)),
        DropdownSelectorItem("hu_HU", R.string.available_region_hungary, icon = painterResource(id = R.drawable.icons_flags_hungary)),
        DropdownSelectorItem("et_EE", R.string.available_region_estonia, icon = painterResource(id = R.drawable.icons_flags_estonia)),
        DropdownSelectorItem("es_MX", R.string.available_region_mexico),  // TODO missing "mexico" flag icon
        DropdownSelectorItem("es_CL", R.string.available_region_chile, icon = painterResource(id = R.drawable.icons_flags_chile)),
        DropdownSelectorItem("en_CA", R.string.available_region_canada_en, icon = painterResource(id = R.drawable.icons_flags_canada)),
        DropdownSelectorItem("fr_CA", R.string.available_region_canada_fr, icon = painterResource(id = R.drawable.icons_flags_canada)),
        DropdownSelectorItem("en_MY", R.string.available_region_malaysia, icon = painterResource(id = R.drawable.icons_flags_malaysia)),
        DropdownSelectorItem("bg_BG", R.string.available_region_bulgaria, icon = painterResource(id = R.drawable.icons_flags_bulgaria)),
        DropdownSelectorItem("fi_FI", R.string.available_region_finland, icon = painterResource(id = R.drawable.icons_flags_finland)),
        DropdownSelectorItem("pl_PL", R.string.available_region_poland, icon = painterResource(id = R.drawable.icons_flags_poland)),
        DropdownSelectorItem("nl_NL", R.string.available_region_netherlands, icon = painterResource(id = R.drawable.icons_flags_netherlands)),
        DropdownSelectorItem("pt_PT", R.string.available_region_portugal, icon = painterResource(id = R.drawable.icons_flags_portugal)),
        DropdownSelectorItem("de_CH", R.string.available_region_switzerland_de, icon = painterResource(id = R.drawable.icons_flags_switzerland)),
        DropdownSelectorItem("fr_CH", R.string.available_region_switzerland_fr, icon = painterResource(id = R.drawable.icons_flags_switzerland)),
        DropdownSelectorItem("it_CH", R.string.available_region_switzerland_it, icon = painterResource(id = R.drawable.icons_flags_switzerland)),
        DropdownSelectorItem("de_AT", R.string.available_region_austria, icon = painterResource(id = R.drawable.icons_flags_austria)),
        DropdownSelectorItem("fr_BE", R.string.available_region_belgium_fr, icon = painterResource(id = R.drawable.icons_flags_belgium)),
        DropdownSelectorItem("nl_BE", R.string.available_region_belgium_nl, icon = painterResource(id = R.drawable.icons_flags_belgium)),
        DropdownSelectorItem("en_IE", R.string.available_region_ireland, icon = painterResource(id = R.drawable.icons_flags_ireland)),
    )
    val selectedSearchRegion by remember(value) { derivedStateOf {
        availableSearchRegions.find { it.value == value } ?: availableSearchRegions[0]
    } }

    DropdownSelector(
        items = availableSearchRegions,
        selectedItem = selectedSearchRegion,
        onItemSelected = { onValueChange(it.value) },
        modifier = Modifier.fillMaxWidth()
    )
}