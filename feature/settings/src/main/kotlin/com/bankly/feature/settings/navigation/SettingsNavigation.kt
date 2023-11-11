package com.bankly.feature.settings.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.settingsNavGraph(
    onBackPress: () -> Unit,
) {
    navigation(
        route = settingsNavGraphRoute,
        startDestination = settingsRoute,
    ) {
        composable(settingsRoute) {
            val settingsState by rememberSettingsState()
            SettingsNavHost(
                navHostController = settingsState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun SettingsNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = featureToggleListRoute,
    ) {
        featureToggleListRoute(
            onBackPress = onBackPress,
        )
    }
}
