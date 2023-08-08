package com.qwant.android.qwantbrowser.ui.zap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ZapState(
    private val clearDataUseCase: QwantUseCases.ClearDataUseCase,
    private val coroutineScope: CoroutineScope = MainScope()
) {
    enum class State { Zapping, Confirm, Waiting, Error }
    enum class AnimationState { Idle, In, Wait, Out }

    var state: State by mutableStateOf(State.Waiting)
        private set

    var animationState by mutableStateOf(AnimationState.Idle)
        private set

    private var endCallback: ((Boolean) -> Unit)? = null

    fun updateAnimationState(s: AnimationState) {
        animationState = s
    }

    fun zap(then: (Boolean) -> Unit = {}) {
        endCallback = then
        if (state == State.Waiting) { state = State.Confirm }
    }

    internal fun confirmZap(doIt: Boolean) {
        if (doIt) {
            state = State.Zapping
            animationState = AnimationState.In
            clearDataUseCase(coroutineScope) { success ->
                // TODO handle zap fails globally ?
                coroutineScope.launch {
                    while (animationState != AnimationState.Wait) {
                        delay(50)
                    }
                    state = if (success) State.Waiting else State.Error
                    animationState = AnimationState.Out
                    endCallback?.invoke(success)
                }
            }
        } else {
            state = State.Waiting
            endCallback?.invoke(false)
        }
    }

    internal fun clearError() {
        if (state == State.Error)
            state = State.Waiting
    }
}

@Composable
fun rememberZapState(
    clearDataUseCase: QwantUseCases.ClearDataUseCase,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): ZapState {
    return remember { ZapState(clearDataUseCase, coroutineScope) }
}