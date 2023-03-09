package com.qwant.android.qwantbrowser.ui.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

data class DropdownSelectorItem<T>(
    val value: T,
    @StringRes val label: Int,
    val icon: Painter? = null
)

@Composable
fun <T> DropdownSelector(
    items: List<DropdownSelectorItem<T>>,
    selectedItem: DropdownSelectorItem<T>,
    onItemSelected: (item: DropdownSelectorItem<T>) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var dropDownWidth by remember { mutableStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(1.dp, LocalContentColor.current, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp)
            .defaultMinSize(minHeight = 36.dp)
            .clickable { expanded = true }
            .onSizeChanged { dropDownWidth = it.width },
    ) {
        val selectedLabel = stringResource(selectedItem.label)
        selectedItem.icon?.let { icon ->
            Image(icon, selectedLabel, modifier = Modifier.padding(end = 8.dp))
        }
        Text(selectedLabel, modifier = Modifier.weight(2f))
        Icon(Icons.Default.ArrowDropDown, "down")
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { dropDownWidth.toDp() })

        ) {
            items.forEach { item ->
                val label = stringResource(item.label)
                DropdownMenuItem(
                    text = { Text(label) },
                    leadingIcon = if (item.icon !== null) {
                        { Image(item.icon, label) }
                    } else null,
                    trailingIcon = {
                        if (item.value == selectedItem.value) {
                            Icon(Icons.Default.Check, "selected")
                        }
                    },
                    onClick = {
                        expanded = false
                        onItemSelected(item)
                    }
                )
            }
        }
    }
}
