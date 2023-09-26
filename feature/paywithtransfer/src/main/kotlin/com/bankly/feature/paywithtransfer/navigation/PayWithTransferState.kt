package com.bankly.feature.paywithtransfer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberPayWithTransferState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<PayWithTransferState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            PayWithTransferState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class PayWithTransferState(
    val navHostController: NavHostController,
)
