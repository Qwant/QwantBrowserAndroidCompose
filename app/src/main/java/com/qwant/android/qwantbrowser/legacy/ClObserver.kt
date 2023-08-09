package com.qwant.android.qwantbrowser.legacy

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.preference.PreferenceManager
import com.qwant.android.qwantbrowser.ext.core
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.ui.browser.mozaccompose.ComposeFeatureWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.feature.session.SessionFeature
import mozilla.components.lib.state.ext.flowScoped
import mozilla.components.support.base.feature.LifecycleAwareFeature
import mozilla.components.support.ktx.kotlinx.coroutines.flow.ifChanged


// TODO move cl to internal datastore
class ClObserver(
    context: Context,
): LifecycleAwareFeature {
    private var scope: CoroutineScope? = null
    private val store = context.core.store

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val prefKeyCl = "pref_key_utm_campaign"
    private val prefKeyClTimestamp = "pref_key_utm_campaign_todo"

    override fun start() {
        if (prefs.getString(prefKeyCl, null) == null) {
            scope = store.flowScoped { flow -> flow
                .mapNotNull { state -> state.selectedTab?.content?.url }
                .ifChanged()
                .filter { it.isQwantUrl() && it.contains("cl=") }
                .onEach { url ->
                    url.findCl()?.let {
                        with (prefs.edit()) {
                            putString(prefKeyCl, it)
                            putLong(prefKeyClTimestamp, System.currentTimeMillis())
                            apply()
                        }
                        scope?.cancel()
                    }
                }
                .collect()
            }
        }
    }

    override fun stop() {
        scope?.cancel()
    }


    private fun String.findCl(): String? {
        val split = this.split("&cl=", "?cl=")
        if (split.size == 2) {
            return split[1].substringBefore('&')
        }
        return null
    }
}

@Composable
fun ClFeature() {
    val context = LocalContext.current
    ComposeFeatureWrapper(feature = remember(context) {
        ClObserver(context)
    })
}