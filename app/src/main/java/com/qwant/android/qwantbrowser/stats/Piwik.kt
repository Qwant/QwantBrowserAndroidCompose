package com.qwant.android.qwantbrowser.stats

import android.content.Context
import com.qwant.android.qwantbrowser.BuildConfig
import com.qwant.android.qwantbrowser.ext.isQwantSERPUrl
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.piwik.sdk.Piwik
import pro.piwik.sdk.TrackerConfig
import pro.piwik.sdk.extra.TrackHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Piwik @Inject constructor(
    @ApplicationContext context: Context,
    appPreferencesRepository: AppPreferencesRepository,
) {
    private val coroutineScope = MainScope()

    private val tracker = Piwik.getInstance(context).newTracker(TrackerConfig(
        BuildConfig.PIWIK_URL,
        BuildConfig.PIWIK_SITE_ID,
        "default"
    )).also {
        it.setAnonymizationState(false)
        it.visitorIDLifetime = (((365.25 / 12) * 13) * (24 * 60 * 60.0 * 1000)).toLong() // 13 months in milliseconds // 5 * 60 * 1000 // 5 minutes in milliseconds //
        it.dispatchInterval = 0
        it.isPrefixing = false
    }

    init {
        coroutineScope.launch {
            appPreferencesRepository.flow
                .map { it.piwikOptout }
                .collect {
                    tracker.isOptOut = it
                }
        }
    }

    fun trackApplicationDownload() {
        TrackHelper.track().sendApplicationDownload().with(tracker)
    }

    fun event(
        category: String,
        action: String,
        name: String? = null
    ) {
        val builder = TrackHelper.track().event(category, action)
        name?.let { builder.name(it) }
        builder.with(tracker)
    }

    fun screenView(url: String) {
        val screen = if (url.isQwantUrl()) {
            if (url.isQwantSERPUrl()) "SERP navigation"
            else "HP navigation"
        } else "Web navigation"
        TrackHelper.track().screens(listOf(screen)).with(tracker);
    }
}