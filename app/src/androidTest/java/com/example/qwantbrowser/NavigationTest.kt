package com.example.qwantbrowser

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Before
    fun setupQwantNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            // QwantNavHost(navController = navController, store = LocalContext.current.components.core.store)
        }
    }

    @Test
    fun qwantNavHost_startDestination() {
        composeTestRule
            .onNodeWithText("Browser")
            .assertIsDisplayed()
    }

    @Test
    fun qwantNavHost_forcedFail() {
        fail()
    }
}