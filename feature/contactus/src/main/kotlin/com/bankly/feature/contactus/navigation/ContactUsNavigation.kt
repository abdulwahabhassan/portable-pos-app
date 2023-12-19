package com.bankly.feature.contactus.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.contactUsNavGraph(
    onBackPress: () -> Unit,
) {
    navigation(
        route = contactUsNavGraphRoute,
        startDestination = contactUsRoute,
    ) {
        composable(contactUsRoute) {
            val contactUsState by rememberContactUsState()
            CardTransferNavHost(
                navHostController = contactUsState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun CardTransferNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = contactUsRoute,
    ) {
        contactUsRoute(
            onBackPress = onBackPress,
        )
    }
}
