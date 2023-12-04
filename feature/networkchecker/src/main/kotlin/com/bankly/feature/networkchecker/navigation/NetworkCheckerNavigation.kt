package com.bankly.feature.networkchecker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.networkCheckerNavGraph(
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit
) {
    navigation(
        route = networkCheckerNavGraphRoute,
        startDestination = networkCheckerRoute,
    ) {
        composable(networkCheckerRoute) {
            val networkCheckerState by rememberNetworkCheckerState()
            NetworkCheckerNavHost(
                navHostController = networkCheckerState.navHostController,
                onBackPress = onBackPress,
                onSessionExpired = onSessionExpired
            )
        }
    }
}

@Composable
private fun NetworkCheckerNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = networkCheckerListRoute,
    ) {
        networkCheckerListRoute(
            onBackPress = onBackPress,
            onSessionExpired = onSessionExpired
        )
    }
}
