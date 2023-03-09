package com.qwant.android.qwantbrowser.ui.preferences.screens.frontendInterface

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.preferences.frontend.CustomPageCharacter
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceSelector
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceSelectorOption

@Composable
fun CustomPageCharacterSelector(
    value: CustomPageCharacter,
    onValueChange: (CustomPageCharacter) -> Unit
) {
    PreferenceSelector(
        options = listOf(
            PreferenceSelectorOption(
                label = "Random",
                value = CustomPageCharacter.RANDOM_CHARACTER,
                icon = rememberVectorPainter(Icons.Default.DoubleArrow)
            ),
            PreferenceSelectorOption(
                label = "Doudoune",
                value = CustomPageCharacter.DOUDOUNE,
                icon = painterResource(R.drawable.custompage_character_doudoune),
                tintIcon = false
            ),
            PreferenceSelectorOption(
                label = "Football",
                value = CustomPageCharacter.FOOTBALL,
                icon = painterResource(R.drawable.custompage_character_football),
                tintIcon = false
            ),
            PreferenceSelectorOption(
                label = "Turtleneck",
                value = CustomPageCharacter.TURTLENECK,
                icon = painterResource(R.drawable.custompage_character_turtleneck),
                tintIcon = false
            ),
            PreferenceSelectorOption(
                label = "Glasses",
                value = CustomPageCharacter.GLASSES,
                icon = painterResource(R.drawable.custompage_character_glasses),
                tintIcon = false
            ),
            PreferenceSelectorOption(
                label = "Cat",
                value = CustomPageCharacter.CAT,
                icon = painterResource(R.drawable.custompage_character_cat),
                tintIcon = false
            ),
            PreferenceSelectorOption(
                label = "Balloon",
                value = CustomPageCharacter.BALLOON,
                icon = painterResource(R.drawable.custompage_character_balloon),
                tintIcon = false
            ),
            PreferenceSelectorOption(
                label = "None",
                value = CustomPageCharacter.NO_CHARACTER,
                icon = rememberVectorPainter(Icons.Default.Block)
            )
        ),
        selectedValue = value,
        onSelected = onValueChange,
        shape = RoundedCornerShape(12.dp)
    )
}