package com.qwant.android.qwantbrowser.vip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.theme.LocalQwantTheme

@Composable
fun VIPPopup(
    vipState: VIPState,
    openLink: (String) -> Unit
) {
    val isDarkTheme = LocalQwantTheme.current.dark
    val colors = when {
        vipState.enabled && isDarkTheme -> enabledDark
        vipState.enabled && !isDarkTheme -> enabledLight
        !vipState.enabled && isDarkTheme -> disabledDark
        !vipState.enabled && !isDarkTheme -> disabledLight
        else -> throw (Exception("Invalid color set for VIP colors"))
    }.animatedColors()

    val titleText = if (vipState.enabled) R.string.vip_protection_enabled
                    else R.string.vip_protection_disabled
    val descText = if (vipState.enabled) R.string.vip_protection_enabled_hint
                   else R.string.vip_protection_disabled_hint

    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
        Column(modifier = Modifier
            .background(color = colors.content)
            .border(2.dp, colors.border, RoundedCornerShape(8.dp))
            .padding(start = 12.dp, end = 12.dp, bottom = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(titleText),
                    color = colors.toggleBorder,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(2f).padding(end = 20.dp)
                )
                Switch(
                    checked = vipState.enabled,
                    onCheckedChange = { vipState.toggleProtection() },
                    colors = switchColorsLight(isDarkTheme)
                )
            }
            Text(text = stringResource(descText))
        }

        Spacer(Modifier.height(12.dp))

        Column(modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
        ) {
            VIPLink(stringResource(R.string.vip_about), stringResource(R.string.vip_about_link), openLink)
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
            VIPLink(stringResource(R.string.vip_comment), stringResource(R.string.vip_comment_link), openLink)
        }
    }
}

@Composable
fun VIPLink(
    text: String,
    url: String,
    openLink: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .clickable { openLink(url) }
            .padding(8.dp)
    ) {
        Text(text = text, modifier = Modifier.weight(2f))
        Icon(
            painter = painterResource(R.drawable.icons_arrow_tab),
            contentDescription = "arrow"
        )
    }
}