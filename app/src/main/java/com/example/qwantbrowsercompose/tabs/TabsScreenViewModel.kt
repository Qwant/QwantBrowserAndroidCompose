package com.example.qwantbrowsercompose.tabs

import androidx.lifecycle.ViewModel
import com.example.qwantbrowsercompose.mozac.Core
import com.example.qwantbrowsercompose.mozac.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TabsScreenViewModel @Inject constructor(
    val mozac: Core,
    val useCases: UseCases
): ViewModel()