package com.qwant.android.qwantbrowser.ui.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

data class DropdownSelectorItem(
    val value: String,
    @StringRes val label: Int,
    val icon: ImageVector? = null
)

@Composable
fun DropdownSelector(
    items: List<DropdownSelectorItem>,
    selectedItem: DropdownSelectorItem,
    onItemSelected: (item: DropdownSelectorItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var dropDownWidth by remember { mutableStateOf(0) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(1.dp, Color.White, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp)
            .clickable { expanded = true }
            .onSizeChanged { dropDownWidth = it.width },
    ) {
        Text(stringResource(selectedItem.label), modifier = Modifier.weight(2f))
        Icon(Icons.Default.ArrowDropDown, "down")
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(LocalDensity.current) { dropDownWidth.toDp() })

        ) {
            items.forEach {
                DropdownMenuItem(
                    text = { Text(stringResource(it.label)) },
                    trailingIcon = {
                        if (it.value == selectedItem.value) {
                            Icon(Icons.Default.Check, "selected")
                        }
                    },
                    onClick = {
                        expanded = false
                        onItemSelected(it)
                    }
                )
            }
        }
    }
}
