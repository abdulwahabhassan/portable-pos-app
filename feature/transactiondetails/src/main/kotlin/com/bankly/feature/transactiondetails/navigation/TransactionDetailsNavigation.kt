package com.bankly.feature.transactiondetails.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bankly.core.model.sealed.TransactionReceipt
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.transactionDetailsNavGraph(
    appNavController: NavHostController,
    onBackPress: () -> Unit,
    onLogComplaintClick: () -> Unit,
) {
    navigation(
        route = "$transactionDetailsNavGraphRoute/{$transactionReceiptArg}",
        startDestination = transactionDetailsRoute,
        arguments = listOf(
            navArgument(transactionReceiptArg) { type = NavType.StringType },
        ),
    ) {
        composable(transactionDetailsRoute) { backStackEntry: NavBackStackEntry ->
            val parentEntry = remember(backStackEntry) {
                appNavController.getBackStackEntry(transactionDetailsRoute)
            }
            val transactionReceipt = parentEntry.arguments?.getString(transactionReceiptArg)
            val decodedTransactionReceipt: TransactionReceipt? = transactionReceipt?.let { Json.decodeFromString(it) }
            Log.d("debug transaction receipt", "transaction receipt details nav graph $transactionReceipt")
            val transactionDetailsState by rememberTransactionDetailsState()
            if (decodedTransactionReceipt != null) {
                TransactionDetailsNavHost(
                    transactionReceipt = decodedTransactionReceipt,
                    navHostController = transactionDetailsState.navHostController,
                    onBackPress = onBackPress,
                    onLogComplaintClick = onLogComplaintClick,
                )
            }
        }
    }
}

@Composable
private fun TransactionDetailsNavHost(
    transactionReceipt: TransactionReceipt,
    navHostController: NavHostController,
    onBackPress: () -> Unit,
    onLogComplaintClick: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = transactionDetailsRoute,
    ) {
        transactionDetailsRoute(
            transactionReceipt = transactionReceipt,
            onShareClick = { },
            onSmsClick = { transactionReceipt ->
                navHostController.navigateToSendReceiptRoute(transactionReceipt = transactionReceipt)
            },
            onLogComplaintClick = onLogComplaintClick,
            onBackPress = onBackPress,
        )
        sendReceiptRoute(
            onGoToSuccessScreen = {
                navHostController.navigateToDoneRoute(title = "Receipt Sent", message = "Your receipt has been sent")
            },
            onBackPress = {
                navHostController.popBackStack()
            },
        )
        doneRoute(
            onDoneClick = {
                navHostController.popBackStack()
            },
        )
    }
}
