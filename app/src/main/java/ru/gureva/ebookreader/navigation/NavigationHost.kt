package ru.gureva.ebookreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.gureva.ebookreader.feature.auth.navigation.AuthRoute
import ru.gureva.ebookreader.feature.auth.ui.AuthScreen

@Composable
fun NavigationHost(
    startDestination: Any,
    navController: NavHostController
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        composable<AuthRoute> {
            AuthScreen()
        }
    }
}
