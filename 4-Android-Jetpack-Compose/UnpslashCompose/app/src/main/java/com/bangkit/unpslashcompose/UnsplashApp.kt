package com.bangkit.unpslashcompose

import android.annotation.SuppressLint
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.unpslashcompose.ui.components.AppBar
import com.bangkit.unpslashcompose.ui.components.DrawerBody
import com.bangkit.unpslashcompose.ui.components.DrawerHeader
import com.bangkit.unpslashcompose.ui.components.MenuItem
import com.bangkit.unpslashcompose.ui.navigation.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UnsplashApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                navController = navController,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            DrawerHeader()
            DrawerBody(
                items = listOf(
                    MenuItem(
                        id = "home",
                        title = "Home",
                        contentDescription = "Go to home screen",
                        route = "home",
                        icon = Icons.Default.Home
                    ),
                    MenuItem(
                        id = "profile",
                        title = "Profile",
                        route = "profile",
                        contentDescription = "Go to profile screen",
                        icon = Icons.Default.Person
                    ),
                ),
                onItemClick = {
                    when (it.id) {
                        "home" -> navController.navigate(Screen.Home.route)
                        "profile" -> navController.navigate(Screen.Profile.route)
                    }
                }
            )
        }
    ) {

    }
}