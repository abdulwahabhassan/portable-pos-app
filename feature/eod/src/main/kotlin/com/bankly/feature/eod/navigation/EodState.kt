package com.bankly.feature.eod.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberEodState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<EodState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            EodState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class EodState(
    val navHostController: NavHostController,
)
