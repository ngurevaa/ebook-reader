package ru.gureva.ebookreader.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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
import ru.gureva.ebookreader.feature.profile.navigation.ProfileRoute
import ru.gureva.ebookreader.feature.profile.presentation.ProfileScreen
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
        composable<LoginRoute>(
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            LoginScreen(
                navigateToRegistration = {
                    navController.navigate(RegistrationRoute) {
                        popUpTo(0)
                    }
                },
                navigateToBookList = {
                    navController.navigate(BookListRoute) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable<RegistrationRoute>(
            enterTransition = { fadeIn(animationSpec = tween(durationMillis = 500)) },
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 500)) }
        ) {
            RegistrationScreen(
                navigateToLogin = {
                    navController.navigate(LoginRoute) {
                        popUpTo(0)
                    }
                },
                navigateToBookList = {
                    navController.navigate(BookListRoute) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable<BookUploadRoute>(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            BookUploadScreen()
        }

        composable<BookListRoute>(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            BookListScreen(
                navigateToBook = { fileName, title ->
                    navController.navigate(ReaderRoute(fileName, title)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<ReaderRoute>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            }
        ) { backStackEntry ->
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

        composable<ProfileRoute>(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            ProfileScreen(
                navigateToLogin = {
                    navController.navigate(LoginRoute) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}
