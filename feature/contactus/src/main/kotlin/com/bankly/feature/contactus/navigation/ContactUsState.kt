package com.bankly.feature.contactus.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
internal fun rememberContactUsState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<ContactUsState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            ContactUsState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
internal data class ContactUsState(
    val navHostController: NavHostController,
)
