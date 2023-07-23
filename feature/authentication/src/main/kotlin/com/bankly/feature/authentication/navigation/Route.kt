package com.bankly.feature.authentication.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.feature.authentication.ui.createnewpasscode.CreateNewPassCodeScreen
import com.bankly.feature.authentication.ui.login.LoginRoute
import com.bankly.feature.authentication.ui.pin.ConfirmPinScreen
import com.bankly.feature.authentication.ui.pin.SetPinScreen
import com.bankly.feature.authentication.ui.recoverpasscode.RecoverPassCodeRoute
import com.bankly.feature.authentication.ui.setnewpasscode.SetNewPassCodeRoute
import com.bankly.feature.authentication.ui.success.SuccessfulRoute
import com.bankly.feature.authentication.ui.validateotp.OtpValidationRoute

const val authenticationNavGraphRoute = "authentication_nav_graph"
const val authenticationRoute = authenticationNavGraphRoute.plus("/auth_route")
const val loginRoute = authenticationRoute.plus("/login_screen")
const val confirmPinRoute = authenticationRoute.plus("/confirm_pin_screen")
const val createNewPassCodeRoute = authenticationRoute.plus("/create_new_pass_code_screen")
const val otpValidationRoute = authenticationRoute.plus("/otp_validation_screen")
const val recoverPassCodeRoute = authenticationRoute.plus("/input_phone_number_screen")
const val setNewPassCodeRoute = authenticationRoute.plus("/set_new_pass_code_screen")
const val setPinRoute = authenticationRoute.plus("/set_pin_screen")
const val successfulRoute = authenticationRoute.plus("/successful_screen")

internal fun NavGraphBuilder.loginRoute(
    onLoginSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onRecoverPassCodeClick: () -> Unit
) {
    composable(route = loginRoute) {
        LoginRoute(
            onLoginSuccess = onLoginSuccess,
            onBackPress = onBackPress,
            onRecoverPassCodeClick = onRecoverPassCodeClick
        )
    }
}

internal fun NavGraphBuilder.recoverPassCodeRoute(
    onRecoverPassCodeSuccess: (String) -> Unit,
    onBackPress: () -> Unit
) {
    composable(
        route = recoverPassCodeRoute,
    ) {
        RecoverPassCodeRoute(
            onRecoverPassCodeSuccess = onRecoverPassCodeSuccess,
            onBackPress = onBackPress
        )
    }
}

internal fun NavGraphBuilder.otpValidationRoute(
    onOtpValidationSuccess: (phoneNumber: String, otp: String) -> Unit,
    onBackPress: () -> Unit
) {
    composable(
        route = "$otpValidationRoute/{$phoneNumberArg}",
        arguments = listOf(
            navArgument(phoneNumberArg) { type = NavType.StringType },
        )
    ) {
        it.arguments?.getString(phoneNumberArg)?.let { phoneNumber: String ->
            OtpValidationRoute(
                phoneNumber = phoneNumber,
                onOtpValidationSuccess = onOtpValidationSuccess,
                onBackPress = onBackPress
            )
        }
    }
}

internal fun NavGraphBuilder.setNewPassCodeRoute(
    onSetNewPassCodeSuccess: (String) -> Unit,
    onBackPress: () -> Unit
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
                SetNewPassCodeRoute(
                    phoneNumber = phoneNumber,
                    otp = otp,
                    onSetNewPassCodeSuccess = onSetNewPassCodeSuccess,
                    onBackPress = onBackPress
                )
            }
        }
    }
}

internal fun NavGraphBuilder.successfulRoute(
    onBackToLoginClick: () -> Unit
) {
    composable(
        route = "$successfulRoute/{$successMessageArg}",
        arguments = listOf(
            navArgument(successMessageArg) { type = NavType.StringType },
        )
    ) {
        val message = it.arguments?.getString(successMessageArg)
        SuccessfulRoute(
            message = message ?: "Passcode Reset Successfully",
            subMessage = "You have successfully reset your account passcode. Login to continue",
            buttonText = "Back to login",
            onBackToLoginClick = onBackToLoginClick
        )
    }
}

internal fun NavGraphBuilder.createNewPassCodeRoute() {
    composable(
        route = createNewPassCodeRoute
    ) {
        CreateNewPassCodeScreen()
    }
}

internal fun NavGraphBuilder.setPinRoute() {
    composable(
        route = setPinRoute
    ) {
        SetPinScreen()
    }
}

internal fun NavGraphBuilder.confirmPinRoute() {
    composable(
        route = confirmPinRoute
    ) {
        ConfirmPinScreen()
    }
}
