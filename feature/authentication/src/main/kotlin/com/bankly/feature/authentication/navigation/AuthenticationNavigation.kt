package com.bankly.feature.authentication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.authenticationNavGraph(
    onLoginSuccess: () -> Unit,
    onBackPress: () -> Unit,
) {
    navigation(
        route = authenticationNavGraphRoute,
        startDestination = authenticationRoute,
    ) {
        composable(authenticationRoute) {
            val authenticationState by rememberAuthenticationState()
            AuthenticationNavHost(
                navHostController = authenticationState.navHostController,
                onLoginSuccess = onLoginSuccess,
                onBackPress = onBackPress
            )
        }
    }
}

@Composable
fun AuthenticationNavHost(
    navHostController: NavHostController,
    onLoginSuccess: () -> Unit,
    onBackPress: () -> Unit
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = loginRoute,
    ) {
        loginRoute(
            onLoginSuccess = onLoginSuccess,
            onBackPress = onBackPress,
            onRecoverPassCodeClick = {
                navHostController.navigateToRecoverPassCodeRoute()
            })
        recoverPassCodeRoute(
            onRecoverPassCodeSuccess = { phoneNumber: String ->
                navHostController.navigateToOtpValidationRoute(phoneNumber = phoneNumber)
            },
            onBackPress = {
                navHostController.popBackStack()
            }
        )
        otpValidationRoute(
            onOtpValidationSuccess = { phoneNumber: String, otp: String ->
                navHostController.navigateToSetNewPassCodeRoute(
                    phoneNumber = phoneNumber,
                    otp = otp
                )
            },
            onBackPress = {
                navHostController.popBackStack()
            })
        setNewPassCodeRoute(
            onSetNewPassCodeSuccess = { message: String ->
                navHostController.navigateToSuccessfulRoute(message = message)
            },
            onBackPress = {
                navHostController.popBackStack()
            })
        successfulRoute(
            onBackToLoginClick = {
                navHostController.popBackStack()
            }
        )
        setPinRoute()
        confirmPinRoute()
        createNewPassCodeRoute()
    }
}



