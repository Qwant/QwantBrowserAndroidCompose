package com.qwant.android.qwantbrowser.ui.preferences.frontend.ux

import androidx.compose.runtime.Composable
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.preferences.frontend.CustomPageColor
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceSelector
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceSelectorOption
import com.qwant.android.qwantbrowser.ui.theme.ActionBlue300
import com.qwant.android.qwantbrowser.ui.theme.Green400
import com.qwant.android.qwantbrowser.ui.theme.Purple200
import com.qwant.android.qwantbrowser.ui.theme.Red100

@Composable
fun CustomPageColorSelector(
    value: CustomPageColor,
    onValueChange: (CustomPageColor) -> Unit,
    enabled: Boolean = true
) {
    PreferenceSelector(
        options = listOf(
            PreferenceSelectorOption("Blue", CustomPageColor.BLUE, backgroundColor = ActionBlue300),
            PreferenceSelectorOption("Green", CustomPageColor.GREEN, backgroundColor = Green400),
            PreferenceSelectorOption("Pink", CustomPageColor.PINK, backgroundColor = Red100),
            PreferenceSelectorOption("Purple", CustomPageColor.PURPLE, backgroundColor = Purple200)
        ),
        selectedValue = value,
        onSelected = onValueChange,
        showSelectedIcon = true,
        enabled = enabled
    )
}