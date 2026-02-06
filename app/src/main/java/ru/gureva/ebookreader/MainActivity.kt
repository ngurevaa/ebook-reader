package ru.gureva.ebookreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ru.gureva.ebookreader.core.designsystem.theme.AppTheme
import ru.gureva.ebookreader.feature.auth.navigation.LoginRoute
import ru.gureva.ebookreader.feature.booklist.navigation.BookListRoute
import ru.gureva.ebookreader.navigation.BottomDestinations
import ru.gureva.ebookreader.navigation.BottomNavigationBar
import ru.gureva.ebookreader.navigation.NavigationHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                val currentDestination = navController.currentBackStackEntryAsState().value?.destination
                val currentRouteSimpleName = currentDestination?.route?.substringAfterLast('.')

                val bottomBarDestinations = listOf(
                    BottomDestinations.BOOK_LIST.route.toString(),
                    BottomDestinations.BOOK_UPLOAD.route.toString(),
                    BottomDestinations.PROFILE.route.toString()
                )

                val showBottomBar = currentRouteSimpleName?.let { route ->
                    bottomBarDestinations.any { it == route }
                } == true

                val startDestination = if (Firebase.auth.currentUser == null) LoginRoute
                else BookListRoute

                Scaffold(
                    bottomBar = { if (showBottomBar) BottomNavigationBar(navController) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        NavigationHost(
                            startDestination = startDestination,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    AppTheme {
        Scaffold(
            bottomBar = {
                Surface {
                    NavigationBar(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    ) {
                        BottomDestinations.entries.forEach { destination ->
                            NavigationBarItem(
                                enabled = false,
                                selected = false,
                                onClick = {},
                                icon = {
                                    Icon(
                                        imageVector = destination.icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            )
                            NavigationBarItem(
                                enabled = false,
                                selected = false,
                                onClick = {},
                                icon = {
                                    Icon(
                                        imageVector = destination.icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.secondary,
                                    )
                                }
                            )
                        }
                    }
                }

            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {

            }
        }
    }
}
