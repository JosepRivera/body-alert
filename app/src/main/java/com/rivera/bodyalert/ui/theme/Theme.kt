package com.rivera.bodyalert.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = InfoBackground,
    onPrimaryContainer = Primary,

    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SurfaceVariant,
    onSecondaryContainer = OnSurface,

    tertiary = Gray600,
    onTertiary = Color.White,
    tertiaryContainer = Gray100,
    onTertiaryContainer = Gray800,

    background = Background,
    onBackground = OnBackground,

    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    surfaceTint = Primary,
    inverseSurface = Gray900,
    inverseOnSurface = Gray50,

    error = Error,
    onError = Color.White,
    errorContainer = ErrorBackground,
    onErrorContainer = Error,

    outline = Border,
    outlineVariant = Gray100,
    scrim = Color.Black.copy(alpha = 0.32f)
)

@Composable
fun BodyAlertTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = true
                isAppearanceLightNavigationBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}