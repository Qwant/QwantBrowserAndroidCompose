package com.example.qwantbrowsercompose.ui.tabs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.qwantbrowsercompose.R


@Composable
fun NewTabButton(
    private: Boolean,
    // privacyColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        // colors = ButtonDefaults.buttonColors(containerColor = privacyColor),
        shape = RoundedCornerShape(50),
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            tint = Color.White,
            contentDescription = "Add tab",
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = if (private) "add private tab" else "add tab",
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}