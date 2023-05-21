package com.bankly.feature.authentication.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bankly.feature.authentication.ui.ConfirmPinScreen
import com.bankly.feature.authentication.ui.CreateNewPassCodeScreen
import com.bankly.feature.authentication.ui.InputPassCodeScreen
import com.bankly.feature.authentication.ui.InputPhoneNumberScreen
import com.bankly.feature.authentication.ui.LoginScreen
import com.bankly.feature.authentication.ui.SetNewPassCodeScreen
import com.bankly.feature.authentication.ui.SetPinScreen
import com.bankly.feature.authentication.ui.SuccessfulScreen

const val authenticationNavGraph = "authentication_graph"
const val loginScreen = authenticationNavGraph.plus("/login_screen")
const val confirmPinScreen = authenticationNavGraph.plus("/confirm_pin_screen")
const val createNewPassCodeScreen = authenticationNavGraph.plus("/create_new_pass_code_screen")
const val inputPassCodeScreen = authenticationNavGraph.plus("/input_pass_code_screen")
const val inputPhoneNumberScreen = authenticationNavGraph.plus("/input_phone_number_screen")
const val setNewPassCodeScreen = authenticationNavGraph.plus("/set_new_pass_code_screen")
const val setPinScreen = authenticationNavGraph.plus("/set_pin_route")
const val successfulScreen = authenticationNavGraph.plus("/successful_screen")

fun NavGraphBuilder.authenticationNavGraph(
    onLoginSuccess: () -> Unit,
    onLoginError: (String) -> Unit,
) {
    navigation(
        route = authenticationNavGraph,
        startDestination = loginScreen,
    ) {
        loginScreen(onLoginSuccess = onLoginSuccess, onLoginError = onLoginError)
        confirmPinScreen()
        createNewPassCodeScreen()
        inputPassCodeScreen()
        inputPhoneNumberScreen()
        successfulScreen()
        setNewPassCodeScreen()
        setPinScreen()
    }
}

internal fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit,
    onLoginError: (String) -> Unit,
) {
    composable(route = loginScreen) {
        LoginScreen(onLoginSuccess = onLoginSuccess, onLoginError = onLoginError)
    }
}

internal fun NavGraphBuilder.confirmPinScreen() {
    composable(
        route = confirmPinScreen
    ) {
        ConfirmPinScreen()
    }
}

internal fun NavGraphBuilder.createNewPassCodeScreen() {
    composable(
        route = createNewPassCodeScreen
    ) {
        CreateNewPassCodeScreen()
    }
}

internal fun NavGraphBuilder.inputPassCodeScreen() {
    composable(
        route = inputPassCodeScreen,
    ) {
        InputPassCodeScreen()
    }
}

internal fun NavGraphBuilder.inputPhoneNumberScreen() {
    composable(
        route = inputPhoneNumberScreen,
    ) {
        InputPhoneNumberScreen()
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

internal fun NavController.navigateToCreateNewPassCodeRoute(topicId: String) {
    this.navigate(createNewPassCodeScreen)
}

internal fun NavController.navigateToInputPassCodeRoute(topicId: String) {
    this.navigate(inputPassCodeScreen)
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


