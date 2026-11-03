package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

enum class EyeComfortTheme(val title: String, val subtitle: String, val iconEmoji: String) {
    MIDNIGHT_NAVY("Midnight Navy", "Deep cyber navy for focused study", "🌌"),
    WARM_SEPIA("Warm Eye-Care", "Amber tone, reduces blue-light strain", "🕯️"),
    OLED_BLACK("OLED Pitch Black", "Zero backlight bleed, maximum battery", "🌑"),
    CLEAN_LIGHT("Daylight Clean", "Crisp high-contrast daytime reading", "☀️")
}

private val MidnightNavyColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = Color(0xFF03142B),
    primaryContainer = Color(0xFF0C3866),
    onPrimaryContainer = Color(0xFFBAE6FD),
    secondary = VioletAccent,
    onSecondary = Color(0xFF1E0A45),
    secondaryContainer = Color(0xFF3B1E78),
    onSecondaryContainer = Color(0xFFE9D5FF),
    tertiary = AmberGold,
    onTertiary = Color(0xFF451A03),
    tertiaryContainer = Color(0xFF78350F),
    onTertiaryContainer = Color(0xFFFEF3C7),
    background = DarkBackground,
    onBackground = Color(0xFFF1F5F9),
    surface = DarkSurface,
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFF94A3B8),
    outline = DarkBorder,
    error = CrimsonCoral,
    onError = Color.White
)

private val WarmSepiaColorScheme = darkColorScheme(
    primary = AmberGold,
    onPrimary = Color(0xFF261202),
    primaryContainer = Color(0xFF452207),
    onPrimaryContainer = Color(0xFFFEF3C7),
    secondary = Color(0xFFD97706),
    onSecondary = Color(0xFF261202),
    secondaryContainer = Color(0xFF3B2312),
    onSecondaryContainer = Color(0xFFFDE68A),
    tertiary = Color(0xFF10B981),
    onTertiary = Color(0xFF022614),
    background = Color(0xFF17130E),
    onBackground = Color(0xFFFDE8C8),
    surface = Color(0xFF211B14),
    onSurface = Color(0xFFFDE8C8),
    surfaceVariant = Color(0xFF2D251C),
    onSurfaceVariant = Color(0xFFD4BFA4),
    outline = Color(0xFF4A3C2D),
    error = CrimsonCoral,
    onError = Color.White
)

private val OledBlackColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF111827),
    onPrimaryContainer = Color(0xFFBAE6FD),
    secondary = VioletAccent,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF1F1338),
    onSecondaryContainer = Color(0xFFE9D5FF),
    tertiary = AmberGold,
    onTertiary = Color.Black,
    background = Color.Black,
    onBackground = Color(0xFFF3F4F6),
    surface = Color(0xFF0A0A0A),
    onSurface = Color(0xFFF3F4F6),
    surfaceVariant = Color(0xFF171717),
    onSurfaceVariant = Color(0xFFA3A3A3),
    outline = Color(0xFF262626),
    error = CrimsonCoral,
    onError = Color.White
)

private val CleanLightColorScheme = lightColorScheme(
    primary = ElectricBlueDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0F2FE),
    onPrimaryContainer = Color(0xFF0369A1),
    secondary = DeepViolet,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF3E8FF),
    onSecondaryContainer = Color(0xFF6D28D9),
    tertiary = AmberGold,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFEF3C7),
    onTertiaryContainer = Color(0xFF92400E),
    background = LightBackground,
    onBackground = Color(0xFF0F172A),
    surface = LightSurface,
    onSurface = Color(0xFF0F172A),
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = Color(0xFF475569),
    outline = LightBorder,
    error = CrimsonCoral,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    eyeTheme: EyeComfortTheme = EyeComfortTheme.MIDNIGHT_NAVY,
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when (eyeTheme) {
        EyeComfortTheme.MIDNIGHT_NAVY -> MidnightNavyColorScheme
        EyeComfortTheme.WARM_SEPIA -> WarmSepiaColorScheme
        EyeComfortTheme.OLED_BLACK -> OledBlackColorScheme
        EyeComfortTheme.CLEAN_LIGHT -> CleanLightColorScheme
    }

    val isDark = eyeTheme != EyeComfortTheme.CLEAN_LIGHT
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !isDark
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
