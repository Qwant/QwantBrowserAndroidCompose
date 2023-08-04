package com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.dialog

import androidx.compose.runtime.Composable
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.dialog.internalcopy.PromptAbuserDetector
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog
import mozilla.components.concept.engine.prompt.PromptRequest

@Composable
fun ConfirmDialog(
    promptAbuserDetector: PromptAbuserDetector,
    request: PromptRequest.Confirm,
    onConfirm: () -> Unit,
    onRefuse: () -> Unit,
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
            onNo = onRefuse,
            title = request.title,
            description = request.message,
            additionalContent = { abusingControls() }
        )
    }
}