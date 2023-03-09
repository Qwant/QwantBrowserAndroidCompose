package com.qwant.android.qwantbrowser.ui.preferences.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.theme.Grey600

data class PreferenceSelectorOption<T> (
    val label: String,
    val value: T,
    val icon: Painter? = null,
    val tintIcon: Boolean = true,
    val backgroundColor: Color? = null,
    val iconColor: Color? = null
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> PreferenceSelector(
    options: List<PreferenceSelectorOption<T>>,
    selectedValue: T,
    onSelected: (T) -> Unit,
    shape: Shape = RoundedCornerShape(50),
    showSelectedIcon: Boolean = false,
    enabled: Boolean = true
) {
    val localContentColor = LocalContentColor.current

    FlowRow(
        modifier = Modifier.selectableGroup(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        options.forEach { option ->
            if (enabled) {
                val isSelected = (option.value == selectedValue)
                val borderColor = when {
                    isSelected -> localContentColor
                    else -> Color.Transparent
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(shape = shape)
                        .border(2.dp, borderColor, shape)
                        .padding(4.dp)
                        .selectable(
                            selected = isSelected,
                            onClick = { onSelected(option.value) }
                        )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .defaultMinSize(40.dp, 40.dp)
                            .background(option.backgroundColor ?: Color.Transparent, shape)
                    ) {
                        option.icon?.let { icon ->
                            Image(
                                painter = icon,
                                contentDescription = option.label,
                                colorFilter = if (option.tintIcon) {
                                    ColorFilter.tint(option.iconColor ?: localContentColor)
                                } else null
                            )
                        }

                        if (isSelected && showSelectedIcon) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "selected",
                                tint = localContentColor,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .clip(shape = shape)
                        .defaultMinSize(48.dp, 48.dp)
                        .padding(4.dp)
                        .background(Grey600, shape) // TODO Set this color for light theme also if needed
                )
            }
        }
    }
}