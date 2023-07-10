package com.qwant.android.qwantbrowser.ui.preferences

import androidx.compose.runtime.Composable
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.preferences.app.TabsViewOption
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceRadioButtonSelector
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceSelectionPopup
import com.qwant.android.qwantbrowser.ui.preferences.widgets.RadioButtonOption


val tabsViewOptions = mapOf(
    TabsViewOption.GRID to R.string.available_tabs_view_grid,
    TabsViewOption.LIST to R.string.available_tabs_view_list
)

@Composable
fun TabsViewPreference(
    value: TabsViewOption,
    onValueChange: (TabsViewOption) -> Unit
) {
    PreferenceSelectionPopup(
        label = R.string.tabs_view_label,
        description = tabsViewOptions[value],
        popupContent = { TabsViewPreferenceSelector(
            value = value,
            onValueChange = onValueChange
        )}
    )
}

@Composable
fun TabsViewPreferenceSelector(
    value: TabsViewOption,
    onValueChange: (TabsViewOption) -> Unit
) {
    PreferenceRadioButtonSelector(
        options = tabsViewOptions.map {
            RadioButtonOption(it.key, it.value)
        },
        value = value,
        onValueChange = onValueChange
    )
}