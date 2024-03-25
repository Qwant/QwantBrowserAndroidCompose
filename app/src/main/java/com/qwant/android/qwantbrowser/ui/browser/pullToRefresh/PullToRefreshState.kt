package com.qwant.android.qwantbrowser.ui.browser.pullToRefresh

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FloatTweenSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.TimeSource
import kotlin.time.measureTime

@OptIn(ExperimentalMaterial3Api::class)
interface CustomPullToRefreshState: PullToRefreshState {
    fun addOffset(offset: Float)
    fun resetOffset()
    fun animateOffset(offset: Float, duration: Int)
}

@Composable
fun rememberPullToRefreshState(): CustomPullToRefreshState {
    return remember {
        object: CustomPullToRefreshState {
            private var coroutineScope = MainScope()

            override val positionalThreshold: Float = 250f
            override val progress get() = verticalOffset / positionalThreshold
            override var verticalOffset: Float by mutableFloatStateOf(0f)
            override var isRefreshing: Boolean by mutableStateOf(false)

            override fun startRefresh() {}
            override fun endRefresh() {}

            override fun addOffset(offset: Float) {
                verticalOffset = (verticalOffset + offset).coerceIn(0f, positionalThreshold)
            }

            override fun resetOffset() {
                verticalOffset = 0f
            }

            override fun animateOffset(offset: Float, duration: Int) {
                coroutineScope.launch {
                    val tween = FloatTweenSpec(duration, easing = FastOutLinearInEasing)
                    val initialValue = verticalOffset
                    val startMark = TimeSource.Monotonic.markNow()
                    while (verticalOffset != offset) {
                        val ns = startMark.elapsedNow().inWholeNanoseconds
                        verticalOffset = tween.getValueFromNanos(ns, initialValue, offset, 0f)
                        delay(10)
                    }
                }
            }

            // Provide logic for the PullRefreshContainer to consume scrolls within a nested scroll
            override var nestedScrollConnection: NestedScrollConnection = object : NestedScrollConnection {}
        }
    }
}