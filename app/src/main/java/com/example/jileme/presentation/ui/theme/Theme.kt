package com.example.jileme.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8BD4C2),
    onPrimary = Color(0xFF00382C),
    primaryContainer = Color(0xFF00513F),
    onPrimaryContainer = Color(0xFFA8F0DC),
    secondary = Color(0xFFB0CCC3),
    onSecondary = Color(0xFF1C3530),
    secondaryContainer = Color(0xFF334B45),
    onSecondaryContainer = Color(0xFFCBE8DE),
    tertiary = Color(0xFFA5CDDF),
    onTertiary = Color(0xFF063544),
    tertiaryContainer = Color(0xFF234C5C),
    onTertiaryContainer = Color(0xFFC1E8FB),
    background = Color(0xFF101412),
    onBackground = Color(0xFFE0E3E0),
    surface = Color(0xFF101412),
    onSurface = Color(0xFFE0E3E0),
    surfaceVariant = Color(0xFF3F4946),
    onSurfaceVariant = Color(0xFFBFC9C5),
    outline = Color(0xFF89938F),
    outlineVariant = Color(0xFF3F4946)
)

private val LightColorScheme = lightColorScheme(
    primary = SeedPrimary,
    onPrimary = SeedOnPrimary,
    primaryContainer = SeedPrimaryContainer,
    onPrimaryContainer = SeedOnPrimaryContainer,
    secondary = SeedSecondary,
    onSecondary = SeedOnSecondary,
    secondaryContainer = SeedSecondaryContainer,
    onSecondaryContainer = SeedOnSecondaryContainer,
    tertiary = SeedTertiary,
    onTertiary = Color.White,
    tertiaryContainer = SeedTertiaryContainer,
    onTertiaryContainer = SeedOnTertiaryContainer,
    background = SeedBackground,
    onBackground = Color(0xFF1A1C1B),
    surface = SeedSurface,
    onSurface = Color(0xFF1A1C1B),
    surfaceVariant = SeedSurfaceVariant,
    onSurfaceVariant = Color(0xFF5C5E5C),
    // 减轻 M3 默认表面着色，整体更干净偏白
    surfaceTint = Color(0x00000000),
    outline = SeedOutline,
    outlineVariant = SeedOutlineVariant
)

@Composable
fun JilemeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = JilemeShapes,
        content = content
    )
}
