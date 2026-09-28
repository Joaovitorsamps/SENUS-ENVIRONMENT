package com.jaax_sensus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BarChart
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.jaax_sensus.R
import com.jaax_sensus.navigation.AppRoute
import com.jaax_sensus.ui.screens.DadosScreen
import com.jaax_sensus.ui.screens.DiarioScreen
import com.jaax_sensus.ui.screens.EditarPerfilScreen
import com.jaax_sensus.ui.screens.EmocoesScreen
import com.jaax_sensus.ui.screens.LoginScreen
import com.jaax_sensus.ui.screens.RegisterScreen
import com.jaax_sensus.ui.theme.JAAXSENSUSTheme
import com.jaax_sensus.ui.theme.SensusDarkTaupe
import com.jaax_sensus.ui.theme.SensusMintGreen
import com.jaax_sensus.ui.theme.SensusSageTeal
import com.jaax_sensus.ui.theme.SensusTaupeDark
import com.jaax_sensus.ui.theme.SensusTerracotta
import com.jaax_sensus.ui.theme.SensusWarmCream
import com.jaax_sensus.ui.viewmodel.EmotionViewModel

private data class BottomNavItem(
    val route: AppRoute,
    val title: String,
    val icon: ImageVector
)

private enum class BackgroundSection(val alignment: Alignment) {
    LEFT(Alignment.CenterStart),
    CENTER(Alignment.Center),
    RIGHT(Alignment.CenterEnd)
}

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
    var showRegister by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    AnimatedContent(
        targetState = isLoggedIn,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "AuthTransition"
    ) { loggedIn ->
        if (loggedIn) {
            MainAppScreen(emotionViewModel = emotionViewModel)
        } else if (showRegister) {
            RegisterScreen(
                isLoading = emotionViewModel.isLoading.collectAsState().value,
                errorMessage = emotionViewModel.errorMessage.collectAsState().value,
                onRegister = emotionViewModel::register,
                onBackToLogin = {
                    emotionViewModel.clearError()
                    showRegister = false
                }
            )
        } else {
            LoginScreen(
                isLoading = emotionViewModel.isLoading.collectAsState().value,
                errorMessage = emotionViewModel.errorMessage.collectAsState().value,
                onLogin = { username, password ->
                    emotionViewModel.login(username, password)
                },
                onRegisterClick = {
                    emotionViewModel.clearError()
                    showRegister = true
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
    val backgroundSection = when (currentRoute) {
        AppRoute.Emocoes -> BackgroundSection.LEFT
        AppRoute.Diario -> BackgroundSection.CENTER
        AppRoute.Dados -> BackgroundSection.RIGHT
        AppRoute.EditarPerfil -> BackgroundSection.CENTER
        else -> BackgroundSection.CENTER
    }

    val navItems = listOf(
        BottomNavItem(
            route = AppRoute.Emocoes,
            title = "Emoções",
            icon = Icons.Default.GridView
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
        containerColor = Color.Transparent,
        bottomBar = {
            NavigationBar(
                containerColor = SensusDarkTaupe,
                contentColor = SensusWarmCream
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
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SensusWarmCream,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (selected) {
                                                SensusTerracotta
                                            } else {
                                                SensusTaupeDark.copy(alpha = 0.96f)
                                            }
                                        )
                                        .padding(horizontal = 7.dp, vertical = 2.dp)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SensusWarmCream,
                            selectedTextColor = SensusWarmCream,
                            unselectedIconColor = SensusWarmCream.copy(alpha = 0.9f),
                            unselectedTextColor = SensusWarmCream.copy(alpha = 0.9f),
                            indicatorColor = SensusTerracotta
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            AnimatedContent(
                targetState = backgroundSection,
                transitionSpec = {
                    slideInHorizontally { width -> width } togetherWith
                        slideOutHorizontally { width -> -width }
                },
                label = "BackgroundTransition"
            ) { section ->
                Image(
                    painter = painterResource(id = R.drawable.bg_sensus_nature),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    alignment = section.alignment,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(4.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SensusSageTeal.copy(alpha = 0.18f))
            )
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
                transitionSpec = {
                    slideInHorizontally { width -> width } togetherWith
                        slideOutHorizontally { width -> -width }
                },
                popTransitionSpec = {
                    slideInHorizontally { width -> -width } togetherWith
                        slideOutHorizontally { width -> width }
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
                        onEditProfile = {
                            backStack.add(AppRoute.EditarPerfil)
                        },
                        onLogoutClick = {
                            emotionViewModel.logout()
                        }
                    )
                }
                entry<AppRoute.EditarPerfil> {
                    EditarPerfilScreen(
                        initialName = userName,
                        isLoading = emotionViewModel.isLoading.collectAsState().value,
                        errorMessage = emotionViewModel.errorMessage.collectAsState().value,
                        onSave = emotionViewModel::updateProfile,
                        onBack = {
                            emotionViewModel.clearError()
                            if (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
                        }
                    )
                }
                }
            )
        }
    }
}