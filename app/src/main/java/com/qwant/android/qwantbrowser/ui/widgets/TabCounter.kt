package com.qwant.android.qwantbrowser.ui.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TabCounter(tabCount: Int) {
    Text(
        text = if (tabCount > 99) ":)" else tabCount.toString(),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 16.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 2.dp,
                color = LocalContentColor.current,
                shape = RoundedCornerShape(4.dp)
            )
            .wrapContentHeight()
    )
}