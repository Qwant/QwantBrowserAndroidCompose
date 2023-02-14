package com.example.qwantbrowsercompose.ui.nav

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.qwantbrowsercompose.R


@Composable
fun MainMenuNavBar(
    currentScreen: NavDestination,
    onTabSelected: (NavDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier
        .height(56.dp)
        .fillMaxWidth()
    ) {
        Row(modifier = Modifier.selectableGroup()) {
            NavDestinationsForNavigationBar.forEach { screen ->
                val selected = currentScreen.route == screen.route
                val icon = if (selected) {
                    screen.selectedIcon
                } else {
                    screen.icon
                } ?: Icons.Default.Search

                NavBarItem(
                    label = screen.label,
                    icon = icon,
                    onSelected = { onTabSelected(screen) },
                    selected = selected,
                    modifier = Modifier.weight(2F)
                )
            }
        }
    }
}

@Composable
fun NavBarItem(
    @StringRes label: Int,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    val text = stringResource(id = label)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxHeight()
            .clearAndSetSemantics { contentDescription = text }
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            )
    ) {
        CompositionLocalProvider(LocalContentColor provides color) {
            if (label != R.string.tabs) {
                Icon(
                    icon,
                    contentDescription = text,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("1") // TODO replace with tabcounter
            }
            Text(text = text)
        }
    }
}


@Preview
@Composable
private fun Preview() {
    MainMenuNavBar(currentScreen = NavDestination.Browser, onTabSelected = {})
}