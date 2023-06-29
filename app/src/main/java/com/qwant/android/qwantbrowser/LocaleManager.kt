package com.qwant.android.qwantbrowser

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.core.content.ContextCompat.startActivity
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import mozilla.components.support.ktx.kotlinx.coroutines.flow.ifChanged
import java.util.*
import javax.inject.Inject

class LocaleManager @Inject constructor(
    @ApplicationContext val context: Context,
    frontEndPreferencesRepository: FrontEndPreferencesRepository
) {
    private val defaultInterfaceLanguage: String = Locale.getDefault().language
    private val interfaceLanguageFlow = frontEndPreferencesRepository.flow.map {
        it.interfaceLanguage.ifEmpty { defaultInterfaceLanguage }
    }

    suspend fun setLocale() {
        val locale = Locale(interfaceLanguageFlow.first())
        Locale.setDefault(locale)
        context.resources.configuration.locale = locale
        context.resources.updateConfiguration(context.resources.configuration, context.resources.displayMetrics)
    }

    suspend fun watchLocale() {
        val restartIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            action = "CHANGED_LANGUAGE"
        }

        interfaceLanguageFlow
            .ifChanged()
            .filter { it != context.resources.configuration.locale.language }
            .collect {
                startActivity(context, restartIntent, null)
            }
    }
}