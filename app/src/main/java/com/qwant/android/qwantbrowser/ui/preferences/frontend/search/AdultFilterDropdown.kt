package com.qwant.android.qwantbrowser.ui.preferences.frontend.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.preferences.frontend.AdultFilter
import com.qwant.android.qwantbrowser.ui.widgets.DropdownSelector
import com.qwant.android.qwantbrowser.ui.widgets.DropdownSelectorItem

@Composable
fun AdultFilterDropdown(
    value: AdultFilter,
    onValueChange: (AdultFilter) -> Unit
) {
    val availableFilters = listOf(
        DropdownSelectorItem(AdultFilter.NO_FILTER, R.string.adult_filter_none),
        DropdownSelectorItem(AdultFilter.MODERATE, R.string.adult_filter_moderate),
        DropdownSelectorItem(AdultFilter.STRICT, R.string.adult_filter_strict)
    )
    val selectedFilter by remember(value) { derivedStateOf {
        availableFilters.find { it.value == value } ?: availableFilters[0]
    } }

    DropdownSelector(
        items = availableFilters,
        selectedItem = selectedFilter,
        onItemSelected = { onValueChange(it.value) },
        modifier = Modifier.fillMaxWidth()
    )
}