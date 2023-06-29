package com.qwant.android.qwantbrowser.ui.preferences

import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.PackageInfoCompat
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceRow
import com.qwant.android.qwantbrowser.ui.widgets.QwantIconOnBackground
import org.mozilla.geckoview.BuildConfig

@Composable
fun AppDetailsPreference() {
    val context = LocalContext.current

    val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
    } else {
        @Suppress("DEPRECATION") context.packageManager.getPackageInfo(context.packageName, 0) // No alternative from compat libraries yet
    }

    PreferenceRow(
        label = R.string.qwant_details_label,
        description = stringResource(
            R.string.qwant_details_description,
            packageInfo.versionName,
            PackageInfoCompat.getLongVersionCode(packageInfo).toString(),
            BuildConfig.MOZ_APP_VERSION
        ),
        icon = { QwantIconOnBackground(shape = RoundedCornerShape(8.dp), modifier = Modifier.size(28.dp)) },
        onClicked = {}
    )
}