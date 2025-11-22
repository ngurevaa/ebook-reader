package ru.gureva.ebookreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.gureva.ebookreader.feature.auth.navigation.LoginRoute
import ru.gureva.ebookreader.feature.auth.navigation.RegistrationRoute
import ru.gureva.ebookreader.feature.auth.presentation.login.LoginScreen
import ru.gureva.ebookreader.feature.auth.presentation.registration.RegistrationScreen
import ru.gureva.ebookreader.feature.booklist.navigation.BookListRoute
import ru.gureva.ebookreader.feature.booklist.presentation.BookListScreen
import ru.gureva.ebookreader.feature.bookupload.navigation.BookUploadRoute
import ru.gureva.ebookreader.feature.bookupload.presentation.BookUploadScreen

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
            LoginScreen(
                navigateToRegistration = {
                    navController.navigate(RegistrationRoute) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable<RegistrationRoute> {
            RegistrationScreen(
                navigateToLogin = {
                    navController.navigate(LoginRoute) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable<BookUploadRoute> {
            BookUploadScreen()
        }

        composable<BookListRoute> {
            BookListScreen()
        }
    }
}
