package com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.dialog.internalcopy.PromptAbuserDetector
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog
import mozilla.components.concept.engine.prompt.PromptRequest
import mozilla.components.feature.prompts.R as mozacR

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
            additionalContent = { abusingControls() },
            yesText = request.positiveButtonTitle.ifBlank { stringResource(mozacR.string.mozac_feature_prompts_ok) },
            noText = request.negativeButtonTitle.ifBlank { stringResource(mozacR.string.mozac_feature_prompts_cancel) },
        )
    }
}