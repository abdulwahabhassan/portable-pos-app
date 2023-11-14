package com.bankly.feature.checkcardbalance.navigation

import ProcessPayment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bankly.core.common.model.AccountType
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools

private const val DEFAULT_AMOUNT = 20.0

fun NavGraphBuilder.checkCardBalanceNavGraph(
    onBackPress: () -> Unit,
) {


    navigation(
        route = checkCardBalanceNavGraphRoute,
        startDestination = checkCardBalanceRoute,
    ) {
        composable(checkCardBalanceRoute) {
            val checkCardBalanceState by rememberCheckCardBalanceState()
            CheckCardBalanceNavHost(
                navHostController = checkCardBalanceState.navHostController,
                onBackPress = onBackPress,
            )
        }
    }
}

@Composable
private fun CheckCardBalanceNavHost(
    navHostController: NavHostController,
    onBackPress: () -> Unit,
) {
    val context = LocalContext.current

    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = selectAccountTypeRoute,
    ) {
        selectAccountTypeRoute(
            onAccountSelected = { accountType: AccountType ->
                val acctType = when (accountType) {
                    AccountType.SAVINGS -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.SAVINGS
                    AccountType.DEFAULT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.DEFAULT
                    AccountType.CREDIT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CREDIT
                    AccountType.CURRENT -> com.bankly.kozonpaymentlibrarymodule.posservices.AccountType.CURRENT
                }

                Tools.transactionType =
                    com.bankly.kozonpaymentlibrarymodule.posservices.TransactionType.BALANCE_INQUIRY
                Tools.TransactionAmount = DEFAULT_AMOUNT
                Tools.SetAccountType(acctType)
                ProcessPayment(context) { transactionResponse, _ ->
                    val balance = transactionResponse.accountMainBalance?.ifEmpty {
                        transactionResponse.accountLegerBalance ?: ""
                    } ?: transactionResponse.accountLegerBalance ?: ""
                    navHostController.navigateToCardBalanceRoute(
                        cardBalanceAmount = balance,
                        responseCode = transactionResponse.responseCode ?: "",
                        responseMessage = transactionResponse.responseMessage ?: ""
                    )
                }
            },
            onBackPress = {
                navHostController.popBackStack()
            },
            onCancelPress = onBackPress
        )
        cardBalanceRoute(
            onGoToDashboardClick = onBackPress
        )
    }
}
