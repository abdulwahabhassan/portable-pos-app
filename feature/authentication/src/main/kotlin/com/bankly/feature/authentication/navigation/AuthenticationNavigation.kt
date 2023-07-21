package com.bankly.feature.authentication.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.bankly.feature.authentication.ui.login.LoginScreen
import com.bankly.feature.authentication.ui.otp.OtpValidationScreen
import com.bankly.feature.authentication.ui.passcode.CreateNewPassCodeScreen
import com.bankly.feature.authentication.ui.passcode.RecoverPassCodeScreen
import com.bankly.feature.authentication.ui.passcode.SetNewPassCodeScreen
import com.bankly.feature.authentication.ui.pin.ConfirmPinScreen
import com.bankly.feature.authentication.ui.pin.SetPinScreen
import com.bankly.feature.authentication.ui.success.SuccessfulScreen

const val authenticationNavGraph = "authentication_graph"
const val authenticationRoute = authenticationNavGraph.plus("/authentication_route")
const val loginScreen = authenticationRoute.plus("/login_screen")
const val confirmPinScreen = authenticationRoute.plus("/confirm_pin_screen")
const val createNewPassCodeScreen = authenticationRoute.plus("/create_new_pass_code_screen")
const val otpValidationScreen = authenticationRoute.plus("/otp_validation_screen")
const val recoverPassCodeScreen = authenticationRoute.plus("/input_phone_number_screen")
const val setNewPassCodeScreen = authenticationRoute.plus("/set_new_pass_code_screen")
const val setPinScreen = authenticationRoute.plus("/set_pin_route")
const val successfulScreen = authenticationRoute.plus("/successful_screen")


internal const val phoneNumberArg = "phoneNumber"
internal const val otpArg = "otp"
internal const val successMessageArg = "successMessage"

fun NavGraphBuilder.authenticationNavGraph(
    onLoginSuccess: () -> Unit,
    onPopLoginScreen: () -> Unit,
    onBackToLoginClick: () -> Unit,
) {
    navigation(
        route = authenticationNavGraph,
        startDestination = authenticationRoute,
    ) {
        composable(authenticationRoute) {
            val authenticationState by rememberAuthenticationState()
            AuthenticationNavHost(
                authenticationState.navHostController,
                onLoginSuccess,
                onPopLoginScreen,
                onBackToLoginClick
            )
        }

    }
}

@Composable
fun AuthenticationNavHost(
    navHostController: NavHostController,
    onLoginSuccess: () -> Unit,
    onPopLoginScreen: () -> Unit,
    onBackToLoginClick: () -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = navHostController,
        startDestination = loginScreen,
    ) {
        loginScreen(
            onLoginSuccess = onLoginSuccess,
            onBackClick = onPopLoginScreen,
            onRecoverPassCode = {
                navHostController.navigateToRecoverPassCodeScreen()
            })
        recoverPassCodeScreen(
            onRecoverPassCodeSuccess = { phoneNumber: String ->
                navHostController.navigateToOtpValidationScreen(phoneNumber = phoneNumber)
            },
            onPopRecoverPassCodeScreen = {
                navHostController.popBackStack()
            }
        )
        otpValidationScreen(
            onOtpValidationSuccess = { phoneNumber: String, otp: String ->
                navHostController.navigateToSetNewPassCodeScreen(phoneNumber = phoneNumber, otp = otp)
            },
            onPopOtpValidationScreen = {
                navHostController.popBackStack()
            })
        setNewPassCodeScreen(
            onSetNewPassCodeSuccess = { message: String ->
                navHostController.navigateToSuccessfulRoute(message = message)
            },
            onPopSetNewPassCodeScreen = {
                navHostController.popBackStack()
            })
        successfulScreen(onBackToLoginClick = onBackToLoginClick)
        setPinScreen()
        confirmPinScreen()
        createNewPassCodeScreen()
    }
}

/**
 * Login
 */
internal fun NavController.navigateToLoginScreen(topicId: String) {
    this.navigate(loginScreen)
}

internal fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onRecoverPassCode: () -> Unit
) {
    composable(route = loginScreen) {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
            onBackClick = onBackClick,
            onRecoverPassCodeClick = onRecoverPassCode
        )
    }
}

/**
 * Recover Passcode
 */
internal fun NavController.navigateToRecoverPassCodeScreen() {
    this.navigate(recoverPassCodeScreen)
}

internal fun NavGraphBuilder.recoverPassCodeScreen(
    onRecoverPassCodeSuccess: (String) -> Unit,
    onPopRecoverPassCodeScreen: () -> Unit
) {
    composable(
        route = recoverPassCodeScreen,
    ) {
        RecoverPassCodeScreen(
            onRecoverPassCodeSuccess = onRecoverPassCodeSuccess,
            onBackButtonClick = onPopRecoverPassCodeScreen
        )
    }
}

/**
 * OTP Validation
 */
internal fun NavController.navigateToOtpValidationScreen(phoneNumber: String) {
    val encodedPhoneNumber = Uri.encode(phoneNumber)
    Log.d("otp debug encoded", encodedPhoneNumber.toString())
    //Pop up to Login screen
    val navOptions = NavOptions.Builder().setPopUpTo(this.graph.findStartDestination().id, false).build()
    this.navigate("$otpValidationScreen/$encodedPhoneNumber", navOptions)
}

internal fun NavGraphBuilder.otpValidationScreen(
    onOtpValidationSuccess: (phoneNumber: String, otp: String) -> Unit,
    onPopOtpValidationScreen: () -> Unit
) {
    composable(
        route = "$otpValidationScreen/{$phoneNumberArg}",
        arguments = listOf(
            navArgument(phoneNumberArg) { type = NavType.StringType },
        )
    ) {
        Log.d("otp debug argument", "${it.arguments?.getString(phoneNumberArg)}")
        it.arguments?.getString(phoneNumberArg)?.let { phoneNumber: String ->
            OtpValidationScreen(
                phoneNumber = phoneNumber,
                onOtpValidationSuccess = onOtpValidationSuccess,
                onBackButtonClick = onPopOtpValidationScreen
            )
        }
    }
}

/**
 * Set New Pass Code
 */
internal fun NavController.navigateToSetNewPassCodeScreen(phoneNumber: String, otp: String) {
    val encodedPhoneNumber = Uri.encode(phoneNumber)
    val encodedOtp = Uri.encode(otp)
    //Pop up to Login screen
    val navOptions = NavOptions.Builder().setPopUpTo(this.graph.findStartDestination().id, false).build()
    this.navigate("$setNewPassCodeScreen/$encodedPhoneNumber/$encodedOtp", navOptions)
}

internal fun NavGraphBuilder.setNewPassCodeScreen(
    onSetNewPassCodeSuccess: (String) -> Unit,
    onPopSetNewPassCodeScreen: () -> Unit
) {
    composable(
        route = "$setNewPassCodeScreen/{$phoneNumberArg}/{$otpArg}",
        arguments = listOf(
            navArgument(phoneNumberArg) { type = NavType.StringType },
            navArgument(otpArg) { type = NavType.StringType },
        )
    ) {
        it.arguments?.getString(phoneNumberArg)?.let { phoneNumber: String ->
            it.arguments?.getString(otpArg)?.let { otp: String ->
                SetNewPassCodeScreen(
                    phoneNumber = phoneNumber,
                    otp = otp,
                    onSetNewPassCodeSuccess = onSetNewPassCodeSuccess,
                    onBackClick = onPopSetNewPassCodeScreen
                )
            }
        }
    }
}

/**
 * Successful
 */
internal fun NavController.navigateToSuccessfulRoute(message: String) {
    val encodedSuccessMessage = Uri.encode(message)
    this.navigate("$successfulScreen/$encodedSuccessMessage")
}

internal fun NavGraphBuilder.successfulScreen(
    onBackToLoginClick: () -> Unit
) {
    composable(
        route = "$successfulScreen/{$successMessageArg}",
        arguments = listOf(
            navArgument(successMessageArg) { type = NavType.StringType },
        )
    ) {
        val message = it.arguments?.getString(successMessageArg)
        SuccessfulScreen(
            message = message ?: "Passcode Reset Successfully",
            subMessage = "You have successfully reset your account passcode. Login to continue",
            buttonText = "Back to login",
            onButtonClick = onBackToLoginClick
        )
    }
}

/**
 * Create New Pass Code
 */
internal fun NavController.navigateToCreateNewPassCodeScreen(topicId: String) {
    this.navigate(loginScreen)
}

internal fun NavGraphBuilder.createNewPassCodeScreen() {
    composable(
        route = createNewPassCodeScreen
    ) {
        CreateNewPassCodeScreen()
    }
}

/**
 * Set PIN
 */
internal fun NavController.navigateToSetPinRoute(topicId: String) {
    this.navigate(setPinScreen)
}

internal fun NavGraphBuilder.setPinScreen() {
    composable(
        route = setPinScreen
    ) {
        SetPinScreen()
    }
}


/**
 * Confirm PIN
 */
internal fun NavController.navigateToConfirmPinRoute(topicId: String) {
    this.navigate(confirmPinScreen)
}

internal fun NavGraphBuilder.confirmPinScreen() {
    composable(
        route = confirmPinScreen
    ) {
        ConfirmPinScreen()
    }
}




