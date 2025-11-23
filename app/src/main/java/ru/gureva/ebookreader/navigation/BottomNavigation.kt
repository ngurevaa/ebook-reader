package ru.gureva.ebookreader.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.gureva.ebookreader.feature.booklist.navigation.BookListRoute
import ru.gureva.ebookreader.feature.bookupload.navigation.BookUploadRoute

enum class BottomDestinations(
    val route: Any,
    val icon: ImageVector
) {
    BOOK_LIST(route = BookListRoute, icon = Icons.Filled.Book),
    BOOK_UPLOAD(route = BookUploadRoute, icon = Icons.Filled.Upload),
    PROFILE(route = "", icon = Icons.Filled.Home)
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route?.substringAfterLast('.')

    NavigationBar {
        BottomDestinations.entries.forEach { destination ->
            val isSelected = currentRoute == destination.route.toString()

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(destination.route) {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = null
                    )
                }
            )
        }
    }
}
