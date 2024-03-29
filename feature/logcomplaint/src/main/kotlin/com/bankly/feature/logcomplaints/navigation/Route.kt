package com.bankly.feature.logcomplaints.navigation

import androidx.compose.foundation.layout.padding
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.core.common.ui.view.ComingSoonView
import com.bankly.feature.logcomplaints.ui.LoggedComplaintRoute
import com.bankly.feature.logcomplaints.ui.NewComplaintRoute
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val logComplaintNavGraphRoute = "log_complaint_graph"
internal const val logComplaintRoute = logComplaintNavGraphRoute.plus("/log_complaint_route")
internal const val newComplaintRoute = logComplaintRoute.plus("/log_complaint_screen")
internal const val complaintLoggedRoute = logComplaintRoute.plus("/complaint_logged_screen")

internal fun NavGraphBuilder.newComplaintRoute(
    onBackPress: () -> Unit,
    onSuccessfulLog: (String) -> Unit,
) {
    composable(route = newComplaintRoute) {
        NewComplaintRoute(
            onBackPress = onBackPress,
            onSuccessfulLog = onSuccessfulLog,
        )
    }
}

internal fun NavGraphBuilder.complaintLoggedRoute(
    onBackPress: () -> Unit,
    onGoToHomeClick: () -> Unit,
) {
    composable(
        route = "$complaintLoggedRoute/{$complaintIdArg}",
        arguments = listOf(
            navArgument(complaintIdArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(complaintIdArg)?.let { complaintIdString: String ->
            val complaintId: String = Json.decodeFromString(complaintIdString)
            LoggedComplaintRoute(
                onBackPress = onBackPress,
                onGoToHome = onGoToHomeClick,
                complaintId = complaintId,
            )
        }
    }
}
