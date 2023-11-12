package com.bankly.feature.checkcardbalance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberCheckCardBalanceState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<CheckCardBalanceState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            CheckCardBalanceState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class CheckCardBalanceState(
    val navHostController: NavHostController,
)
