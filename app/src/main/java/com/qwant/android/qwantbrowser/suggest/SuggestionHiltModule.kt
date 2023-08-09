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
}