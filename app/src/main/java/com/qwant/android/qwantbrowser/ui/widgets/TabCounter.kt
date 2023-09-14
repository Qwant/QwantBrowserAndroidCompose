package com.qwant.android.qwantbrowser.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qwant.android.qwantbrowser.R


@Composable
fun TabCounter(tabCount: Int) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icons_checkbox_unchecked),
            contentDescription = "tab counter border",
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = if (tabCount > 99) ":)" else tabCount.toString(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}