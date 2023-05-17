package com.bankly.banklykozenpos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bankly.feature.authentication.navigation.CreateNewPassCodeScreen
import com.bankly.feature.authentication.navigation.SetPinScreen
import com.bankly.feature.authentication.navigation.SuccessfulScreen

@Composable
fun BanklyNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "login"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("login") {
            SetPinScreen()
        }
    }
}
