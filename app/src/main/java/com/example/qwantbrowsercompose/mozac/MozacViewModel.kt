package com.example.qwantbrowsercompose.mozac

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/*
    SHOULD NOT BE USED, EXCEPT FOR INSTANTIATING OTHER MOZILLA COMPONENTS.
    TO USE IN QWANT COMPOSE VIEWS, ABSTRACT THIS BEHIND A VIEWMODEL
 */

/* @Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
annotation class MozacDirectUsage

@MozacDirectUsage
// propagates too much :(
*/

@HiltViewModel
class MozacViewModel @Inject constructor(
    val mozac: Core,
    val useCases: UseCases
): ViewModel()