package com.bankly.feature.authentication.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bankly.feature.authentication.R
import com.bankly.feature.authentication.ui.confirmpin.ConfirmPinRoute
import com.bankly.feature.authentication.ui.createnewpasscode.CreateNewPassCodeScreen
import com.bankly.feature.authentication.ui.login.LoginRoute
import com.bankly.feature.authentication.ui.recoverpasscode.RecoverPassCodeRoute
import com.bankly.feature.authentication.ui.setnewpasscode.SetNewPassCodeRoute
import com.bankly.feature.authentication.ui.setuppin.SetPinRoute
import com.bankly.feature.authentication.ui.success.SuccessRoute
import com.bankly.feature.authentication.ui.unassignedterminal.UnassignedTerminalRoute
import com.bankly.feature.authentication.ui.validateotp.OtpValidationRoute
import com.bankly.feature.authentication.ui.validatepasscode.ValidatePassCodeRoute

const val authenticationNavGraphRoute = "authentication_nav_graph"
internal const val authenticationRoute = authenticationNavGraphRoute.plus("/auth_route")
internal const val loginRoute = authenticationRoute.plus("/login_screen")
internal const val confirmPinRoute = authenticationRoute.plus("/confirm_pin_screen")
internal const val createNewPassCodeRoute = authenticationRoute.plus("/create_new_pass_code_screen")
internal const val otpValidationRoute = authenticationRoute.plus("/otp_validation_screen")
internal const val recoverPassCodeRoute = authenticationRoute.plus("/input_phone_number_screen")
internal const val setNewPassCodeRoute = authenticationRoute.plus("/set_new_pass_code_screen")
internal const val setPinRoute = authenticationRoute.plus("/set_pin_screen")
internal const val successfulRoute = authenticationRoute.plus("/successful_screen")
const val validatePassCodeRoute = authenticationRoute.plus("/validate_passcode_screen")
internal const val unassignedTerminalRoute = authenticationRoute.plus("/unassigned_terminal_screen")

internal fun NavGraphBuilder.loginRoute(
    onLoginSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onSetUpAccessPin: (defaultPin: String) -> Unit,
    onTerminalUnAssigned: () -> Unit
) {
    composable(route = loginRoute) {
        LoginRoute(
            onLoginSuccess = onLoginSuccess,
            onBackPress = onBackPress,
            onSetUpAccessPin = onSetUpAccessPin,
            onTerminalUnAssigned = onTerminalUnAssigned
        )
    }
}

internal fun NavGraphBuilder.recoverPassCodeRoute(
    onRecoverPassCodeSuccess: (String) -> Unit,
    onBackPress: () -> Unit,
) {
    composable(
        route = recoverPassCodeRoute,
    ) {
        RecoverPassCodeRoute(
            onRecoverPassCodeSuccess = onRecoverPassCodeSuccess,
            onBackPress = onBackPress,
        )
    }
}

internal fun NavGraphBuilder.otpValidationRoute(
    onOtpValidationSuccess: (phoneNumber: String, otp: String) -> Unit,
    onBackPress: () -> Unit,
) {
    composable(
        route = "$otpValidationRoute/{$phoneNumberArg}",
        arguments = listOf(
            navArgument(phoneNumberArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(phoneNumberArg)?.let { phoneNumber: String ->
            OtpValidationRoute(
                phoneNumber = phoneNumber,
                onOtpValidationSuccess = onOtpValidationSuccess,
                onBackPress = onBackPress,
            )
        }
    }
}

internal fun NavGraphBuilder.setNewPassCodeRoute(
    onSetNewPassCodeSuccess: (String) -> Unit,
    onBackPress: () -> Unit,
) {
    composable(
        route = "$setNewPassCodeRoute/{$phoneNumberArg}/{$otpArg}",
        arguments = listOf(
            navArgument(phoneNumberArg) { type = NavType.StringType },
            navArgument(otpArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(phoneNumberArg)?.let { phoneNumber: String ->
            it.arguments?.getString(otpArg)?.let { otp: String ->
                SetNewPassCodeRoute(
                    phoneNumber = phoneNumber,
                    otp = otp,
                    onSetNewPassCodeSuccess = onSetNewPassCodeSuccess,
                    onBackPress = onBackPress,
                )
            }
        }
    }
}

internal fun NavGraphBuilder.successfulRoute(
    onBackToLoginClick: () -> Unit,
) {
    composable(
        route = "$successfulRoute/{$successMessageArg}",
        arguments = listOf(
            navArgument(successMessageArg) { type = NavType.StringType },
        ),
    ) {
        val context = LocalContext.current
        val message = it.arguments?.getString(successMessageArg)
        SuccessRoute(
            message = message ?: context.getString(R.string.msg_passcode_reset_successfully),
            subMessage = context.getString(R.string.msg_you_have_successfully_reset_your_account_passcode_login_to_continue),
            buttonText = context.getString(R.string.sction_back_to_login),
            onBackToLoginClick = onBackToLoginClick,
        )
    }
}

internal fun NavGraphBuilder.createNewPassCodeRoute() {
    composable(
        route = createNewPassCodeRoute,
    ) {
        CreateNewPassCodeScreen()
    }
}

internal fun NavGraphBuilder.setPinRoute(
    onGoToConfirmPinScreen: (defaultPin: String, newPin: String) -> Unit,
    onBackPress: () -> Unit
) {
    composable(
        route = "$setPinRoute/{$defaultPinArg}",
        arguments = listOf(
            navArgument(defaultPinArg) { type = NavType.StringType },
        ),
    ) {
        it.arguments?.getString(defaultPinArg)?.let { defaultPin: String ->
            SetPinRoute(
                onBackPress = onBackPress,
                onGoToConfirmPinScreen = onGoToConfirmPinScreen,
                defaultPin = defaultPin,
            )
        }
    }
}

internal fun NavGraphBuilder.confirmPinRoute(
    onBackPress: () -> Unit,
    onPinChangeSuccess: () -> Unit,
    onSessionExpired: () -> Unit
) {
    composable(
        route = "$confirmPinRoute/{$defaultPinArg}/{$newPinArg}",
        arguments = listOf(
            navArgument(defaultPinArg) { type = NavType.StringType },
            navArgument(newPinArg) { type = NavType.StringType },
        ),
    ) {

        it.arguments?.getString(defaultPinArg)?.let { defaultPin: String ->
            it.arguments?.getString(newPinArg)?.let { newPin: String ->
                ConfirmPinRoute(
                    onBackPress = onBackPress,
                    onPinChangeSuccess = onPinChangeSuccess,
                    defaultPin = defaultPin,
                    newPin = newPin,
                    onSessionExpired = onSessionExpired
                )
            }
        }
    }
}

internal fun NavGraphBuilder.validatePassCodeRoute(
    onGoToSettingsRoute: () -> Unit,
    onBackPress: () -> Unit,
) {
    composable(
        route = validatePassCodeRoute,
    ) {
        ValidatePassCodeRoute(
            onBackPress = onBackPress,
            onGoToSettingsScreen = onGoToSettingsRoute
        )
    }
}

internal fun NavGraphBuilder.unassignedTerminalRoute(
    onGoToBackPress: () -> Unit,
    onContactSupportPress: () -> Unit
) {
    composable(
        route = unassignedTerminalRoute,
    ) {
        UnassignedTerminalRoute(
            onGoToBackPress = onGoToBackPress,
            onContactSupportPress = onContactSupportPress,
        )
    }
}
