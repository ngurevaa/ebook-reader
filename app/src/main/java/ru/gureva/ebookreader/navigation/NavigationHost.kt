package ru.gureva.ebookreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.gureva.ebookreader.feature.auth.navigation.LoginRoute
import ru.gureva.ebookreader.feature.auth.navigation.RegistrationRoute
import ru.gureva.ebookreader.feature.auth.presentation.login.LoginScreen
import ru.gureva.ebookreader.feature.auth.presentation.registration.RegistrationScreen
import ru.gureva.ebookreader.feature.booklist.navigation.BookListRoute
import ru.gureva.ebookreader.feature.booklist.presentation.BookListScreen
import ru.gureva.ebookreader.feature.bookupload.navigation.BookUploadRoute
import ru.gureva.ebookreader.feature.bookupload.presentation.BookUploadScreen
import ru.gureva.ebookreader.feature.reader.navigation.ReaderRoute
import ru.gureva.ebookreader.feature.reader.presentation.ui.ReaderScreen

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
            BookListScreen(
                navigateToBook = { fileName, title ->
                    navController.navigate(ReaderRoute(fileName, title)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<ReaderRoute> { backStackEntry ->
            val fileName = backStackEntry.toRoute<ReaderRoute>().fileName
            val title = backStackEntry.toRoute<ReaderRoute>().title
            ReaderScreen(
                fileName = fileName,
                title = title,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
