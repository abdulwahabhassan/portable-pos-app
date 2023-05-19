package com.bankly.feature.authentication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bankly.feature.authentication.ui.ConfirmPinScreen
import com.bankly.feature.authentication.ui.CreateNewPassCodeScreen
import com.bankly.feature.authentication.ui.InputPassCodeScreen
import com.bankly.feature.authentication.ui.InputPhoneNumberScreen
import com.bankly.feature.authentication.ui.LoginRoute
import com.bankly.feature.authentication.ui.SetNewPassCodeScreen
import com.bankly.feature.authentication.ui.SetPinScreen
import com.bankly.feature.authentication.ui.SuccessfulScreen

const val authenticationNavGraphRoutePattern = "authentication_graph"
const val loginRoute = "login_route"
const val confirmPinRoute = "confirm_pin_route"
const val createNewPassCodeRoute = "create_new_pass_code_route"
const val inputPassCodeRoute = "input_pass_code_route"
const val inputPhoneNumberRoute = "input_phone_number_route"
const val setNewPassCodeRoute = "set_new_pass_code_route"
const val setPinRoute = "set_pin_route"
const val successfulRoute = "successful_route"

fun NavController.navigateToAuthenticationNavGraph(navOptions: NavOptions? = null) {
    this.navigate(authenticationNavGraphRoutePattern, navOptions)
}

fun NavController.confirmPinRoute(topicId: String) {
    this.navigate(confirmPinRoute)
}
fun NavController.createNewPassCodeRoute(topicId: String) {
    this.navigate(createNewPassCodeRoute)
}

fun NavController.inputPassCodeRoute(topicId: String) {
    this.navigate(inputPassCodeRoute)
}

fun NavController.inputPhoneNumberRoute(topicId: String) {
    this.navigate(inputPhoneNumberRoute)
}

fun NavController.setNewPassCodeRoute(topicId: String) {
    this.navigate(setNewPassCodeRoute)
}

fun NavController.setPinRoute(topicId: String) {
    this.navigate(setPinRoute)
}

fun NavController.successfulRoute(topicId: String) {
    this.navigate(successfulRoute)
}

fun NavGraphBuilder.authenticationNavGraph(
    onLoginClick: (phoneNumber: String, passCode: String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = authenticationNavGraphRoutePattern,
        startDestination = loginRoute,
    ) {
        composable(route = loginRoute) {
            LoginRoute(onLoginClick = onLoginClick)
        }
        nestedGraphs()
    }
}

fun NavGraphBuilder.confirmPinScreen() {
    composable(
        route = confirmPinRoute
    ) {
        ConfirmPinScreen()
    }
}

fun NavGraphBuilder.createNewPassCodeScreen() {
    composable(
        route = createNewPassCodeRoute
    ) {
        CreateNewPassCodeScreen()
    }
}

fun NavGraphBuilder.inputPassCodeScreen() {
    composable(
        route = inputPassCodeRoute,
    ) {
        InputPassCodeScreen()
    }
}

fun NavGraphBuilder.inputPhoneNumberScreen() {
    composable(
        route = inputPhoneNumberRoute,
    ) {
        InputPhoneNumberScreen()
    }
}

fun NavGraphBuilder.successfulScreen() {
    composable(
        route = successfulRoute
    ) {
        SuccessfulScreen(message = "Successful", subMessage = "Proud of you!", buttonText = "Click me!") {

        }
    }
}

fun NavGraphBuilder.setNewPassCodeScreen() {
    composable(
        route = setNewPassCodeRoute,
    ) {
        SetNewPassCodeScreen()
    }
}

fun NavGraphBuilder.setPinScreen() {
    composable(
        route = setPinRoute
    ) {
        SetPinScreen()
    }
}


