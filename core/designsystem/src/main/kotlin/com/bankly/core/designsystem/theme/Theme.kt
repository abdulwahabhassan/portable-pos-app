package com.bankly.core.designsystem.theme

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun BanklyTheme(
    content: @Composable () -> Unit,
) {
    val banklyLightColorScheme = lightColorScheme(
        primary = blue06, // Icon tint, Text color
        onPrimary = white,
        primaryContainer = blue02.copy(0.5f), // InputTextField background
        onPrimaryContainer = grey04, // InputTextField placeholder
        inversePrimary = blue03.copy(0.6f), // Disabled Icon and Text color

        secondary = pink04,
        onSecondary = blue06,

        tertiary = grey05, // Body text, Title
        onTertiary = white,
        tertiaryContainer = grey02.copy(alpha = 0.5f), // Disabled InputTextField, Disabled Button
        onTertiaryContainer = blue01,

        error = red, // Error text
        onError = white,
        errorContainer = pink02, // Error InputTextField
        onErrorContainer = red,

        background = grey00,
        surface = grey00,
        surfaceVariant = white, // Card background
        outlineVariant = grey01,
    )

    CompositionLocalProvider(LocalRippleTheme provides BanklyRippleTheme) {
        MaterialTheme(
            colorScheme = banklyLightColorScheme,
            typography = banklyTypography,
            content = content,
        )
    }
}
