package com.qwant.android.qwantbrowser.piwik

import android.content.Context
import com.qwant.android.qwantbrowser.BuildConfig
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
    )).apply {
        android.util.Log.d("QWANT_PIWIK", "new piwik tracker")
        this.dispatchInterval = 0
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
        android.util.Log.d("QWANT_PIWIK", "track app download")
        TrackHelper.track().sendApplicationDownload().with(tracker)
    }

    fun event(
        category: String,
        action: String,
        name: String? = null
    ) {
        android.util.Log.d("QWANT_PIWIK", "track event $category - $action - $name")
        val builder = TrackHelper.track().event(category, action)
        name?.let { builder.name(it) }
        builder.with(tracker)
    }
}