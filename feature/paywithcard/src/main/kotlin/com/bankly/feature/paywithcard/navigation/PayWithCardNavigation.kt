package com.bankly.feature.paywithcard.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation


fun NavGraphBuilder.payWithCardNavGraph(
    onBackPress: () -> Unit
) {
    navigation(
        route = payWithCardNavGraphRoute,
        startDestination = payWithCardRoute,
    ) {

    }
}