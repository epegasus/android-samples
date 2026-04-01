package com.sohaib.composenavigation.ui.manual

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sohaib.composenavigation.ui.screens.DetailScreen
import com.sohaib.composenavigation.ui.screens.HomeScreen

@Composable
fun ManualNavigation(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf("home") }

    when (currentScreen) {
        "home" -> HomeScreen(
            modifier,
            goToDetail = { currentScreen = "detail" }
        )

        "detail" -> DetailScreen(
            modifier,
            navigateBack = { currentScreen = "home" }
        )
    }
}