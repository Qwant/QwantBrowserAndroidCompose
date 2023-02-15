package com.qwant.android.qwantbrowser.suggest

import com.qwant.android.qwantbrowser.mozac.Core
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
    @Provides fun provideGroupedSuggestionProvider(
        clipboardProvider: ClipboardProvider,
        qwantOpensearchProvider: QwantOpensearchProvider
    ) : GroupedSuggestionProvider {
        return GroupedSuggestionProvider(
            listOf(clipboardProvider, qwantOpensearchProvider)
        )
    }
}