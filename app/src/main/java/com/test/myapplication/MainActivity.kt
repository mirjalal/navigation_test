package com.test.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.test.myapplication.ui.theme.NavigationTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationTestTheme {
                val navController = rememberNavController()
                val bottomNavItems = listOf(
                    BottomMenu.Home,
                    BottomMenu.Services,
                )

                Scaffold(
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route
                            bottomNavItems.forEach { screen ->
                                val isSelected = currentRoute == screen.route
                                val screenTitle = screen.label
                                BottomNavigationItem(
                                  //  modifier = Modifier.background(if (isSelected) Color(0xFF005EA8) else MaterialTheme.colors.primary),
                                    icon = {
                                        Icon(
                                            painterResource(screen.icon),
                                            contentDescription = screenTitle
                                        )
                                    },
                                    label = { Text(screenTitle) },
                                    selected = isSelected,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.startDestinationId) {
                                                // read: https://issuetracker.google.com/issues/80029773#comment136
                                                saveState = true
                                            }

                                            // Avoid multiple copies of the same destination when
                                            // re-selecting the same item
                                            launchSingleTop = true

                                            // read: https://issuetracker.google.com/issues/80029773#comment136
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    },
                ) {
                    // Vertical scroller is a composable that adds the ability to scroll through the
                    // child views.
                    SetupNavHost(navController)
                }
            }
        }
    }

    @Composable
    private fun SetupNavHost(
        navController: NavHostController
    ) {
        NavHost(navController = navController, startDestination = BottomMenu.ROUTE_HOME) {
            composable(BottomMenu.ROUTE_HOME) {
                HomeScreen(navController)
            }

            composable(BottomMenu.ROUTE_SERVICES) {
                ServicesScreen(navController)
            }

            composable("route_2") {
                SubServicesScreen(navController, navController.getParcelable(SERVICE_ROUTE_PARAM))
            }
            composable("route_2_1") {
                Text(text = "the route_2_1 screen")
            }
            composable("route_2_2") {
                Text(text = "the route_2_2 screen")
            }

            composable("route_3") {
                SubServicesScreen(navController, navController.getParcelable(SERVICE_ROUTE_PARAM))
            }
            composable("route_3_1") {
                Text(text = "the route_3_1 screen")
            }
            composable("route_3_2") {
                Text(text = "the route_3_2 screen")
            }
        }
    }
}

sealed class BottomMenu(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int
) {
    companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_SERVICES = "services"
    }

    object Home : BottomMenu(ROUTE_HOME, "Home", android.R.drawable.ic_menu_add)
    object Services : BottomMenu(ROUTE_SERVICES, "Services", android.R.drawable.ic_delete)
}
