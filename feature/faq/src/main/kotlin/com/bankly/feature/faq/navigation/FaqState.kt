package com.bankly.feature.faq.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberFaqState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<FaqState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            FaqState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class FaqState(
    val navHostController: NavHostController,
)