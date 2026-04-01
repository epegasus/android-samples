package com.sohaib.composenavigation.ui.navigation.route

sealed class Route(val route: String) {
    data object HomeScreen : Route("home")
    data object DetailScreen : Route("detail")
}