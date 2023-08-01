package com.qwant.android.qwantbrowser.ui.zap

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.qwant.android.qwantbrowser.ui.widgets.YesNoDialog


@Composable
fun ZapFeature(
    state: ZapState
) {
    ZapAnimation(state)

    when (state.state) {
        ZapState.State.Confirm -> ZapConfirmDialog(state)
        ZapState.State.Error -> ZapErrorDialog(state)
        else -> {}
    }
}


@Composable
internal fun ZapAnimation(
    state: ZapState
) {
    val outOffset = LocalConfiguration.current.screenHeightDp.dp / 2
    val inOffset = 0.dp
    val targetOffset = remember(state.animationState) {
        when (state.animationState) {
            ZapState.AnimationState.In, ZapState.AnimationState.Wait  -> inOffset
            ZapState.AnimationState.Out, ZapState.AnimationState.Idle -> outOffset
        }
    }

    val verticalOffset by animateDpAsState(
        targetValue = targetOffset,
        label = "vertical offset",
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        finishedListener = {
            when (state.animationState) {
                ZapState.AnimationState.In -> state.updateAnimationState(ZapState.AnimationState.Wait)
                ZapState.AnimationState.Out -> state.updateAnimationState(ZapState.AnimationState.Idle)
                else -> {}
            }
        }
    )

    if (state.animationState != ZapState.AnimationState.Idle) {
        // TODO replace animation dialog with surface, but we need first to modify fullscreen preferences (which are also dialogs ...)
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = {}
        ) {
            if (state.animationState == ZapState.AnimationState.Wait) {
                Surface(
                    color = Color.Yellow,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {}
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Surface(
                        color = Color.Green,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .align(Alignment.TopCenter)
                            .offset(0.dp, -verticalOffset)
                    ) {}
                    Surface(
                        color = Color.Blue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f)
                            .align(Alignment.BottomCenter)
                            .offset(0.dp, verticalOffset)
                    ) {}
                }
            }
        }
    }
}

@Composable
internal fun ZapConfirmDialog(state: ZapState) {
    // TODO zap confirm texts
    YesNoDialog(
        onDismissRequest = { state.confirmZap(false) },
        onYes = { state.confirmZap(true) },
        onNo = { state.confirmZap(false) },
        title = "Zap",
        description = "Ca va tout effacer !!",
        yesText = "Effacer tout",
        noText = "Annuler"
    )
}

@Composable
internal fun ZapErrorDialog(state: ZapState) {
    // TODO zap error texts
    YesNoDialog(
        onDismissRequest = { state.clearError() },
        onYes = { state.confirmZap(true) },
        onNo = { state.clearError() },
        title = "Zap",
        description = "Erreur pendant le zap",
        yesText = "Try again",
        noText = "Annuler"
    )
}
