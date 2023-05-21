package com.bankly.feature.authentication.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.bankly.feature.authentication.ui.ConfirmPinScreen
import com.bankly.feature.authentication.ui.CreateNewPassCodeScreen
import com.bankly.feature.authentication.ui.recover.OtpValidationScreen
import com.bankly.feature.authentication.ui.recover.InputPhoneNumberScreen
import com.bankly.feature.authentication.ui.login.LoginScreen
import com.bankly.feature.authentication.ui.SetNewPassCodeScreen
import com.bankly.feature.authentication.ui.SetPinScreen
import com.bankly.feature.authentication.ui.SuccessfulScreen

const val authenticationNavGraph = "authentication_graph"
const val loginScreen = authenticationNavGraph.plus("/login_screen")
const val confirmPinScreen = authenticationNavGraph.plus("/confirm_pin_screen")
const val createNewPassCodeScreen = authenticationNavGraph.plus("/create_new_pass_code_screen")
const val otpValidationScreen = authenticationNavGraph.plus("/otp_validation_screen")
const val inputPhoneNumberScreen = authenticationNavGraph.plus("/input_phone_number_screen")
const val setNewPassCodeScreen = authenticationNavGraph.plus("/set_new_pass_code_screen")
const val setPinScreen = authenticationNavGraph.plus("/set_pin_route")
const val successfulScreen = authenticationNavGraph.plus("/successful_screen")


internal const val phoneNumberArg = "phoneNumber"

fun NavGraphBuilder.authenticationNavGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit,
) {
    navigation(
        route = authenticationNavGraph,
        startDestination = inputPhoneNumberScreen,
    ) {
        loginScreen(onLoginSuccess = onLoginSuccess, onBackClick = onBackClick)
        confirmPinScreen()
        createNewPassCodeScreen()
        otpValidationScreen(onOtpValidationSuccess = { phoneNumber: String ->
            navController.navigateToCreateNewPassCodeRoute(phoneNumber = phoneNumber)
        })
        inputPhoneNumberScreen(onRecoverPassCodeSuccess = { phoneNumber: String ->
            navController.navigateToOtpValidationScreen(phoneNumber = phoneNumber)
        })
        successfulScreen()
        setNewPassCodeScreen()
        setPinScreen()
    }
}

internal fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    composable(route = loginScreen) {
        LoginScreen(onLoginSuccess = onLoginSuccess, onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.confirmPinScreen() {
    composable(
        route = confirmPinScreen
    ) {
        ConfirmPinScreen()
    }
}

/**
 * Call [navigateToCreateNewPassCodeRoute]  to navigate to this screen
 */
internal fun NavController.navigateToCreateNewPassCodeRoute(phoneNumber: String) {
    val encodedPhoneNumber = Uri.encode(phoneNumber)
    this.navigate("$createNewPassCodeScreen/$encodedPhoneNumber")
}

/**
 * [CreateNewPassCodeScreen] takes nav argument [phoneNumberArg] of type [String]
 */
internal fun NavGraphBuilder.createNewPassCodeScreen() {
    composable(
        route = "$createNewPassCodeScreen/{$phoneNumberArg}",
        arguments = listOf(
            navArgument(phoneNumberArg) { type = NavType.StringType },
        )
    ) {
        CreateNewPassCodeScreen()
    }
}

/**
 * Call [navigateToOtpValidationScreen]  to navigate to this screen
 */
internal fun NavController.navigateToOtpValidationScreen(phoneNumber: String) {
    val encodedPhoneNumber = Uri.encode(phoneNumber)
    this.navigate("$otpValidationScreen/$encodedPhoneNumber")
}

/**
 * [OtpValidationScreen] takes nav argument [phoneNumberArg] of type [String]
 */
internal fun NavGraphBuilder.otpValidationScreen(onOtpValidationSuccess: (String) -> Unit) {
    composable(
        route = "$otpValidationScreen/{$phoneNumberArg}",
        arguments = listOf(
            navArgument(phoneNumberArg) { type = NavType.StringType },
        )
    ) {
        it.arguments?.getString(phoneNumberArg)?.let { phoneNumber: String ->
            OtpValidationScreen(
                phoneNumber = phoneNumber,
                onOtpValidationSuccess = onOtpValidationSuccess
            )
        }
    }
}

internal fun NavGraphBuilder.inputPhoneNumberScreen(
    onRecoverPassCodeSuccess: (String) -> Unit
) {
    composable(
        route = inputPhoneNumberScreen,
    ) {
        InputPhoneNumberScreen(
            onRecoverPassCodeSuccess = onRecoverPassCodeSuccess
        )
    }
}

internal fun NavGraphBuilder.successfulScreen() {
    composable(
        route = successfulScreen
    ) {
        SuccessfulScreen(
            message = "Successful",
            subMessage = "Proud of you!",
            buttonText = "Click me!"
        ) {

        }
    }
}

internal fun NavGraphBuilder.setNewPassCodeScreen() {
    composable(
        route = setNewPassCodeScreen,
    ) {
        SetNewPassCodeScreen()
    }
}

internal fun NavGraphBuilder.setPinScreen() {
    composable(
        route = setPinScreen
    ) {
        SetPinScreen()
    }
}

internal fun NavController.navigateToConfirmPinRoute(topicId: String) {
    this.navigate(confirmPinScreen)
}

internal fun NavController.navigateToInputPhoneNumberRoute(topicId: String) {
    this.navigate(inputPhoneNumberScreen)
}

internal fun NavController.navigateToSetNewPassCodeRoute(topicId: String) {
    this.navigate(setNewPassCodeScreen)
}

internal fun NavController.navigateToSetPinRoute(topicId: String) {
    this.navigate(setPinScreen)
}

internal fun NavController.navigateToSuccessfulRoute(topicId: String) {
    this.navigate(successfulScreen)
}


