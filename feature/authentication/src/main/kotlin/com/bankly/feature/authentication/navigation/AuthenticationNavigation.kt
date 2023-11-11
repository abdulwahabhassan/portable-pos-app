package com.bankly.feature.authentication.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.authenticationNavGraph(
    appNavController: NavHostController,
    onLoginSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onGoToSettingsRoute: () -> Unit,
    onPopBackStack: () -> Unit
) {
    navigation(
        route = "$authenticationNavGraphRoute/{$isValidatePassCodeArg}",
        startDestination = authenticationRoute,
        arguments = listOf(
            navArgument(isValidatePassCodeArg) { type = NavType.StringType },
        ),
    ) {

        composable(authenticationRoute) { backStackEntry: NavBackStackEntry ->
            val parentEntry = remember(backStackEntry) {
                appNavController.getBackStackEntry(authenticationRoute)
            }
            val isValidatePassCode = parentEntry.arguments?.getString(
                isValidatePassCodeArg,
            )?.toBoolean() ?: false
            Log.d("debug auth nav", "isValidatePasscode: $isValidatePassCode")
            val authenticationState by rememberAuthenticationState()
            AuthenticationNavHost(
                navHostController = authenticationState.navHostController,
                onLoginSuccess = onLoginSuccess,
                onBackPress = if (isValidatePassCode) onPopBackStack else onBackPress,
                onGoToSettingsRoute = onGoToSettingsRoute,
                isValidatePassCode = isValidatePassCode
            )
        }
    }
}

@Composable
fun AuthenticationNavHost(
    navHostController: NavHostController,
    onLoginSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onGoToSettingsRoute: () -> Unit,
    isValidatePassCode: Boolean
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = if (isValidatePassCode) validatePassCodeRoute else loginRoute,
    ) {
        loginRoute(
            onLoginSuccess = onLoginSuccess,
            onBackPress = onBackPress,
            onRecoverPassCodeClick = {
                navHostController.navigateToRecoverPassCodeRoute()
            },
        )
        recoverPassCodeRoute(
            onRecoverPassCodeSuccess = { phoneNumber: String ->
                navHostController.navigateToOtpValidationRoute(phoneNumber = phoneNumber)
            },
            onBackPress = {
                navHostController.popBackStack()
            },
        )
        otpValidationRoute(
            onOtpValidationSuccess = { phoneNumber: String, otp: String ->
                navHostController.navigateToSetNewPassCodeRoute(
                    phoneNumber = phoneNumber,
                    otp = otp,
                )
            },
            onBackPress = {
                navHostController.popBackStack()
            },
        )
        setNewPassCodeRoute(
            onSetNewPassCodeSuccess = { message: String ->
                navHostController.navigateToSuccessfulRoute(message = message)
            },
            onBackPress = {
                navHostController.popBackStack()
            },
        )
        successfulRoute(
            onBackToLoginClick = {
                navHostController.popBackStack()
            },
        )
        setPinRoute()
        confirmPinRoute()
        createNewPassCodeRoute()
        validatePassCodeRoute(
            onBackPress = {
                if (navHostController.popBackStack().not()) onBackPress()
            },
            onGoToSettingsRoute = onGoToSettingsRoute
        )
    }
}
