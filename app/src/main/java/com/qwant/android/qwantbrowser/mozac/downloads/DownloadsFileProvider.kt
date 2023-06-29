package com.qwant.android.qwantbrowser.mozac.downloads

import android.content.Context
import androidx.core.content.FileProvider
import com.qwant.android.qwantbrowser.ext.openFileInApp
import mozilla.components.feature.downloads.R
import java.io.File

class DownloadsFileProvider : FileProvider(R.xml.feature_downloads_file_paths)

fun Context.openDownloadedFile(filePath: String, contentType: String?) {
    this.openFileInApp(
        FileProvider.getUriForFile(
        this,
        "${this.packageName}.feature.downloads.fileprovider",
        File(filePath),
    ), contentType)
}