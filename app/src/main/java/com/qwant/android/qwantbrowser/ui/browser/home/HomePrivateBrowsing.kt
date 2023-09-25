package com.qwant.android.qwantbrowser.ui.browser.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qwant.android.qwantbrowser.R

@Composable
fun HomePrivateBrowsing(
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Surface(modifier = modifier.fillMaxSize().clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        focusManager.clearFocus()
    }) {
        Column(modifier = Modifier.padding(24.dp)) {
            Image(
                painter = painterResource(id = R.drawable.private_browsing_image),
                contentDescription = "private browsing image",
                modifier = Modifier
                    .size(width = 80.dp, height = 100.dp)
                    .padding(bottom = 20.dp)
            )
            Text(
                text = stringResource(id = R.string.privatebrowsing_title),
                fontSize = 24.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.privatebrowsing_paragraph_1),
                fontSize = 14.sp,
                lineHeight = 18.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = stringResource(id = R.string.privatebrowsing_paragraph_2),
                fontSize = 14.sp,
                lineHeight = 18.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}