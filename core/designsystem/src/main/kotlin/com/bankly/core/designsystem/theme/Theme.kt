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
        primary = blue06, //Icon tint, Text color
        onPrimary = white,
        primaryContainer = blue02, //InputTextField background
        onPrimaryContainer = grey04, // InputTextField placeholder
        inversePrimary = blue03, //Disabled Icon and Text color

        secondary = pink04,
        onSecondary = blue06,

        tertiary = grey05, //Body text, Title
        onTertiary = white,
        tertiaryContainer = grey02, //Disabled InputTextField, Disabled Button
        onTertiaryContainer = blue01,

        error = red, //Error text
        onError = white,
        errorContainer = pink02, //Error InputTextField
        onErrorContainer = red,

        surface = white,
        surfaceVariant = white //Card background

    )

    CompositionLocalProvider(LocalRippleTheme provides BanklyRippleTheme) {
        MaterialTheme(
            colorScheme = banklyLightColorScheme,
            typography = banklyTypography,
            content = content,
        )
    }

}