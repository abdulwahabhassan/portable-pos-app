package com.bankly.feature.paywithcard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberPayWithCardState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<PayWithCardState> {
    return remember(
        navHostController
    ) {
        mutableStateOf(
            PayWithCardState(
                navHostController = navHostController
            )
        )
    }
}

@Stable
data class PayWithCardState(
    val navHostController: NavHostController,
) {
    val shouldShowTopAppBar: Boolean
        @Composable get() = true
}

