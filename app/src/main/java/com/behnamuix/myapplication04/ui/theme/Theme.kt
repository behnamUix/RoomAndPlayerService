package com.behnamuix.myapplication04.ui.theme

import android.os.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = InstaPink,
    secondary = InstaPurple,
    tertiary = InstaOrange,
    background = InstaDarkBg,
    surface = InstaDarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = InstaTextLight,
    onSurface = InstaTextLight
)

private val LightColorScheme = lightColorScheme(
    primary = InstaPink,
    secondary = InstaPurple,
    tertiary = InstaOrange,
    background = InstaLightBg,
    surface = InstaLightSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = InstaTextDark,
    onSurface = InstaTextDark
)

@Composable
fun MyApplication04Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}