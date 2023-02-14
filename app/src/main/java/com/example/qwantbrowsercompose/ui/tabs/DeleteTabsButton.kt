package com.example.qwantbrowsercompose.ui.tabs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DeleteTabsButton(
    private: Boolean,
    onDeleteConfirmed: () -> Unit,
    modifier: Modifier = Modifier
) {
    var dialogOpened by remember { mutableStateOf(false) }

    Icon(
        Icons.Default.DeleteForever,
        contentDescription = "privacy tabs",
        modifier = modifier.clickable { dialogOpened = true }
    )

    if (dialogOpened) {
        AlertDialog(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = { Text(if (private) "close private tabs" else "close tabs") },
            text = { Text("are you sure") },
            onDismissRequest = { dialogOpened = false },
            dismissButton = {
                TextButton(onClick = { dialogOpened = false }) {
                    Text("No")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    dialogOpened = false
                    onDeleteConfirmed()
                }) {
                    Text("Yes")
                }
            }
        )
    }
}