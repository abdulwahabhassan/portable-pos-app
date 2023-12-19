package com.bankly.feature.logcomplaints.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.logComplaintNavGraph(
    onBackPress: () -> Unit,
) {
    navigation(
        route = logComplaintNavGraphRoute,
        startDestination = logComplaintRoute,
    ) {
        composable(logComplaintRoute) {
            val sendMoneyState by rememberLogComplaintState()
            LogComplaintNavHost(
                navHostController = sendMoneyState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun LogComplaintNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = newComplaintRoute,
    ) {
        newComplaintRoute(
            onBackPress = onBackPress,
            onSuccessfulLog = { complaintId ->
                navHostController.navigateToComplaintLoggedRoute(complaintId)
            },
        )

        complaintLoggedRoute(
            onBackPress = onBackPress,
            onGoToHomeClick = onBackPress,
        )
    }
}
