package com.sohaib.composenavigation.ui.navigation.route

sealed class Route(val route: String) {

    object HomeScreen : Route("home")

    object DetailScreen : Route("detail/{message}") {
        const val ARG_MESSAGE = "message"
        fun createRoute(message: String): String = "detail/$message"
    }
}