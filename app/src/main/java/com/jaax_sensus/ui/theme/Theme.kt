package com.jaax_sensus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = White,
    secondary = PrimaryBlue,
    onSecondary = White,
    background = DeepNavyBlue,
    onBackground = White,
    surface = GreyishDarkBlue,
    onSurface = White,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = LightGrey,
    outline = BorderDark,
    outlineVariant = GreyishDarkBlue,
    surfaceContainer = GreyishDarkBlue,
    surfaceContainerHigh = SurfaceVariantDark,
    surfaceContainerLowest = DeepNavyBlue
)

@Composable
fun JAAXSENSUSTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // App strictly runs in Dark Mode with custom theme colors
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}