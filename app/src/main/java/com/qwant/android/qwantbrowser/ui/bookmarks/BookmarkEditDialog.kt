package com.qwant.android.qwantbrowser.ui.bookmarks

import android.widget.Toast
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog
import org.mozilla.reference.browser.storage.BookmarkItemV2

@Composable
fun BookmarkEditDialog(
    item: BookmarkItemV2? = null,
    onDismiss: () -> Unit = {},
    onSubmit: (title: String, url: String?) -> Unit = { _,_ -> }
) {
    var itemTitle by remember {
        val text =
            if (item?.title?.isNotEmpty() == true) item.title
            else "Folder name" // TODO translate/change placeholder text ?
        mutableStateOf(TextFieldValue(
            text = item?.title ?: text,
            TextRange(0, text.length))
        )
    }
    var itemUrl by remember { mutableStateOf(item?.url) }

    val context = LocalContext.current

    YesNoDialog(
        onDismissRequest = { onDismiss() },

        title = (if (item == null) "Create" else "Edit") + // TODO bookmark create/edit title text
                (if (item?.type == BookmarkItemV2.BookmarkType.BOOKMARK) " bookmark" else " folder"),
        onYes = {
            if (itemTitle.text.isEmpty() || (item?.type == BookmarkItemV2.BookmarkType.BOOKMARK && itemUrl?.isEmpty() == true)) {
                Toast.makeText(context, "all_fields_required", Toast.LENGTH_LONG).show()
            } else {
                onSubmit(itemTitle.text, itemUrl)
                onDismiss()
            }
        },
        onNo = { onDismiss() },
        yesText = "Ok", // TODO translate
        noText = "Cancel",
        additionalContent = {
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(true) {
                focusRequester.requestFocus()
            }
            TextField(
                value = itemTitle,
                onValueChange = { itemTitle = it },
                modifier = Modifier.focusRequester(focusRequester)
            )
            // TODO folder selection popup
            if (item?.type == BookmarkItemV2.BookmarkType.BOOKMARK) {
                TextField(
                    value = itemUrl ?: "",
                    onValueChange = { itemUrl = it }
                )
            }
        }
    )
}