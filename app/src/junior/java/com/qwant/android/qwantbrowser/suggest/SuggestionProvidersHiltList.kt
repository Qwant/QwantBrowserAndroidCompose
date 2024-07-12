package com.qwant.android.qwantbrowser.suggest

import com.qwant.android.qwantbrowser.suggest.providers.ClipboardProvider
import com.qwant.android.qwantbrowser.suggest.providers.DomainProvider
import com.qwant.android.qwantbrowser.suggest.providers.QwantSuggestProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object SuggestionProvidersHiltModule {
    @Provides fun provideSuggestionProviders(
        clipboardProvider: ClipboardProvider,
        qwantSuggestProvider: QwantSuggestProvider,
        domainProvider: DomainProvider
    ): List<SuggestionProvider> {
        return listOf(
            clipboardProvider,
            qwantSuggestProvider,
            domainProvider
        )
    }
}