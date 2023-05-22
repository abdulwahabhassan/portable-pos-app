package com.bankly.feature.authentication.navigation

import android.net.Uri
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.bankly.feature.authentication.ui.ConfirmPinScreen
import com.bankly.feature.authentication.ui.CreateNewPassCodeScreen
import com.bankly.feature.authentication.ui.recover.OtpValidationScreen
import com.bankly.feature.authentication.ui.recover.RecoverPassCodeScreen
import com.bankly.feature.authentication.ui.login.LoginScreen
import com.bankly.feature.authentication.ui.recover.SetNewPassCodeScreen
import com.bankly.feature.authentication.ui.SetPinScreen
import com.bankly.feature.authentication.ui.SuccessfulScreen

const val authenticationNavGraph = "authentication_graph"
const val loginRoute = authenticationNavGraph.plus("/login_screen")
const val confirmPinRoute = authenticationNavGraph.plus("/confirm_pin_screen")
const val createNewPassCodeRoute = authenticationNavGraph.plus("/create_new_pass_code_screen")
const val otpValidationRoute = authenticationNavGraph.plus("/otp_validation_screen")
const val recoverPassCodeRoute = authenticationNavGraph.plus("/input_phone_number_screen")
const val setNewPassCodeRoute = authenticationNavGraph.plus("/set_new_pass_code_screen")
const val setPinRoute = authenticationNavGraph.plus("/set_pin_route")
const val successfulRoute = authenticationNavGraph.plus("/successful_screen")


internal const val phoneNumberArg = "phoneNumber"
internal const val otpArg = "otp"
internal const val successMessageArg = "successMessage"

fun NavGraphBuilder.authenticationNavGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
    onPopLoginScreen: () -> Unit,
    onBackToLoginClick: () -> Unit,
) {
    navigation(
        route = authenticationNavGraph,
        startDestination = loginRoute,
    ) {
        loginScreen(
            onLoginSuccess = onLoginSuccess,
            onBackClick = onPopLoginScreen,
            onRecoverPassCode = {
                navController.navigateToRecoverPassCodeScreen()
            })
        confirmPinScreen()
        createNewPassCodeScreen()
        otpValidationScreen(
            onOtpValidationSuccess = { phoneNumber: String, otp: String ->
                navController.navigateToSetNewPassCodeScreen(phoneNumber = phoneNumber, otp = otp)
            },
            onPopOtpValidationScreen = {
                navController.popBackStack()
            })
        recoverPassCodeScreen(
            onRecoverPassCodeSuccess = { phoneNumber: String ->
                navController.navigateToOtpValidationScreen(phoneNumber = phoneNumber)
            },
            onPopRecoverPassCodeScreen = {
                navController.navigateToRecoverPassCodeScreen()
            }
        )
        successfulScreen(onBackToLoginClick = onBackToLoginClick)
        setNewPassCodeScreen(
            onSetNewPassCodeSuccess = { message: String ->
                navController.navigateToSuccessfulRoute(message = message)
            },
            onPopSetNewPassCodeScreen = {
                navController.popBackStack()
            })
        setPinScreen()
    }
}

/**
 * Login
 */
internal fun NavController.navigateToLoginScreen(topicId: String) {
    this.navigate(loginRoute)
}

internal fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit,
    onRecoverPassCode: () -> Unit
) {
    composable(route = loginRoute) {
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
    this.navigate(recoverPassCodeRoute)
}

internal fun NavGraphBuilder.recoverPassCodeScreen(
    onRecoverPassCodeSuccess: (String) -> Unit,
    onPopRecoverPassCodeScreen: () -> Unit
) {
    composable(
        route = recoverPassCodeRoute,
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
    this.navigate("$otpValidationRoute/$encodedPhoneNumber", navOptions)
}

internal fun NavGraphBuilder.otpValidationScreen(
    onOtpValidationSuccess: (phoneNumber: String, otp: String) -> Unit,
    onPopOtpValidationScreen: () -> Unit
) {
    composable(
        route = "$otpValidationRoute/{$phoneNumberArg}",
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
    this.navigate("$setNewPassCodeRoute/$encodedPhoneNumber/$encodedOtp", navOptions)
}

internal fun NavGraphBuilder.setNewPassCodeScreen(
    onSetNewPassCodeSuccess: (String) -> Unit,
    onPopSetNewPassCodeScreen: () -> Unit
) {
    composable(
        route = "$setNewPassCodeRoute/{$phoneNumberArg}/{$otpArg}",
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
    this.navigate("$successfulRoute/$encodedSuccessMessage")
}

internal fun NavGraphBuilder.successfulScreen(
    onBackToLoginClick: () -> Unit
) {
    composable(
        route = "$successfulRoute/{$successMessageArg}",
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
    this.navigate(loginRoute)
}

internal fun NavGraphBuilder.createNewPassCodeScreen() {
    composable(
        route = createNewPassCodeRoute
    ) {
        CreateNewPassCodeScreen()
    }
}

/**
 * Set PIN
 */
internal fun NavController.navigateToSetPinRoute(topicId: String) {
    this.navigate(setPinRoute)
}

internal fun NavGraphBuilder.setPinScreen() {
    composable(
        route = setPinRoute
    ) {
        SetPinScreen()
    }
}


/**
 * Confirm PIN
 */
internal fun NavController.navigateToConfirmPinRoute(topicId: String) {
    this.navigate(confirmPinRoute)
}

internal fun NavGraphBuilder.confirmPinScreen() {
    composable(
        route = confirmPinRoute
    ) {
        ConfirmPinScreen()
    }
}




