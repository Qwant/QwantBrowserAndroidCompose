package com.qwant.android.qwantbrowser.widget

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qwant.android.qwantbrowser.ext.getQwantSERPCategory
import com.qwant.android.qwantbrowser.stats.Datahub
import com.qwant.android.qwantbrowser.suggest.providers.ClipboardProvider
import com.qwant.android.qwantbrowser.suggest.providers.DomainProvider
import com.qwant.android.qwantbrowser.suggest.providers.QwantSuggestProvider
import com.qwant.android.qwantbrowser.ui.browser.toolbar.ToolbarState
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.engine.EngineSession
import mozilla.components.concept.engine.permission.PermissionRequest
import javax.inject.Inject

@HiltViewModel
class WidgetViewModel @Inject constructor(
    val engine: Engine,
    val datahub: Datahub,
    val qwantUseCases: QwantUseCases,
    clipboardProvider: ClipboardProvider,
    qwantSuggestProvider: QwantSuggestProvider,
    domainProvider: DomainProvider,
    browserIcons: BrowserIcons,
)  : ViewModel() {
    val toolbarState: ToolbarState = ToolbarState(
        browserIcons, datahub, listOf(clipboardProvider, qwantSuggestProvider, domainProvider), viewModelScope
    )

    var currentCategory: String? by mutableStateOf(null)

    val session = engine.createSession().also {
        it.register(object: EngineSession.Observer {
            override fun onLocationChange(url: String, hasUserGesture: Boolean) {
                currentCategory = url.getQwantSERPCategory()
            }
            override fun onAppPermissionRequest(permissionRequest: PermissionRequest) = Unit
            override fun onContentPermissionRequest(permissionRequest: PermissionRequest) = Unit
        })
    }

    var currentSearch: String? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            snapshotFlow { currentSearch }
                .distinctUntilChanged()
                .filterNotNull()
                .onEach { search ->
                    session.loadUrl(qwantUseCases.getQwantUrl(null, search, currentCategory, true))
                }
                .collect()
        }
    }

    fun commitSearch() {
        currentSearch = toolbarState.text.text
    }

    fun resetSearch() {
        toolbarState.updateText("")
        toolbarState.updateFocus(true)
        currentSearch = null
        currentCategory = null
    }
}