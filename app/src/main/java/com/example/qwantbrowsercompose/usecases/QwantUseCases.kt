package com.example.qwantbrowsercompose.usecases

import com.example.qwantbrowsercompose.preferences.frontend.FrontEndPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.support.ktx.kotlin.urlEncode


class QwantUseCases(
    frontEndPreferencesRepository: FrontEndPreferencesRepository,
    sessionUseCases: SessionUseCases
) {
    class OpenHomePageUseCase internal constructor(
        private val frontEndPreferencesRepository: FrontEndPreferencesRepository,
        private val sessionUseCases: SessionUseCases
    ) {
        suspend operator fun invoke() {
            sessionUseCases.loadUrl(url = frontEndPreferencesRepository.homeUrl.first())
        }

        operator fun invoke(coroutineScope: CoroutineScope) {
            coroutineScope.launch {
                sessionUseCases.loadUrl(url = frontEndPreferencesRepository.homeUrl.first())
            }
        }
    }

    class OpenSERPPageUseCase internal constructor(
        private val frontEndPreferencesRepository: FrontEndPreferencesRepository,
        private val sessionUseCases: SessionUseCases
    ) {
        suspend operator fun invoke(search: String) {
            val base = frontEndPreferencesRepository.homeUrl.first()
            sessionUseCases.loadUrl(url = "$base&q=${search.urlEncode()}")
        }

        operator fun invoke(search: String, coroutineScope: CoroutineScope) {
            coroutineScope.launch {
                val base = frontEndPreferencesRepository.homeUrl.first()
                sessionUseCases.loadUrl(url = "$base&q=${search.urlEncode()}")
            }
        }
    }

    val openHomePage: OpenHomePageUseCase by lazy {
        OpenHomePageUseCase(frontEndPreferencesRepository, sessionUseCases)
    }
    val openSERPPage: OpenSERPPageUseCase by lazy {
        OpenSERPPageUseCase(frontEndPreferencesRepository, sessionUseCases)
    }
}