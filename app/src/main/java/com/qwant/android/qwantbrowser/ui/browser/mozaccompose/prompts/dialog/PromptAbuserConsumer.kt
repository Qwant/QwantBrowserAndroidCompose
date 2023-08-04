package com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.prompts.dialog.internalcopy.PromptAbuserDetector
import mozilla.components.feature.prompts.R as mozacR

@Composable
fun PromptAbuserConsumer(
    promptAbuserDetector: PromptAbuserDetector,
    onAbuse: () -> Unit,
    content: @Composable (
        promptAbuserConfirm: () -> Unit,
        promptAbuserControls: @Composable () -> Unit,
    ) -> Unit
) {
    val showPrompt = remember { promptAbuserDetector.shouldShowMoreDialogs }
    val abusing = remember { promptAbuserDetector.areDialogsBeingAbused() }

    if (showPrompt) {
        LaunchedEffect(true) {
            promptAbuserDetector.updateJSDialogAbusedState()
        }

        var stopAbusingCheckbox by remember { mutableStateOf(false) }

        content(
            promptAbuserConfirm = { promptAbuserDetector.userWantsMoreDialogs(!stopAbusingCheckbox) },
            promptAbuserControls = {
                if (abusing) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = stopAbusingCheckbox,
                            onCheckedChange = {
                                stopAbusingCheckbox = it
                            }
                        )
                        Text(text = stringResource(id = mozacR.string.mozac_feature_prompts_no_more_dialogs)) // TODO text and traduction
                    }
                }
            }
        )
    } else {
        SideEffect {
            onAbuse()
        }
    }
}