package com.qwant.android.qwantbrowser.ui.preferences

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.preferences.frontend.CustomPageCharacter
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceIconSelector
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceIconSelectorOption
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceSelectionPopup

@Composable
fun CustomPageCharacterPreference(
    value: CustomPageCharacter,
    onValueChange: (CustomPageCharacter) -> Unit
) {
    val options = remember { listOf(
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_random,
            value = CustomPageCharacter.RANDOM_CHARACTER,
            icon = R.drawable.icons_shuffle
        ),
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_doudoune,
            value = CustomPageCharacter.DOUDOUNE,
            icon = R.drawable.custompage_character_doudoune,
            tintIcon = false
        ),
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_football,
            value = CustomPageCharacter.FOOTBALL,
            icon = R.drawable.custompage_character_football,
            tintIcon = false
        ),
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_turtleneck,
            value = CustomPageCharacter.TURTLENECK,
            icon = R.drawable.custompage_character_turtleneck,
            tintIcon = false
        ),
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_glasses,
            value = CustomPageCharacter.GLASSES,
            icon = R.drawable.custompage_character_glasses,
            tintIcon = false
        ),
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_cat,
            value = CustomPageCharacter.CAT,
            icon = R.drawable.custompage_character_cat,
            tintIcon = false
        ),
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_balloon,
            value = CustomPageCharacter.BALLOON,
            icon = R.drawable.custompage_character_balloon,
            tintIcon = false
        ),
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_pull,
            value = CustomPageCharacter.PULL,
            icon = R.drawable.custompage_character_pull,
            tintIcon = false
        ),
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_hat,
            value = CustomPageCharacter.HAT,
            icon = R.drawable.custompage_character_hat,
            tintIcon = false
        ),
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_hands,
            value = CustomPageCharacter.HANDS,
            icon = R.drawable.custompage_character_hands,
            tintIcon = false
        ),
        PreferenceIconSelectorOption(
            label = R.string.available_custom_page_character_none,
            value = CustomPageCharacter.NO_CHARACTER,
            icon = R.drawable.icons_stop
        )
    )}

    val currentOption = remember(value) {
        options.find { it.value == value } ?: options.first()
    }

    PreferenceSelectionPopup(
        label = R.string.custom_page_character_label,
        description = currentOption.label,
        popupContent = {
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                PreferenceIconSelector(
                    options = options,
                    selectedValue = value,
                    onSelected = onValueChange,
                    shape = RoundedCornerShape(8.dp)
                )
            }
        },
        icon = {
            currentOption.icon?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = stringResource(id = currentOption.label),
                    colorFilter = if (currentOption.tintIcon) {
                        ColorFilter.tint(currentOption.iconColor ?: LocalContentColor.current)
                    } else null,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
    )
}