package com.qwant.android.qwantbrowser.suggest

import android.content.Context
import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.mozac.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mozilla.components.concept.awesomebar.AwesomeBar
import mozilla.components.feature.awesomebar.provider.ClipboardSuggestionProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SuggestionHiltModule {
    @Singleton
    @Provides fun provideQwantOpensearchProvider(
        core: Core,
        useCases: UseCases
    ) : QwantOpensearchProvider {
        return QwantOpensearchProvider(core.client, useCases.qwantUseCases.loadSERPPage)
    }

    @Singleton
    @Provides fun provideClipboardSuggestionProvider(
        @ApplicationContext context: Context,
        useCases: UseCases
    ) : ClipboardSuggestionProvider {
        return ClipboardSuggestionProvider(context, useCases.sessionUseCases.loadUrl, requireEmptyText = false)
    }

    @Singleton
    @Provides fun provideHistorySuggestionProvider(
        core: Core,
        useCases: UseCases
    ) : HistorySuggestionProvider {
        return HistorySuggestionProvider(core.historyStorage, useCases.sessionUseCases.loadUrl)
    }

    @Singleton
    @Provides fun provideAwesomeSuggestionGroups(
        clipboardProvider: ClipboardSuggestionProvider,
        qwantOpensearchProvider: QwantOpensearchProvider,
        historyProvider: HistorySuggestionProvider
    ) : List<AwesomeBar.SuggestionProviderGroup> {
        return listOf(
            AwesomeBar.SuggestionProviderGroup(
                listOf(clipboardProvider),
                priority = 100,
                title = "Clipboard"
            ),
            AwesomeBar.SuggestionProviderGroup(
                listOf(qwantOpensearchProvider),
                priority = 10,
                title = "Opensearch"
            ),
            /* AwesomeBar.SuggestionProviderGroup(
                listOf(historyProvider),
                priority = 2,
                title = "History"
            ),
            AwesomeBar.SuggestionProviderGroup(
                listOf(),
                priority = 2,
                title = "Opened tabs"
            ),
            AwesomeBar.SuggestionProviderGroup(
                listOf(),
                priority = 2,
                title = "Bookmarks"
            ) */
            /* ... */
        )
    }
}