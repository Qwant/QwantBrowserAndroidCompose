package com.qwant.android.qwantbrowser.suggest

import com.qwant.android.qwantbrowser.mozac.Core
import com.qwant.android.qwantbrowser.suggest.providers.QwantOpensearchProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SuggestionHiltModule {
    @Singleton
    @Provides fun provideQwantOpensearchProvider(
        core: Core
    ) : QwantOpensearchProvider {
        return QwantOpensearchProvider(core.client)
    }

    @Singleton
    @Provides fun aaa() : List<SuggestionProvider> {
        return listOf<SuggestionProvider>()
    }

    /* @Singleton
    @Provides fun provideSuggestionProviders(
        qwantOpensearchProvider: QwantOpensearchProvider
    ) : List<SuggestionProvider> {
        return listOf(qwantOpensearchProvider)
    } */

    /* @Singleton
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
                // priority = 100,
                title = "Clipboard"
            ),
            AwesomeBar.SuggestionProviderGroup(
                listOf(qwantOpensearchProvider),
                // priority = 10,
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
    } */
}