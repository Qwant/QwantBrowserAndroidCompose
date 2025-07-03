package com.qwant.android.qwantbrowser.ui.browser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.contentBlocker.ContentBlockerState
import com.qwant.android.qwantbrowser.stats.Piwik
import com.qwant.android.qwantbrowser.storage.bookmarks.BookmarksRepository
import com.qwant.android.qwantbrowser.ui.browser.toolbar.BrowserToolbarState
import com.qwant.android.qwantbrowser.ui.browser.toolbar.BrowserToolbarStateFactory
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import com.qwant.android.qwantbrowser.vip.VIPState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mozilla.components.browser.engine.gecko.permission.GeckoSitePermissionsStorage
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.browser.state.selector.normalTabs
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.fetch.Client
import mozilla.components.feature.contextmenu.ContextMenuUseCases
import mozilla.components.feature.downloads.DownloadsUseCases
import mozilla.components.feature.downloads.FileSizeFormatter
import mozilla.components.feature.downloads.manager.DownloadManager
import mozilla.components.feature.pwa.WebAppUseCases
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.feature.tabs.TabsUseCases
import mozilla.components.lib.state.ext.flow
import mozilla.components.support.ktx.kotlin.isUrl
import mozilla.components.support.ktx.kotlin.toNormalizedUrl
import mozilla.components.support.ktx.kotlinx.coroutines.flow.ifAnyChanged
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class BrowserScreenViewModel @Inject constructor(
    private val bookmarksRepository: BookmarksRepository,
    private val webAppUseCases: WebAppUseCases,
    val sessionUseCases: SessionUseCases,
    val tabsUseCases: TabsUseCases,
    val contextMenuUseCases: ContextMenuUseCases,
    val downloadUseCases: DownloadsUseCases,
    val fileSizeFormatter: FileSizeFormatter,
    val permissionStorage: GeckoSitePermissionsStorage,
    val browserIcons: BrowserIcons,
    val downloadManager: DownloadManager,
    val store: BrowserStore,
    val engine: Engine,
    val client: Client,
    val qwantUseCases: QwantUseCases,
    val piwik: Piwik,
    val contentBlockerState: ContentBlockerState,
    val vipState: VIPState
): ViewModel() {
    @Inject lateinit var toolbarStateFactory: BrowserToolbarStateFactory
    val toolbarState: BrowserToolbarState by lazy {
        toolbarStateFactory.create(viewModelScope)
    }

    val tabCount = store.flow()
        .ifAnyChanged { s -> arrayOf(s.tabs.size, s.selectedTab?.content?.private) }
        .map { state -> state.tabs.count { it.content.private == state.selectedTab?.content?.private } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = 0
        )

    private val urlFlow = store.flow()
        .map { state -> state.selectedTab?.content?.url }
        .distinctUntilChanged()

    val currentUrl = urlFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val currentEngineSession = store.flow()
        .map { state -> state.selectedTab?.engineState?.engineSession }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    var isUrlBookmarked = urlFlow
        .filterNotNull()
        .flatMapLatest { bookmarksRepository.isUrlBookmarkedFlow(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    init {
        urlFlow
            .mapNotNull { it }
            .mapLatest {
                delay(2000)
                piwik.screenView(it)
            }
            .launchIn(viewModelScope)
    }

    fun addBookmark() {
        store.state.selectedTab?.let { tab ->
            viewModelScope.launch(Dispatchers.IO) {
                bookmarksRepository.addItem(
                    parentGuid = bookmarksRepository.root.guid,
                    url = tab.content.url,
                    title = tab.content.title,
                    position = null
                )
            }
        }
    }

    fun removeBookmark() {
        store.state.selectedTab?.content?.url?.let { url ->
            viewModelScope.launch(Dispatchers.IO) {
                bookmarksRepository.deleteBookmarksByUrl(url)
            }
        }
    }

    val canGoBack = store.flow()
        .map { state -> state.selectedTab?.content?.canGoBack ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val canGoForward = store.flow()
        .map { state -> state.selectedTab?.content?.canGoForward ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val desktopMode = store.flow()
        .map { state -> state.selectedTab?.content?.desktopMode ?: false }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    var showFindInPage by mutableStateOf(false)
        private set

    val isShortcutSupported = webAppUseCases.isPinningSupported()

    fun addShortcutToHomeScreen() {
        viewModelScope.launch {
            webAppUseCases.addToHomescreen()
        }
    }

    val reloadUrl = sessionUseCases.reload
    val stopLoading = sessionUseCases.stopLoading
    val goBack = sessionUseCases.goBack
    val goForward = sessionUseCases.goForward
    val requestDesktopSite = sessionUseCases.requestDesktopSite

    fun commitSearch(searchText: String, category: String? = null) {
        if (searchText.isUrl()) {
            sessionUseCases.loadUrl(url = searchText.toNormalizedUrl())
        } else {
            qwantUseCases.loadSERPPage(searchText, category)
        }
    }

    fun openNewQwantTab(private: Boolean = false) {
        if (private) {
            qwantUseCases.openPrivatePage()
        } else {
            qwantUseCases.openQwantPage(private = false)
        }
        // TODO use invokeOnCompletion from store.dispatch instead of delay,
        //  but this needs QwantUseCases to be recoded using dispatch directly
        viewModelScope.launch {
            delay(100)
            toolbarState.updateFocus(true)
        }
    }

    fun goToHomepage() {
        sessionUseCases.loadUrl(url = qwantUseCases.getQwantUrl())
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
        val private = store.state.selectedTab?.content?.private ?: false
        val lastTab = store.state.normalTabs.count() == 1 && !private
        store.state.selectedTabId?.let {
            tabsUseCases.removeTab(it, selectParentIfExists = true)
            piwik.event("Tab", "Tap", "Close current tab - ${if (private) "Private" else "Normal"}")
            if (lastTab) {
                qwantUseCases.openQwantPage()
            }
        }
    }

    fun updateShowFindInPage(show: Boolean) {
        toolbarState.updateVisibility(!show)
        showFindInPage = show
    }
}
