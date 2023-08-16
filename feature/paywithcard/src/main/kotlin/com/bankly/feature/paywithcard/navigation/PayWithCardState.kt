package com.bankly.feature.paywithcard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bankly.core.common.model.AccountType

@Composable
fun rememberPayWithCardState(
    navHostController: NavHostController = rememberNavController(),
    amount: Double,
    accountType: AccountType? = null
): MutableState<PayWithCardState> {
    return remember(
        navHostController
    ) {
        mutableStateOf(
            PayWithCardState(
                navHostController = navHostController,
                amount = amount,
                accountType = accountType
            )
        )
    }
}

@Stable
data class PayWithCardState(
    val navHostController: NavHostController,
    val amount: Double,
    val accountType: AccountType?
) {
    val shouldShowTopAppBar: Boolean
        @Composable get() = true
}

