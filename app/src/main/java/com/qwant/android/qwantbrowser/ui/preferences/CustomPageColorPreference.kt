package com.qwant.android.qwantbrowser.ui.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.preferences.frontend.CustomPageColor
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceRow
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceSelectionPopup
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceIconSelector
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceIconSelectorOption
import com.qwant.android.qwantbrowser.ui.theme.ActionBlue300
import com.qwant.android.qwantbrowser.ui.theme.Green400
import com.qwant.android.qwantbrowser.ui.theme.Purple200
import com.qwant.android.qwantbrowser.ui.theme.Red100

@Composable
fun CustomPageColorPreference(
    appearance: Appearance,
    value: CustomPageColor,
    onValueChange: (CustomPageColor) -> Unit
) {
    val enabled = (appearance == Appearance.LIGHT)
        || (appearance == Appearance.SYSTEM_SETTINGS && !isSystemInDarkTheme())

    if (enabled) {
        val options = remember { listOf(
            PreferenceIconSelectorOption(R.string.available_custom_page_color_blue, CustomPageColor.BLUE, backgroundColor = ActionBlue300),
            PreferenceIconSelectorOption(R.string.available_custom_page_color_green, CustomPageColor.GREEN, backgroundColor = Green400),
            PreferenceIconSelectorOption(R.string.available_custom_page_color_pink, CustomPageColor.PINK, backgroundColor = Red100),
            PreferenceIconSelectorOption(R.string.available_custom_page_color_purple, CustomPageColor.PURPLE, backgroundColor = Purple200)
        ) }

        val currentOption = remember(value) {
            options.find { it.value == value } ?: options.first()
        }

        val outlineColor = remember { Color(0x1e050506) }

        PreferenceSelectionPopup(
            label = R.string.custom_page_color_label,
            description = currentOption.label,
            popupContent = {
                PreferenceIconSelector(
                    options = options,
                    selectedValue = value,
                    onSelected = onValueChange,
                    showSelectedIcon = true
                )
            },
            icon = {
                Box(modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(currentOption.backgroundColor ?: Color.Transparent)
                    .border(1.dp, outlineColor, CircleShape)
                )
            }
        )
    } else {
        PreferenceRow(
            label = R.string.custom_page_color_label,
            description = stringResource(R.string.custom_page_color_disabled_description)
        )
    }
}