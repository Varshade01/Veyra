package com.example.veyra.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkCyberColorScheme = darkColorScheme(
    primary = TechBlue,
    onPrimary = TextPrimary,

    primaryContainer = TechBlueDark,
    onPrimaryContainer = TextPrimary,

    secondary = NeonSafetyGreen,
    onSecondary = DeepSlate,

    secondaryContainer = SafetyGreenDark,
    onSecondaryContainer = TextPrimary,

    background = DeepSlate,
    onBackground = TextPrimary,

    surface = SlateSurface,
    onSurface = TextPrimary,

    surfaceVariant = SlateCard,
    onSurfaceVariant = TextSecondary,

    outline = SlateBorder,

    error = ErrorRed,
    onError = TextPrimary
)

@Composable
fun VeyraTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkCyberColorScheme,
        typography = Typography,
        content = content
    )
}