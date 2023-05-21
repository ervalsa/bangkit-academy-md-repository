package com.bangkit.unpslashcompose.ui.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object Profile : Screen("profile")

    object DetailPhoto : Screen("home/{photoId}") {
        fun createRoute(photoId: Long) = "home/${photoId}"
    }
}