package com.qwant.android.qwantbrowser.suggest

import com.qwant.android.qwantbrowser.legacy.bookmarks.BookmarksStorage
import com.qwant.android.qwantbrowser.legacy.history.History
import com.qwant.android.qwantbrowser.suggest.providers.ClipboardProvider
import com.qwant.android.qwantbrowser.suggest.providers.DomainProvider
import com.qwant.android.qwantbrowser.suggest.providers.QwantSuggestProvider
import com.qwant.android.qwantbrowser.suggest.providers.SessionTabsProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mozilla.components.concept.storage.HistoryStorage

@InstallIn(SingletonComponent::class)
@Module
object SuggestionProvidersHiltModule {
    @Provides fun provideSuggestionProviders(
        clipboardProvider: ClipboardProvider,
        qwantSuggestProvider: QwantSuggestProvider,
        domainProvider: DomainProvider,
        sessionTabsProvider: SessionTabsProvider,
        historyStorage: HistoryStorage,
        bookmarkStorage: BookmarksStorage
    ): List<SuggestionProvider> {
        return listOf(
            clipboardProvider,
            qwantSuggestProvider,
            domainProvider,
            sessionTabsProvider,
            historyStorage as History,
            bookmarkStorage
        )
    }
}