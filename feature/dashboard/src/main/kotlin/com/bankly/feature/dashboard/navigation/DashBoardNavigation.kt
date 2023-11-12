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
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.dashboard.model.DashboardTab
import com.bankly.core.entity.Feature
import com.bankly.feature.dashboard.model.SupportOption
import com.bankly.feature.dashboard.ui.dashboard.DashBoardRoute

fun NavGraphBuilder.dashBoardNavGraph(
    onExitApp: () -> Unit,
    onFeatureClick: (Feature) -> Unit,
    onContinueToPayWithCardClick: (Double) -> Unit,
    onGoToTransactionDetailsScreen: (TransactionReceipt) -> Unit,
    onSupportOptionClick: (SupportOption) -> Unit,
    onLogOutClick: () -> Unit,
) {
    navigation(
        route = dashBoardNavGraphRoute,
        startDestination = dashBoardRoute,
    ) {
        composable(dashBoardRoute) {
            var dashBoardState by rememberDashBoardState()

            DashBoardRoute(
                showLoadingIndicator = dashBoardState.showLoadingIndicator,
                showTopAppBar = dashBoardState.shouldShowTopAppBar,
                currentBottomNavDestination = dashBoardState.currentBottomNavDestination,
                onNavigateToBottomNavDestination = { destination ->
                    dashBoardState.navigateTo(destination)
                },
                showBottomNavBar = dashBoardState.shouldShowBottomNavBar,
                content = { padding ->
                    DashBoardNavHost(
                        currentHomeTab = dashBoardState.currentTab,
                        modifier = Modifier.padding(padding),
                        navHostController = dashBoardState.navHostController,
                        onFeatureClick = { feature: Feature ->
                            if (feature is Feature.PayWithCard) {
                                dashBoardState = dashBoardState.copy(currentTab = DashboardTab.POS)
                            } else {
                                onFeatureClick(feature)
                            }
                        },
                        onContinueToPayWithCardClick = onContinueToPayWithCardClick,
                        updateLoadingStatus = { isLoading: Boolean ->
                            dashBoardState = dashBoardState.copy(showLoadingIndicator = isLoading)
                        },
                        onGoToTransactionDetailsScreen = onGoToTransactionDetailsScreen,
                        onSupportOptionClick = onSupportOptionClick,
                        onLogOutClick = onLogOutClick
                    )
                },
                currentTab = dashBoardState.currentTab,
                onTabChange = { tab: DashboardTab ->
                    dashBoardState = dashBoardState.copy(currentTab = tab)
                },
                onExitApp = onExitApp,
            )
        }
    }
}

@Composable
fun DashBoardNavHost(
    currentHomeTab: DashboardTab,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onFeatureClick: (Feature) -> Unit,
    onContinueToPayWithCardClick: (Double) -> Unit,
    updateLoadingStatus: (Boolean) -> Unit,
    onGoToTransactionDetailsScreen: (TransactionReceipt) -> Unit,
    onSupportOptionClick: (SupportOption) -> Unit,
    onLogOutClick: () -> Unit,
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
        transactionsRoute(
            onBackPress = {
                navHostController.navigateToHomeRoute()
            },
            onGoToTransactionDetailsScreen = { transactionReceipt: TransactionReceipt ->
                onGoToTransactionDetailsScreen(transactionReceipt)
            },
            updateLoadingStatus = updateLoadingStatus
        )
        supportRoute(
            onBackPress = {
                navHostController.navigateToHomeRoute()
            },
            onSupportOptionClick = onSupportOptionClick
        )
        moreRoute(
            onFeatureClick = onFeatureClick,
            onBackPress = {
                navHostController.navigateToHomeRoute()
            },
            onLogOutClick = onLogOutClick
        )
    }
}
