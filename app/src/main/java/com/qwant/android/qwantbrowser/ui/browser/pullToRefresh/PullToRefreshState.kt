package com.qwant.android.qwantbrowser.ui.browser.pullToRefresh

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection

@OptIn(ExperimentalMaterial3Api::class)

interface CustomPullToRefreshState: PullToRefreshState {
    fun addOffset(offset: Float)
    fun resetOffset()
}

@Composable
fun rememberPullToRefreshState(): CustomPullToRefreshState {
    return remember {
        object: CustomPullToRefreshState {
            override val positionalThreshold: Float = 250f
            override val progress get() = verticalOffset / positionalThreshold
            override var verticalOffset: Float by mutableFloatStateOf(0f)
            override var isRefreshing: Boolean by mutableStateOf(false)

            override fun startRefresh() {}
            override fun endRefresh() {}

            override fun addOffset(offset: Float) {
                val before = verticalOffset
                verticalOffset = (verticalOffset + offset).coerceIn(0f, positionalThreshold)
            }

            override fun resetOffset() { verticalOffset = 0f }

            // Provide logic for the PullRefreshContainer to consume scrolls within a nested scroll
            override var nestedScrollConnection: NestedScrollConnection = object : NestedScrollConnection {}
        }
    }
}