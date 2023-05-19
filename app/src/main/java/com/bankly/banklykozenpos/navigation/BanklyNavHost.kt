package com.bankly.banklykozenpos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bankly.banklykozenpos.ui.BanklyAppState
import com.bankly.feature.authentication.navigation.authenticationNavGraph
import com.bankly.feature.authentication.navigation.authenticationNavGraphRoutePattern
import com.bankly.feature.authentication.navigation.confirmPinRoute
import com.bankly.feature.authentication.navigation.confirmPinScreen
import com.bankly.feature.authentication.navigation.createNewPassCodeScreen
import com.bankly.feature.authentication.navigation.inputPassCodeScreen
import com.bankly.feature.authentication.navigation.inputPhoneNumberScreen
import com.bankly.feature.authentication.navigation.loginRoute
import com.bankly.feature.authentication.navigation.setNewPassCodeScreen
import com.bankly.feature.authentication.navigation.setPinScreen
import com.bankly.feature.authentication.navigation.successfulScreen
import com.bankly.feature.authentication.ui.SetPinScreen

@Composable
fun BanklyNavHost(
    appState: BanklyAppState,
    modifier: Modifier = Modifier,
    startDestination: String = authenticationNavGraphRoutePattern
) {
    val navController: NavHostController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        authenticationNavGraph(
            onLoginClick = {phoneNumber, passCode ->

            },
            nestedGraphs = {
                confirmPinScreen()
                createNewPassCodeScreen()
                inputPassCodeScreen()
                inputPhoneNumberScreen()
                successfulScreen()
                setNewPassCodeScreen()
                setPinScreen()
            }
        )
    }
}
