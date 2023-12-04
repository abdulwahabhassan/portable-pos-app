package com.bankly.feature.networkchecker.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bankly.feature.networkchecker.ui.NetworkCheckerListRoute

const val networkCheckerNavGraphRoute = "network_checker_graph"
internal const val networkCheckerRoute = networkCheckerNavGraphRoute.plus("/network_checker_route")
internal const val networkCheckerListRoute =
    networkCheckerRoute.plus("/network_checker_list_screen")

internal fun NavGraphBuilder.networkCheckerListRoute(
    onBackPress: () -> Unit,
    onSessionExpired: () -> Unit
) {
    composable(route = networkCheckerListRoute) {
        NetworkCheckerListRoute(
            onBackPress = onBackPress,
            onSessionExpired = onSessionExpired
        )
    }
}

