package com.qwant.android.qwantbrowser.ui.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp


@Composable
fun BigButton(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val title = stringResource(id = text)
    Button(
        shape = RoundedCornerShape(50),
        modifier = modifier,
        onClick = { onClick() },
        enabled = enabled
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = title,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}