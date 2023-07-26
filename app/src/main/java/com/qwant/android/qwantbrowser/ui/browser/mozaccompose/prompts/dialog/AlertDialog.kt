package com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.dialog


import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.dialog.internalcopy.PromptAbuserDetector
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog
import mozilla.components.feature.prompts.R

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
            additionalContent = { abusingControls() },
            yesText = stringResource(R.string.mozac_feature_prompts_ok),
            noText = stringResource(R.string.mozac_feature_prompts_cancel)
        )
    }

}