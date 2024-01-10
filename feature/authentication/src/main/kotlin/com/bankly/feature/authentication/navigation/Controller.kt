package com.bankly.feature.authentication.navigation

import android.net.Uri
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal fun NavHostController.navigateToLoginRoute(
    navOptions: NavOptions? = null,
) {
    this.navigate(loginRoute, navOptions)
}

internal fun NavHostController.navigateToRecoverPassCodeRoute() {
    this.navigate(recoverPassCodeRoute)
}

internal fun NavHostController.navigateToOtpValidationRoute(phoneNumber: String) {
    val phoneNumberString = Json.encodeToString(phoneNumber)
    val encodedPhoneNumber = Uri.encode(phoneNumberString)
    this.navigate(
        route = "$otpValidationRoute/$encodedPhoneNumber",
    )
}

internal fun NavHostController.navigateToSetNewPassCodeRoute(phoneNumber: String, otp: String) {
    val encodedPhoneNumber = Uri.encode(Json.encodeToString(phoneNumber))
    val encodedOtp = Uri.encode(Json.encodeToString(otp))
    this.navigate(
        route = "$setNewPassCodeRoute/$encodedPhoneNumber/$encodedOtp",
        navOptions = popUpToStartDestinationNavOption(this),
    )
}

internal fun NavHostController.navigateToSuccessfulRoute(message: String) {
    val encodedSuccessMessage = Uri.encode(Json.encodeToString(message))
    this.navigate(
        route = "$successfulRoute/$encodedSuccessMessage",
        navOptions = popUpToStartDestinationNavOption(this),
    )
}
internal fun NavHostController.navigateToUnassignedTerminalRoute() {
    this.navigate(unassignedTerminalRoute)
}

internal fun NavHostController.navigateToSetPinRoute(defaultPin: String) {
    val encodedDefaultPin = Uri.encode(Json.encodeToString(defaultPin))
    this.navigate("$setPinRoute/$encodedDefaultPin")
}

internal fun NavHostController.navigateToConfirmPinRoute(defaultPin: String, newPin: String) {
    val encodedDefaultPin = Uri.encode(Json.encodeToString(defaultPin))
    val encodedNewPin = Uri.encode(Json.encodeToString(newPin))
    this.navigate("$confirmPinRoute/$encodedDefaultPin/$encodedNewPin")
}

internal fun NavHostController.navigateToCreateNewPassCodeRoute() {
    this.navigate(createNewPassCodeRoute)
}

/**
 * Nav option to Pop up to authentication navigation graph start destination (login route)
 */
internal fun popUpToStartDestinationNavOption(navController: NavHostController): NavOptions {
    return NavOptions.Builder().setPopUpTo(navController.graph.findStartDestination().id, false)
        .build()
}
