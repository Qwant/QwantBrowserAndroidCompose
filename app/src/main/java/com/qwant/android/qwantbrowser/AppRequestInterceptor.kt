package com.qwant.android.qwantbrowser

import android.content.Context
import com.qwant.android.qwantbrowser.ext.UA
import com.qwant.android.qwantbrowser.ext.UAQwant
import com.qwant.android.qwantbrowser.ext.getQwantSERPSearch
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.ext.isQwantUrlValid
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import dagger.Lazy
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mozilla.components.browser.errorpages.ErrorPages
import mozilla.components.browser.errorpages.ErrorType
import mozilla.components.concept.engine.EngineSession
import mozilla.components.concept.engine.request.RequestInterceptor
import mozilla.components.feature.app.links.AppLinksInterceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRequestInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appPreferencesRepository: AppPreferencesRepository,
    private val qwantUseCases: QwantUseCases
) : RequestInterceptor {
    private val uaGlobal = context.UA
    private val uaQwant = context.UAQwant

    private val coroutineScope = MainScope()
    private var openLinksInApp = false

    init {
        coroutineScope.launch {
            appPreferencesRepository.flow
                .map { it.openLinksInApp }
                .distinctUntilChanged()
                .onEach { openLinksInApp = it }
                .collect()
        }
    }

    private val appLinksInterceptor = AppLinksInterceptor(
        context = context,
        interceptLinkClicks = true,
        launchInApp = { openLinksInApp },
        launchFromInterceptor = true
    )

    override fun onLoadRequest(
        engineSession: EngineSession,
        uri: String,
        lastUri: String?,
        hasUserGesture: Boolean,
        isSameDomain: Boolean,
        isRedirect: Boolean,
        isDirectNavigation: Boolean,
        isSubframeRequest: Boolean
    ): RequestInterceptor.InterceptionResponse? {
        if (uri.isQwantUrl()) {
            engineSession.settings.userAgentString = uaQwant
            if (!uri.isQwantUrlValid()) {
                return RequestInterceptor.InterceptionResponse.Url(
                    qwantUseCases.getQwantUrl(search = uri.getQwantSERPSearch())
                )
            }
        } else {
            engineSession.settings.userAgentString = uaGlobal
        }

        return appLinksInterceptor.onLoadRequest(
            engineSession, uri, lastUri, hasUserGesture, isSameDomain, isRedirect, isDirectNavigation,
            isSubframeRequest
        )
    }

    override fun onErrorRequest(
        session: EngineSession,
        errorType: ErrorType,
        uri: String?
    ): RequestInterceptor.ErrorResponse {
        val errorPage = ErrorPages.createUrlEncodedErrorPage(context, errorType, uri)
        return RequestInterceptor.ErrorResponse(errorPage)
    }

    override fun interceptsAppInitiatedRequests() = true
}
