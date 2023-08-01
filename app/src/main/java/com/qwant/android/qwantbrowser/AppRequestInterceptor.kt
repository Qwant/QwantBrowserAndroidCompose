package com.qwant.android.qwantbrowser

import android.content.Context
import android.util.Log
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mozilla.components.browser.errorpages.ErrorPages
import mozilla.components.browser.errorpages.ErrorType
import mozilla.components.concept.engine.EngineSession
import mozilla.components.concept.engine.request.RequestInterceptor
import mozilla.components.feature.app.links.AppLinksInterceptor
import mozilla.components.support.ktx.kotlinx.coroutines.flow.ifChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OptIn(DelicateCoroutinesApi::class)
class AppRequestInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appPreferencesRepository: AppPreferencesRepository
) : RequestInterceptor {
    // TODO centralise ua
    val uaBase = "Mozilla/5.0 (Android 10; Mobile; rv:115.0) Gecko/115.0 Firefox/115.0" // context.getString(R.string.qwant_base_useragent)
    val uaExt = "$uaBase QwantMobile/4.2" // context.getString(R.string.qwant_useragent_ext)

    private var openLinksInApp = false

    init {
        // TODO don't use globalscope ! create dedicated scope instead
        GlobalScope.launch {
            appPreferencesRepository.flow
                .map { it.openLinksInApp }
                .ifChanged()
                .onEach { openLinksInApp = it }
                .collect()
        }
        GlobalScope.launch {
            // TODO get current qwant url
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
        // val isFlight = uri.startsWith(context.getString(R.string.qwantflight_startwith_filter))
        // uri.startsWith(context.getString(R.string.qwantmaps_result_startwith_filter))
        // uri.startsWith(context.getString(R.string.qwantmaps_startwith_filter))
        // if (uri.startsWith(context.getString(R.string.homepage_startwith_filter)) && !isFlight) {
        // TODO exclude flight and maps from "isQwantUrl"
        if (uri.isQwantUrl()) {
            Log.d("QB_INTERCEPT", "$uri is qwant")
            engineSession.settings.userAgentString = uaExt

            if (uri.indexOf("&qbc=1") == -1/* && !uri.startsWith(context.getString(R.string.qwantmaps_result_startwith_filter)) */) {
                // TODO move this to extension "getQwantSearch"
                var searchStart = uri.indexOf("&q=")
                if (searchStart == -1) searchStart = uri.indexOf("?q=")
                val searchTerms = if (searchStart != -1) {
                    var searchEnd = uri.indexOf('&', searchStart + 3)
                    if (searchEnd == -1) searchEnd = uri.length
                    uri.substring(searchStart + 3, searchEnd)
                } else null

                Log.d("QB_INTERCEPT", "should redirect qwant link")
                // TODO redirect adding qwant settings
                // val isMaps = uri.startsWith(context.getString(R.string.qwantmaps_startwith_filter))
                // val redirectUri = QwantUtils.getHomepage(context, query = searchTerms, maps = isMaps)
                // return RequestInterceptor.InterceptionResponse.Url(redirectUri)
            }
        } else if (engineSession.settings.userAgentString?.endsWith(" QwantMobile/4.2") == true) {
            engineSession.settings.userAgentString = uaBase
        }

        Log.d("QB_INTERCEPT", "pass to applinks interceptor")
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
