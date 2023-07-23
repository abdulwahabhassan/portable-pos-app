package com.bankly.feature.authentication.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions

internal fun NavController.navigateToSuccessfulRoute(message: String) {
    val encodedSuccessMessage = Uri.encode(message)
    this.navigate(
        route = "$successfulRoute/$encodedSuccessMessage",
        navOptions = popUpToStartDestinationNavOption(this)
    )
}

internal fun NavController.navigateToSetNewPassCodeRoute(phoneNumber: String, otp: String) {
    val encodedPhoneNumber = Uri.encode(phoneNumber)
    val encodedOtp = Uri.encode(otp)
    this.navigate(
        route = "$setNewPassCodeRoute/$encodedPhoneNumber/$encodedOtp",
        navOptions = popUpToStartDestinationNavOption(this)
    )
}

internal fun NavController.navigateToOtpValidationRoute(phoneNumber: String) {
    val encodedPhoneNumber = Uri.encode(phoneNumber)
    this.navigate(
        route = "$otpValidationRoute/$encodedPhoneNumber",
        navOptions = popUpToStartDestinationNavOption(this)
    )
}

internal fun NavController.navigateToLoginRoute(topicId: String) {
    this.navigate(loginRoute)
}

internal fun NavController.navigateToRecoverPassCodeRoute() {
    this.navigate(recoverPassCodeRoute)
}

internal fun NavController.navigateToConfirmPinRoute(topicId: String) {
    this.navigate(confirmPinRoute)
}

internal fun NavController.navigateToSetPinRoute(topicId: String) {
    this.navigate(setPinRoute)
}

internal fun NavController.navigateToCreateNewPassCodeRoute(topicId: String) {
    this.navigate(createNewPassCodeRoute)
}

/**
 * Nav option to Pop up to authentication navigation graph start destination (login route)
 */
internal fun popUpToStartDestinationNavOption(navController: NavController): NavOptions {
    return NavOptions.Builder().setPopUpTo(navController.graph.findStartDestination().id, false)
        .build()
}