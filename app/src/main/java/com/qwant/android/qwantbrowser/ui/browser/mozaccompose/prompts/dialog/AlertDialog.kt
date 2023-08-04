package com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.dialog

import androidx.compose.runtime.Composable
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.dialog.internalcopy.PromptAbuserDetector
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog

@Composable
fun AlertDialog(
    promptAbuserDetector: PromptAbuserDetector,
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    PromptAbuserConsumer(
        promptAbuserDetector = promptAbuserDetector,
        onAbuse = onDismiss
    ) { abusingConfirm, abusingControls ->
        YesNoDialog(
            onDismissRequest = onDismiss,
            onYes = {
                abusingConfirm()
                onConfirm()
            },
            onNo = onDismiss,
            title = title,
            description = message,
            additionalContent = { abusingControls() }
        )
    }

}