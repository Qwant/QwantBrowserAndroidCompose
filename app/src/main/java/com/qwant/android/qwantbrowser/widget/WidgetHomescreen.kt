package com.qwant.android.qwantbrowser.widget

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.material3.ColorProviders
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.legacy.assist.Assist
import com.qwant.android.qwantbrowser.ui.theme.Grey900
import com.qwant.android.qwantbrowser.ui.theme.darkColorScheme
import com.qwant.android.qwantbrowser.ui.theme.lightColorScheme

class WidgetHomescreen: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            HomescreenGlanceWidget()
        }
    }
}

@Composable
fun HomescreenGlanceWidget() {
    val context = LocalContext.current

    GlanceTheme(colors = ColorProviders(
        light = lightColorScheme,
        dark = darkColorScheme
    )) {
        Row(
            verticalAlignment = Alignment.Vertical.CenterVertically,
            modifier = GlanceModifier
                .fillMaxWidth()
                .height(48.dp)
                .cornerRadius(24.dp)
                .background(GlanceTheme.colors.primaryContainer)
                .padding(start = 6.dp, top = 6.dp, bottom = 6.dp, end = 16.dp)
                .clickable {
                    Intent(context, Assist::class.java).apply {
                        flags = FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(this)
                    }
                }
        ) {
            Box(modifier = GlanceModifier
                .size(36.dp)
                .cornerRadius(18.dp)
                .background(GlanceTheme.colors.primary)
                .padding(6.dp)
            ) {
                Image(
                    provider = ImageProvider(R.drawable.qwant_logo),
                    colorFilter = ColorFilter.tint(ColorProvider(Grey900)),
                    contentDescription = "Logo"
                )
            }
            Text(
                text = context.getString(R.string.browser_toolbar_hint),
                style = TextStyle(
                    color = ColorProvider(GlanceTheme.colors.onPrimaryContainer.getColor(context).copy(0.6f)),
                    fontSize = 16.sp
                ),
                modifier = GlanceModifier
                    .padding(start = 12.dp)
                    .defaultWeight()
            )
            Image(
                provider = ImageProvider(R.drawable.icons_search),
                colorFilter = ColorFilter.tint(GlanceTheme.colors.primary),
                contentDescription = "Search icon",
                modifier = GlanceModifier
                    .size(30.dp)
            )
        }
    }
}