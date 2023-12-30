package com.bankly.feature.faq.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.faqNavGraph(
    onBackPress: () -> Unit,
) {
    navigation(
        route = faqNavGraphRoute,
        startDestination = faqRoute,
    ) {
        composable(faqRoute) {
            val contactUsState by rememberFaqState()
            FaqNavHost(
                navHostController = contactUsState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun FaqNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = faqRoute,
    ) {
        faqRoute(
            onBackPress = onBackPress,
        )
    }
}
