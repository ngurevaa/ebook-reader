package ru.gureva.ebookreader.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.gureva.ebookreader.core.ui.noRippleClickable
import ru.gureva.ebookreader.feature.booklist.navigation.BookListRoute
import ru.gureva.ebookreader.feature.bookupload.navigation.BookUploadRoute
import ru.gureva.ebookreader.feature.profile.navigation.ProfileRoute

enum class BottomDestinations(
    val route: Any,
    val icon: ImageVector
) {
    BOOK_LIST(route = BookListRoute, icon = Icons.Filled.Book),
    BOOK_UPLOAD(route = BookUploadRoute, icon = Icons.Filled.Upload),
    PROFILE(route = ProfileRoute, icon = Icons.Filled.Home)
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route?.substringAfterLast('.')

    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
    ) {
        BottomDestinations.entries.forEach { destination ->
            val isSelected = currentRoute == destination.route.toString()

            NavigationBarItem(
                enabled = false,
                selected = false,
                onClick = {},
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = null,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.noRippleClickable {
                            if (!isSelected) {
                                navController.navigate(destination.route) {
                                    popUpTo(0)
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            )
        }
    }
}
