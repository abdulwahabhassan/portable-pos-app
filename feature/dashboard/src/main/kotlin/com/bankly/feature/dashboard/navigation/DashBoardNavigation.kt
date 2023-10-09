package com.bankly.feature.dashboard.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bankly.feature.dashboard.model.DashboardTab
import com.bankly.feature.dashboard.model.Feature
import com.bankly.feature.dashboard.ui.dashboard.DashBoardRoute

fun NavGraphBuilder.dashBoardNavGraph(
    onBackPress: () -> Unit,
    onFeatureClick: (Feature) -> Unit,
    onContinueToPayWithCardClick: (Double) -> Unit,
) {
    navigation(
        route = dashBoardNavGraphRoute,
        startDestination = dashBoardRoute,
    ) {
        composable(dashBoardRoute) {
            var dashBoardState by rememberDashBoardState()
            DashBoardRoute(
                showTopAppBar = dashBoardState.shouldShowTopAppBar,
                currentBottomNavDestination = dashBoardState.currentBottomNavDestination,
                onNavigateToBottomNavDestination = { destination ->
                    dashBoardState.navigateTo(destination)
                },
                showBottomNavBar = dashBoardState.shouldShowBottomNavBar,
                content = { padding ->
                    DashBoardBottomNavHost(
                        currentHomeTab = dashBoardState.currentTab,
                        modifier = Modifier.padding(padding),
                        navHostController = dashBoardState.navHostController,
                        onFeatureClick = { feature: Feature ->
                            if (feature == Feature.PayWithCard) {
                                dashBoardState = dashBoardState.copy(currentTab = DashboardTab.POS)
                            } else {
                                onFeatureClick(feature)
                            }
                        },
                        onContinueToPayWithCardClick = onContinueToPayWithCardClick,
                    )
                },
                currentTab = dashBoardState.currentTab,
                onTabChange = { tab: DashboardTab ->
                    dashBoardState = dashBoardState.copy(currentTab = tab)
                },
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
fun DashBoardBottomNavHost(
    currentHomeTab: DashboardTab,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onFeatureClick: (Feature) -> Unit,
    onContinueToPayWithCardClick: (Double) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = homeRoute,
    ) {
        homeRoute(
            currentHomeTab = currentHomeTab,
            onFeatureClick = onFeatureClick,
            onContinueToPayWithCardClick = onContinueToPayWithCardClick,
        )
        transactionsRoute()
        supportRoute()
        moreRoute(
            onFeatureClick = onFeatureClick
        )
    }
}
