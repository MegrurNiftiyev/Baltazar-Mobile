//package com.example.baltazar.ui.components
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material3.Icon
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.navigation.NavController
//import androidx.navigation.NavDestination.Companion.hasRoute
//import androidx.navigation.NavGraph.Companion.findStartDestination
//import androidx.navigation.compose.currentBackStackEntryAsState
//import com.example.baltazar.core.core.navigation.Explore
//import com.example.baltazar.core.core.navigation.Profile
//import com.example.baltazar.core.core.navigation.Wishlist
//
//@Composable
//fun BottomNavBar(navController: NavController) {
//    val backStack by navController.currentBackStackEntryAsState()
//    val currentDestination = backStack?.destination
//
//    NavigationBar {
//        NavigationBarItem(
//            selected = currentDestination?.hasRoute<Explore>() == true,
//            onClick = { navController.navigateToTab(Explore) },
//            icon = { Icon(Icons.Default.Explore, contentDescription = "Explore") },
//            label = { Text("Explore") }
//        )
//        NavigationBarItem(
//            selected = currentDestination?.hasRoute<Wishlist>() == true,
//            onClick = { navController.navigateToTab(Wishlist) },
//            icon = { Icon(Icons.Default.FavoriteBorder, contentDescription = "Wishlist") },
//            label = { Text("Wishlist") }
//        )
//        NavigationBarItem(
//            selected = currentDestination?.hasRoute<Profile>() == true,
//            onClick = { navController.navigateToTab(Profile) },
//            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
//            label = { Text("Profile") }
//        )
//    }
//}
//
//fun NavController.navigateToTab(route: Any) {
//    navigate(route) {
//        popUpTo(graph.findStartDestination().id) {
//            saveState = true
//        }
//        launchSingleTop = true
//        restoreState = true
//    }
//}
