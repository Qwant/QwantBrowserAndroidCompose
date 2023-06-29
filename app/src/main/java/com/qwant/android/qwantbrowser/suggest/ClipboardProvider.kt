package com.qwant.android.qwantbrowser.suggest

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/* @Singleton
class ClipboardProvider @Inject constructor(
    @ApplicationContext context: Context
) : SuggestionProvider {
    private val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    override fun getSuggestions(search: String): List<Suggestion> {
        if (clipboard.primaryClipDescription?.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) == true) {
            clipboard.primaryClip?.getItemAt(0)?.let {
                if (it.text?.isNotEmpty() == true) {
                    return listOf(Suggestion(SuggestionType.CLIPBOARD, it.text.toString()))
                } else if (it.uri != null) {
                    val uri = it.uri.toString()
                    return listOf(Suggestion(SuggestionType.CLIPBOARD, uri, uri))
                }
            }
        }

        return listOf()
    }
} */