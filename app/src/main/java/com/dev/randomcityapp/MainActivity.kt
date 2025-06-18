package com.dev.randomcityapp

import com.dev.randomcityapp.ui.navigation.Screen
import com.dev.randomcityapp.ui.screen.SplashScreen
import com.dev.randomcityapp.ui.theme.RandomCityAppTheme


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.*
import com.dev.randomcityapp.ui.screen.MainAndDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomCityAppTheme  {
                val nav = rememberNavController()
                NavHost(navController = nav, startDestination = Screen.Splash.route) {
                    composable(Screen.Splash.route) { SplashScreen(nav) }

                    // Single composable handles both Main + Detail (depending on screen size + route)
                    composable(Screen.Main.route) {
                        MainAndDetailScreen(nav, it)
                    }

                    composable(Screen.Detail.route) { backStack ->
                        MainAndDetailScreen(nav, backStack)
                    }
                }
            }
        }
    }
}

