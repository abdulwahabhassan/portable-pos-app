package com.bankly.feature.cardtransfer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberCardTransferState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<CardTransferState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            CardTransferState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class CardTransferState(
    val navHostController: NavHostController,
)
