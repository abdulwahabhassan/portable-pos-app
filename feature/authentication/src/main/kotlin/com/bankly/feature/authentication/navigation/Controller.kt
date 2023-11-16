package com.bankly.feature.authentication.navigation

import android.net.Uri
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions

internal fun NavHostController.navigateToRecoverPassCodeRoute() {
    this.navigate(recoverPassCodeRoute)
}

internal fun NavHostController.navigateToOtpValidationRoute(phoneNumber: String) {
    val encodedPhoneNumber = Uri.encode(phoneNumber)
    this.navigate(
        route = "$otpValidationRoute/$encodedPhoneNumber",
    )
}

internal fun NavHostController.navigateToSetNewPassCodeRoute(phoneNumber: String, otp: String) {
    val encodedPhoneNumber = Uri.encode(phoneNumber)
    val encodedOtp = Uri.encode(otp)
    this.navigate(
        route = "$setNewPassCodeRoute/$encodedPhoneNumber/$encodedOtp",
        navOptions = popUpToStartDestinationNavOption(this),
    )
}

internal fun NavHostController.navigateToSuccessfulRoute(message: String) {
    val encodedSuccessMessage = Uri.encode(message)
    this.navigate(
        route = "$successfulRoute/$encodedSuccessMessage",
        navOptions = popUpToStartDestinationNavOption(this),
    )
}
internal fun NavHostController.navigateToUnassignedTerminalRoute() {
    this.navigate(unassignedTerminalRoute)
}

internal fun NavHostController.navigateToSetPinRoute(defaultPin: String) {
    val encodedDefaultPin = Uri.encode(defaultPin)
    this.navigate("$setPinRoute/$encodedDefaultPin")
}

internal fun NavHostController.navigateToConfirmPinRoute(defaultPin: String, newPin: String) {
    val encodedDefaultPin = Uri.encode(defaultPin)
    val encodedNewPin = Uri.encode(newPin)
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
