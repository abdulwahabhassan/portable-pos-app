package com.bankly.feature.paybills.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberBillPaymentState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<BillPaymentStateState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            BillPaymentStateState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class BillPaymentStateState(
    val navHostController: NavHostController,
)
