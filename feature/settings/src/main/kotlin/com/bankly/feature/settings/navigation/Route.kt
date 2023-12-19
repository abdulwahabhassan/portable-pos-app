package com.bankly.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bankly.feature.settings.ui.SettingsRoute

const val settingsNavGraphRoute = "settings_graph"
internal const val settingsRoute = settingsNavGraphRoute.plus("/settings_route")
internal const val featureToggleListRoute =
    settingsRoute.plus("/feature_toggle_list_screen")

internal fun NavGraphBuilder.featureToggleListRoute(
    onBackPress: () -> Unit,
) {
    composable(route = featureToggleListRoute) {
        SettingsRoute(
            onBackPress = onBackPress,
        )
    }
}
