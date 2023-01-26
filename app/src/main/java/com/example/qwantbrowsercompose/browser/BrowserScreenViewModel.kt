package com.example.qwantbrowsercompose.browser

import androidx.lifecycle.ViewModel
import com.example.qwantbrowsercompose.mozac.Core
import com.example.qwantbrowsercompose.mozac.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BrowserScreenViewModel @Inject constructor(
    val mozac: Core,
    val useCases: UseCases
): ViewModel()