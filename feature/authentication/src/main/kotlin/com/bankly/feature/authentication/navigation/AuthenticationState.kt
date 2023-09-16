package com.bankly.feature.authentication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAuthenticationState(
    navHostController: NavHostController = rememberNavController(),
): MutableState<AuthenticationState> {
    return remember(
        navHostController,
    ) {
        mutableStateOf(
            AuthenticationState(
                navHostController = navHostController,
            ),
        )
    }
}

@Stable
data class AuthenticationState(
    val navHostController: NavHostController,
) {
    val shouldShowTopAppBar: Boolean
        @Composable get() = true
}
