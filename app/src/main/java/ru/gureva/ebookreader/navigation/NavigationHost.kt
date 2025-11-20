package ru.gureva.ebookreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.gureva.ebookreader.feature.auth.navigation.LoginRoute
import ru.gureva.ebookreader.feature.auth.navigation.RegistrationRoute
import ru.gureva.ebookreader.feature.auth.presentation.login.LoginScreen
import ru.gureva.ebookreader.feature.auth.presentation.registration.RegistrationScreen

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

        composable<RegistrationRoute> {
            RegistrationScreen()
        }
    }
}
