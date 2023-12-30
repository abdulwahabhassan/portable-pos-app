package com.bankly.feature.faq.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bankly.feature.faq.ui.FaqRoute

const val faqNavGraphRoute = "faq_nav_graph"
internal const val faqRoute = faqNavGraphRoute.plus("/faq_route")

internal fun NavGraphBuilder.faqRoute(
    onBackPress: () -> Unit,
) {
    composable(route = faqRoute) {
        FaqRoute(
            onBackPress = onBackPress,
        )
    }
}
