package com.bankly.feature.networkchecker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberNetworkCheckerState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<NetworkCheckerState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            NetworkCheckerState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class NetworkCheckerState(
    val navHostController: NavHostController,
)
