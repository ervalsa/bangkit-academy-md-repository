package com.bangkit.unpslashcompose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit.unpslashcompose.ui.navigation.BottomBar
import com.bangkit.unpslashcompose.ui.navigation.Screen
import com.bangkit.unpslashcompose.ui.screen.detail.DetailScreen
import com.bangkit.unpslashcompose.ui.screen.home.HomeScreen
import com.bangkit.unpslashcompose.ui.screen.profile.ProfileScreen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UnsplashApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (currentRoute != Screen.DetailPhoto.route) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { photoId ->
                        navController.navigate(Screen.DetailPhoto.createRoute(photoId))
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            composable(
                route = Screen.DetailPhoto.route,
                arguments = listOf(navArgument("photoId") { type = NavType.LongType })
            ) {
                val id = it.arguments?.getLong("photoId") ?: -1L
                DetailScreen(
                    photoId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}