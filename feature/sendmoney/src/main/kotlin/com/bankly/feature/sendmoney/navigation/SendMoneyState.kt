package com.bankly.feature.sendmoney.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberSendMoneyState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<PayWithCardState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            PayWithCardState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class PayWithCardState(
    val navHostController: NavHostController,
)
