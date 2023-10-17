package com.bankly.feature.transactiondetails.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberTransactionDetailsState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<TransactionDetailsState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            TransactionDetailsState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class TransactionDetailsState(
    val navHostController: NavHostController,
)
