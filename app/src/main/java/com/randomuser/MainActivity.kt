package com.randomuser

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.randomuser.navigation.Destinations
import com.randomuser.ui.composables.UserDetailScreen
import com.randomuser.ui.composables.UsersListScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                NavigationComponent(navController)
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(
            navController = navController,
            startDestination = Destinations.USER_LIST.destination
    ) {
        composable(Destinations.USER_LIST.destination) {
            UsersListScreen(navController)
        }
        composable(Destinations.USER_DETAIL.destination + "/{username}",
                arguments = listOf(navArgument("username") { type = NavType.StringType })) { backStackEntry ->
            UserDetailScreen(backStackEntry.arguments?.getString("username")!!)
        }
    }
}
