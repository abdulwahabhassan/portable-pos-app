package com.bankly.core.designsystem.theme

import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider


@Composable
fun BanklyTheme(
    content: @Composable () -> Unit
) {
    val banklyLightColorScheme = lightColorScheme(
        primary = blue06,
        onPrimary = white,
        primaryContainer = blue02,
        secondary = pink04,
        onSecondary = blue06,
        inversePrimary = blue02,
        surfaceVariant = blue01,
        tertiary = grey05,
        onTertiary = grey05,
        tertiaryContainer = grey01,
        error = red,
        errorContainer = pink02,
        onError = red,
        outline = blue06,
    )

    CompositionLocalProvider(LocalRippleTheme provides BanklyRippleTheme) {
        MaterialTheme(
            colorScheme = banklyLightColorScheme,
            typography = banklyTypography,
            content = content,
        )
    }

}