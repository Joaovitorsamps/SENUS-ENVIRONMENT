package com.jaax_sensus.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SensusColorScheme = darkColorScheme(
    primary = SensusTerracotta,
    onPrimary = SensusWarmCream,
    secondary = SensusMintGreen,
    onSecondary = SensusDarkTaupe,
    background = SensusSageTeal,
    onBackground = SensusDarkTaupe,
    surface = SensusWarmCream,
    onSurface = SensusDarkTaupe,
    surfaceVariant = SensusCreamLight,
    onSurfaceVariant = SensusTaupeMuted,
    outline = SensusCreamDark,
    outlineVariant = SensusMintGreen,
    surfaceContainer = SensusWarmCream,
    surfaceContainerHigh = SensusCreamLight,
    surfaceContainerLowest = SensusSageTeal
)

@Composable
fun JAAXSENSUSTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // App strictly runs in Dark Mode with custom theme colors
    val colorScheme = SensusColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}