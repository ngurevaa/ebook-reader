package ru.gureva.ebookreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.gureva.ebookreader.feature.auth.navigation.LoginRoute
import ru.gureva.ebookreader.feature.auth.presentation.login.LoginScreen

@Composable
fun NavigationHost(
    startDestination: Any,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        composable<LoginRoute> {
            LoginScreen()
        }
    }
}
