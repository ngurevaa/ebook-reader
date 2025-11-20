package ru.gureva.ebookreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ru.gureva.ebookreader.core.designsystem.theme.AppTheme
import ru.gureva.ebookreader.feature.auth.navigation.LoginRoute
import ru.gureva.ebookreader.feature.auth.navigation.RegistrationRoute
import ru.gureva.ebookreader.feature.auth.presentation.login.LoginScreen
import ru.gureva.ebookreader.navigation.NavigationHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationHost(
                        startDestination = LoginRoute,
                        navController = navController
                    )
                }
            }
        }
    }
}
