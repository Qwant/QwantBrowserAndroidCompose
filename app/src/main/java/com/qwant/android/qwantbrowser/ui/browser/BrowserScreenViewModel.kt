package com.qwant.android.qwantbrowser.ui.browser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases
import com.qwant.android.qwantbrowser.ui.browser.toolbar.ToolbarState
import com.qwant.android.qwantbrowser.ui.browser.toolbar.ToolbarStateFactory
import com.qwant.android.qwantbrowser.vip.QwantVIPFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mozilla.components.browser.state.action.WebExtensionAction
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.lib.state.ext.flow
import mozilla.components.support.ktx.kotlin.isUrl
import mozilla.components.support.ktx.kotlin.toNormalizedUrl
import mozilla.components.support.ktx.kotlinx.coroutines.flow.ifChanged
import javax.inject.Inject

@HiltViewModel
class BrowserScreenViewModel @Inject constructor(
    private val mozac: Core,
    private val useCases: UseCases
): ViewModel() {
    @Inject lateinit var toolbarStateFactory: ToolbarStateFactory
    val toolbarState: ToolbarState by lazy {
        toolbarStateFactory.create(viewModelScope)
    }

    val tabCount = mozac.store.flow()
        .map { state -> state.tabs.count() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = 0
        )

    private val urlFlow = mozac.store.flow()
        .map { state -> state.selectedTab?.content?.url }

    val currentUrl = urlFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    var isUrlBookmarked by mutableStateOf(false)
        private set

    init {
        urlFlow
            .distinctUntilChanged()
            .mapNotNull { it }
            .onEach { isUrlBookmarked = mozac.bookmarkStorage.contains(it) }
            .launchIn(viewModelScope)

        mozac.bookmarkStorage.onChange {
            mozac.store.state.selectedTab?.let {
                isUrlBookmarked = mozac.bookmarkStorage.contains(it.content.url)
            }
        }
    }

    fun addBookmark() {
        mozac.bookmarkStorage.addBookmark(mozac.store.state.selectedTab)
    }

    fun removeBookmark() {
        mozac.bookmarkStorage.deleteBookmark(mozac.store.state.selectedTab)
    }

    val canGoBack = mozac.store.flow()
        .map { state -> state.selectedTab?.content?.canGoBack ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val canGoForward = mozac.store.flow()
        .map { state -> state.selectedTab?.content?.canGoForward ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val desktopMode = mozac.store.flow()
        .map { state -> state.selectedTab?.content?.desktopMode ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    var showFindInPage by mutableStateOf(false)
        private set

    val isShortcutSupported = useCases.webAppUseCases.isPinningSupported()

    fun addShortcutToHomeScreen() {
        viewModelScope.launch {
            useCases.webAppUseCases.addToHomescreen()
        }
    }

    val reloadUrl = useCases.sessionUseCases.reload
    val stopLoading = useCases.sessionUseCases.stopLoading
    val goBack = useCases.sessionUseCases.goBack
    val goForward = useCases.sessionUseCases.goForward
    val requestDesktopSite = useCases.sessionUseCases.requestDesktopSite

    val engine = mozac.engine
    val store = mozac.store
    val browserIcons = mozac.browserIcons
    val downloadManager = mozac.downloadManager
    val permissionStorage = mozac.geckoSitePermissionsStorage
    val sessionUseCases = useCases.sessionUseCases
    val tabsUseCases = useCases.tabsUseCases
    val contextMenuUseCases = useCases.contextMenuUseCases
    val downloadUseCases = useCases.downloadUseCases

    fun commitSearch(searchText: String) {
        if (searchText.isUrl()) {
            // useCases.tabsUseCases.selectOrAddTab(url = searchText.toNormalizedUrl())
            useCases.sessionUseCases.loadUrl(url = searchText.toNormalizedUrl())
        } else {
            useCases.qwantUseCases.loadSERPPage(searchText)
        }
    }

    fun openNewQwantTab(private: Boolean = false) {
        if (private) {
            useCases.qwantUseCases.openPrivatePage()
        } else {
            useCases.qwantUseCases.openQwantPage(private = false)
        }
        // TODO use invokeOnCompletion from store.dispatch instead of delay,
        //  but this needs QwantUseCases to be recoded using dispatch directly
        viewModelScope.launch {
            delay(100)
            toolbarState.updateFocus(true)
        }
    }

    fun openSafetyTabIfNeeded() {
        if (tabCount.value == 0) {
            viewModelScope.launch {
                delay(50)
                if (tabCount.value == 0)
                    openNewQwantTab()
            }
        }
    }

    fun closeCurrentTab() {
        mozac.store.state.selectedTabId?.let {
            useCases.tabsUseCases.removeTab(it, selectParentIfExists = true)
        }
    }

    // TODO Move to dedicated VIPExtensionState with browser action and icon
    val vipExtensionState = mozac.store.flow()
        .mapNotNull { state -> state.extensions[QwantVIPFeature.ID] }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    private val vipBrowserActionFlow = mozac.store.flow()
        .mapNotNull { state ->
            state.selectedTab?.extensionState?.get(QwantVIPFeature.ID)?.browserAction
                ?: state.extensions[QwantVIPFeature.ID]?.browserAction
        }

    val vipIcon = vipBrowserActionFlow
        .mapNotNull { it.loadIcon }
        .ifChanged()
        .mapNotNull { it(92)?.asImageBitmap() }
        .map { BitmapPainter(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val vipCounter = vipBrowserActionFlow
        .mapNotNull { it.badgeText }
        .ifChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    fun closeQwantVIPPopup() {
        store.dispatch(
            WebExtensionAction.UpdatePopupSessionAction(QwantVIPFeature.ID, popupSession = null)
        )
    }

    fun updateShowFindInPage(show: Boolean) {
        toolbarState.updateVisibility(!show)
        showFindInPage = show
    }
}
