package com.qwant.android.qwantbrowser.ui.preferences.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun PreferenceGroup(
    modifier: Modifier = Modifier,
    title: String? = null,
    icon: ImageVector? = null,
    description: String? = null,
    content: @Composable () -> Unit = {}
) {
    Column(modifier = modifier
        .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp))
        .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
        .padding(vertical = 4.dp)
        .fillMaxWidth()
    ) {
        if (title != null) {
            Row(modifier = Modifier.padding(8.dp)){
                if (icon != null) {
                    Icon(icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary)
                }

                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                    if (description != null) {
                        Text(text = description, style = MaterialTheme.typography.labelSmall, color = LocalContentColor.current.copy(0.6f))
                    }
                }
            }

            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
        }

        Column(modifier = Modifier.padding(8.dp)) {
            content()
        }
    }
}