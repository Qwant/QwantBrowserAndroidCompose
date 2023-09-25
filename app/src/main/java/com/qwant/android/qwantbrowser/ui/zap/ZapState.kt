package com.qwant.android.qwantbrowser.ui.zap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.qwant.android.qwantbrowser.usecases.ClearDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO zap state could old clear data config (and clean use case)
class ZapState(
    private val clearDataUseCase: ClearDataUseCase,
    private val coroutineScope: CoroutineScope = MainScope()
) {
    enum class State { Zapping, Confirm, Waiting, Error }
    enum class AnimationState { Idle, In, Wait, Out }

    var state: State by mutableStateOf(State.Waiting)
        private set

    var animationState by mutableStateOf(AnimationState.Idle)
        private set

    private var endCallback: ((Boolean, Boolean) -> Unit)? = null

    internal fun updateAnimationState(s: AnimationState) {
        animationState = s
    }

    fun zap(then: (Boolean, Boolean) -> Unit = { _, _ -> }) {
        endCallback = then
        if (state == State.Waiting) { state = State.Confirm }
    }

    internal fun confirmZap(doIt: Boolean) {
        if (doIt) {
            state = State.Zapping
            animationState = AnimationState.In

            coroutineScope.launch {
                delay(300)
                clearDataUseCase { success, tabsCleared ->
                    // TODO handle zap fails globally ?
                    coroutineScope.launch {
                        while (animationState != AnimationState.Wait) {
                            delay(50)
                        }
                        state = if (success) State.Waiting else State.Error
                        animationState = AnimationState.Out
                        endCallback?.invoke(success, tabsCleared)
                    }
                }
            }
        } else {
            state = State.Waiting
            endCallback?.invoke(false, false)
        }
    }

    internal fun clearError() {
        if (state == State.Error)
            state = State.Waiting
    }
}

@Composable
fun rememberZapState(
    clearDataUseCase: ClearDataUseCase,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): ZapState {
    return remember { ZapState(clearDataUseCase, coroutineScope) }
}