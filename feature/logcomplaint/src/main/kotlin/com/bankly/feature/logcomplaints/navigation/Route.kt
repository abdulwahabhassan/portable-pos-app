package com.bankly.feature.logcomplaints.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.feature.logcomplaints.ui.LoggedComplaintRoute
import com.bankly.feature.logcomplaints.ui.NewComplaintRoute

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
        it.arguments?.getString(complaintIdArg)?.let { complaintId: String ->
            LoggedComplaintRoute(
                onBackPress = onBackPress,
                onGoToHome = onGoToHomeClick,
                complaintId = complaintId,
            )
        }
    }
}
