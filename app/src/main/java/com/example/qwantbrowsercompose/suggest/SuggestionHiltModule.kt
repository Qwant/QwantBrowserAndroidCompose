package com.example.qwantbrowsercompose.suggest

import android.content.Context
import com.example.qwantbrowsercompose.mozac.Core
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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