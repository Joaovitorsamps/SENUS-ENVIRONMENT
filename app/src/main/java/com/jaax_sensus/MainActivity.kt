package com.jaax_sensus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.jaax_sensus.navigation.AppRoute
import com.jaax_sensus.ui.screens.ChatGuiaScreen
import com.jaax_sensus.ui.screens.DadosScreen
import com.jaax_sensus.ui.screens.DiarioScreen
import com.jaax_sensus.ui.screens.EmocoesScreen
import com.jaax_sensus.ui.screens.LoginScreen
import com.jaax_sensus.ui.theme.DeepNavyBlue
import com.jaax_sensus.ui.theme.GreyishDarkBlue
import com.jaax_sensus.ui.theme.JAAXSENSUSTheme
import com.jaax_sensus.ui.theme.LightGrey
import com.jaax_sensus.ui.theme.PrimaryBlue
import com.jaax_sensus.ui.theme.White
import com.jaax_sensus.ui.viewmodel.EmotionViewModel

private data class BottomNavItem(
    val route: AppRoute,
    val title: String,
    val icon: ImageVector
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JAAXSENSUSTheme {
                MainAppRoot()
            }
        }
    }
}

@Composable
fun MainAppRoot(
    emotionViewModel: EmotionViewModel = viewModel()
) {
    val isLoggedIn by emotionViewModel.isLoggedIn.collectAsState()

    AnimatedContent(
        targetState = isLoggedIn,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "AuthTransition"
    ) { loggedIn ->
        if (loggedIn) {
            MainAppScreen(emotionViewModel = emotionViewModel)
        } else {
            LoginScreen(
                onLoginSuccess = { username ->
                    emotionViewModel.login(username, "mock_password")
                }
            )
        }
    }
}

@Composable
fun MainAppScreen(
    emotionViewModel: EmotionViewModel
) {
    val backStack = rememberNavBackStack(AppRoute.Emocoes)
    val currentRoute = backStack.lastOrNull() ?: AppRoute.Emocoes
    val userName by emotionViewModel.userName.collectAsState()

    val navItems = listOf(
        BottomNavItem(
            route = AppRoute.Emocoes,
            title = "Emoções",
            icon = Icons.Default.GridView
        ),
        BottomNavItem(
            route = AppRoute.ChatGuia,
            title = "Chat Guia",
            icon = Icons.Default.ChatBubbleOutline
        ),
        BottomNavItem(
            route = AppRoute.Diario,
            title = "Diário",
            icon = Icons.AutoMirrored.Filled.MenuBook
        ),
        BottomNavItem(
            route = AppRoute.Dados,
            title = "Dados",
            icon = Icons.Default.BarChart
        )
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DeepNavyBlue,
        bottomBar = {
            NavigationBar(
                containerColor = GreyishDarkBlue,
                contentColor = White
            ) {
                navItems.forEach { item ->
                    val selected = currentRoute == item.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (currentRoute != item.route) {
                                if (item.route is AppRoute.Emocoes) {
                                    backStack.clear()
                                    backStack.add(AppRoute.Emocoes)
                                } else {
                                    backStack.clear()
                                    backStack.add(AppRoute.Emocoes)
                                    backStack.add(item.route)
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryBlue,
                            selectedTextColor = PrimaryBlue,
                            unselectedIconColor = LightGrey,
                            unselectedTextColor = LightGrey,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onBack = {
                if (backStack.size > 1) {
                    backStack.removeAt(backStack.lastIndex)
                }
            },
            entryProvider = entryProvider {
                entry<AppRoute.Emocoes> {
                    EmocoesScreen(
                        userName = userName,
                        onEmotionSelected = { emotion ->
                            emotionViewModel.selectEmotion(emotion)
                            backStack.clear()
                            backStack.add(AppRoute.Emocoes)
                            backStack.add(AppRoute.Diario)
                        },
                        onLogoutClick = {
                            emotionViewModel.logout()
                        }
                    )
                }
                entry<AppRoute.ChatGuia> {
                    ChatGuiaScreen(
                        userName = userName,
                        onLogoutClick = {
                            emotionViewModel.logout()
                        }
                    )
                }
                entry<AppRoute.Diario> {
                    DiarioScreen(
                        viewModel = emotionViewModel,
                        onLogoutClick = {
                            emotionViewModel.logout()
                        }
                    )
                }
                entry<AppRoute.Dados> {
                    DadosScreen(
                        viewModel = emotionViewModel,
                        onNavigateToEmocoes = {
                            backStack.clear()
                            backStack.add(AppRoute.Emocoes)
                        },
                        onLogoutClick = {
                            emotionViewModel.logout()
                        }
                    )
                }
            }
        )
    }
}