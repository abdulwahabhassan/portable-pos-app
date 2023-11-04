package com.bankly.feature.contactus.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bankly.feature.contactus.ui.ContactUsRoute

const val contactUsNavGraphRoute = "contact_us_nav_graph"
internal const val contactUsRoute = contactUsNavGraphRoute.plus("/contact_us_route")

internal fun NavGraphBuilder.contactUsRoute(
    onBackPress: () -> Unit,
) {
    composable(route = contactUsRoute) {
        ContactUsRoute(
            onBackPress = onBackPress
        )
    }
}