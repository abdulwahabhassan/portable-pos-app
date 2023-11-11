package com.bankly.feature.settings.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberSettingsState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<SettingsState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            SettingsState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class SettingsState(
    val navHostController: NavHostController,
)
