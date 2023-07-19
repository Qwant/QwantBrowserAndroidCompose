package com.qwant.android.qwantbrowser.ui.bookmarks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mozilla.components.support.ktx.kotlin.toNormalizedUrl
import org.mozilla.reference.browser.storage.BookmarkItemV2
import java.security.InvalidParameterException
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BookmarksScreenViewModel @Inject constructor(
    private val core: Core,
    private val useCases: UseCases
): ViewModel() {
    val storage = core.bookmarkStorage

    var currentFolder: BookmarkItemV2? by mutableStateOf(null)
        private set
    var currentBookmarks by mutableStateOf(storage.root().toTypedArray())
        private set

    val bookmarksRoot = storage.root()

    init {
        storage.onChange { setBookmarksFromFolder() }
        snapshotFlow { currentFolder }
            .distinctUntilChanged()
            .onEach { setBookmarksFromFolder() }
            .launchIn(viewModelScope)
    }

    fun visitFolder(folder: BookmarkItemV2?) {
        currentFolder = folder
    }

    fun addFolder(name: String, parent: BookmarkItemV2? = null) {
        core.bookmarkStorage.addBookmark(
            BookmarkItemV2(BookmarkItemV2.BookmarkType.FOLDER, name, parent = parent)
        )
    }

    fun deleteBookmark(item: BookmarkItemV2) {
        core.bookmarkStorage.deleteBookmark(item)
    }

    fun editBookmark(item: BookmarkItemV2, title: String, url: String? = null) {
        item.title = title
        item.url = url

        core.bookmarkStorage.persist()
    }

    fun moveBookmark(item: BookmarkItemV2, to: BookmarkItemV2?) {
        if (to == null || to.type == BookmarkItemV2.BookmarkType.FOLDER) {
            if (item.parent != to) {
                // remove from parent or root
                item.parent?.removeChild(item) ?: bookmarksRoot.remove(item)
                // add to parent or root
                to?.addChild(item) ?: bookmarksRoot.add(item)
                // change child parent
                item.parent = to

                setBookmarksFromFolder()
                core.bookmarkStorage.persist()
            }
        } else {
            throw InvalidParameterException("Can't move bookmark to non-folder location `to` = $to")
        }
    }

    val browserIcons = core.browserIcons

    private fun setBookmarksFromFolder() {
        currentBookmarks = (currentFolder?.children ?: storage.root())
            .sortedWith(compareByDescending<BookmarkItemV2> { it.type }
                .thenBy { it.title.lowercase(Locale.getDefault()) })
            .toTypedArray()
    }

    fun openBookmarkTab(item: BookmarkItemV2, private: Boolean = false) {
        item.url?.let {
            useCases.tabsUseCases.addTab(it.toNormalizedUrl(), private = private)
        }
    }
}