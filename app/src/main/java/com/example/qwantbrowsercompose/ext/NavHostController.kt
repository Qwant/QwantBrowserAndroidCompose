package com.example.qwantbrowsercompose.ext

import androidx.navigation.NavHostController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }