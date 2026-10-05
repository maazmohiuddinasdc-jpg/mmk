package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = MMBluePrimary,
    onPrimary = Color.White,
    primaryContainer = MMBlueSubtle,
    onPrimaryContainer = MMBlueDark,
    secondary = MMGoldAccent,
    onSecondary = Color.Black,
    secondaryContainer = MMGoldLight,
    onSecondaryContainer = Color(0xFF7A4A00),
    tertiary = MMBlueLight,
    onTertiary = Color.White,
    background = MMBackground,
    onBackground = MMTextPrimary,
    surface = MMSurface,
    onSurface = MMTextPrimary,
    surfaceVariant = MMSurfaceVariant,
    onSurfaceVariant = MMTextSecondary,
    outline = MMBorder,
    outlineVariant = MMBorderSubtle,
    error = MMRedAlert,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6DA4FE),
    onPrimary = Color(0xFF002C71),
    primaryContainer = Color(0xFF003F9E),
    onPrimaryContainer = Color(0xFFD6E3FF),
    secondary = MMGoldAccent,
    onSecondary = Color.Black,
    background = Color(0xFF0B132B),
    onBackground = Color(0xFFE2E8F0),
    surface = Color(0xFF141F3B),
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = Color(0xFF1C2B4F),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF334155),
    error = Color(0xFFFFB4AB)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep MMKART's branded cobalt blue & white look consistent
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
