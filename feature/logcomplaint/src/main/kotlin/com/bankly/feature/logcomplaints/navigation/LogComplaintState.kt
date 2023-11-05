package com.bankly.feature.logcomplaints.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberLogComplaintState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<LogComplaintState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            LogComplaintState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class LogComplaintState(
    val navHostController: NavHostController,
)
