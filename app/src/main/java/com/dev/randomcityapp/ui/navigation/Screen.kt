package com.dev.randomcityapp.ui.navigation


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Main : Screen("main")
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: Long) = "detail/$id"
    }
}
