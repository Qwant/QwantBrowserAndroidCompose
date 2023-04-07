package com.qwant.android.qwantbrowser


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.qwant.android.qwantbrowser.ext.core
import com.qwant.android.qwantbrowser.ext.useCases
import com.qwant.android.qwantbrowser.ui.QwantBrowserApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mozilla.components.feature.session.FullScreenFeature
import mozilla.components.support.base.feature.ViewBoundFeatureWrapper
import mozilla.components.support.ktx.android.view.enterToImmersiveMode
import mozilla.components.support.ktx.android.view.exitImmersiveMode
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var localeManager: LocaleManager

    private val fullScreenFeature = ViewBoundFeatureWrapper<FullScreenFeature>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        runBlocking { localeManager.setLocale(this@MainActivity.resources) }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                localeManager.watchLocale(this@MainActivity)
            }
        }

        setContent {
            QwantBrowserApp(intent.action)
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val view = super.onCreateView(name, context, attrs)

        view?.let {
            fullScreenFeature.set(
                feature = FullScreenFeature(
                    context.core.store,
                    context.useCases.sessionUseCases,
                    viewportFitChanged = ::viewportFitChanged,
                    fullScreenChanged = ::fullScreenChanged),
                owner = this,
                view = it
            )
        }

        return view
    }

    private fun fullScreenChanged(enabled: Boolean) {
        if (enabled) {
            this.enterToImmersiveMode()
            // toolbar.visibility = View.GONE
            // engineView.setDynamicToolbarMaxHeight(0)
        } else {
            this.exitImmersiveMode()
            // toolbar.visibility = View.VISIBLE
            // engineView.setDynamicToolbarMaxHeight(resources.getDimensionPixelSize(R.dimen.browser_toolbar_height))
        }
    }

    private fun viewportFitChanged(viewportFit: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            this.window.attributes.layoutInDisplayCutoutMode = viewportFit
        }
    }
}
