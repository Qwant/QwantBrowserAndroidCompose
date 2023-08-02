package com.qwant.android.qwantbrowser.ui.browser.mozaccompose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.engine.EngineView
import mozilla.components.feature.session.SessionFeature
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.feature.tabs.CustomTabsUseCases
import mozilla.components.feature.tabs.TabsUseCases
import mozilla.components.lib.state.ext.observeAsComposableState

@Composable
fun SessionFeature(
    engineView: EngineView,
    store: BrowserStore,
    canGoBack: Boolean,
    goBackUseCase: SessionUseCases.GoBackUseCase,
    tabsUseCases: TabsUseCases,
    openQwantUseCase: QwantUseCases.OpenQwantPageUseCase,
    backEnabled: () -> Boolean = { true }
) {
    val feature = remember(engineView) {
        SessionFeature(
            store = store,
            goBackUseCase = goBackUseCase,
            engineView = engineView
        )
    }

    ComposeFeatureWrapper(feature = feature) {
        if (backEnabled()) {
            if (engineView.canClearSelection()) {
                BackHandler(true) { engineView.clearSelection() }
            } else if (canGoBack) {
                BackHandler(true) { goBackUseCase() }
            } else {
                val selectedTab by store.observeAsComposableState { it.selectedTab }
                selectedTab?.let { tab ->
                    tab.parentId?.let { parent ->
                        BackHandler(true) {
                            tabsUseCases.selectTab(parent)
                            tabsUseCases.removeTab(tab.id)
                        }
                    } ?: BackHandler(selectedTab?.content?.url?.isQwantUrl() == false) {
                        openQwantUseCase(private = selectedTab?.content?.private ?: false, selectIfExists = true)
                        tabsUseCases.removeTab(tab.id)
                    }
                }
            }
        }
    }
}