package com.bankly.feature.authentication.ui.confirmpin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.designsystem.component.BanklyAccessPinInputField
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklyNumericKeyboard
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun ConfirmPinRoute(
    viewModel: ConfirmPinViewModel = hiltViewModel(),
    onPinChangeSuccess: () -> Unit,
    onBackPress: () -> Unit,
    defaultPin: String,
    newPin: String,
    onSessionExpired: () -> Unit
) {

    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ConfirmPinScreen(
        onBackPress = onBackPress,
        screenState = screenState,
        onUiEvent = { uiEvent: ConfirmPinScreenEvent -> viewModel.sendEvent(uiEvent) },
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.oneShotState.onEach { oneShotState: ConfirmPinScreenOneShotState ->
            when (oneShotState) {
                is ConfirmPinScreenOneShotState.OnSetPinSuccess -> {
                    Toast.makeText(context, oneShotState.message, Toast.LENGTH_LONG).show()
                    onPinChangeSuccess()
                }

                ConfirmPinScreenOneShotState.OnSessionExpired -> {
                    onSessionExpired()
                }
            }
        }.launchIn(this)
    })

    LaunchedEffect(key1 = Unit, block = {
        viewModel.sendEvent(
            ConfirmPinScreenEvent.OnSetPins(
                defaultPin = defaultPin,
                newPin = newPin
            )
        )
    })
}

@Composable
private fun ConfirmPinScreen(
    onBackPress: () -> Unit,
    screenState: ConfirmPinScreenState,
    onUiEvent: (ConfirmPinScreenEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.msg_confirm_pin),
                currentPage = 2,
                totalPage = 2,
                subTitle = buildAnnotatedString {
                    append(stringResource(R.string.msg_confirm_your_access_pin_subtitle))
                },
                isLoading = screenState.isLoading
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))

                BanklyAccessPinInputField(
                    passCode = screenState.confirmPin,
                    isError = screenState.isConfirmPinError,
                    pinErrorMessage = screenState.pinErrorMessage
                )
            }

            item {
                BanklyNumericKeyboard(
                    onKeyPressed = { key ->
                        when (key) {
                            PassCodeKey.DELETE -> {
                                val index =
                                    screenState.confirmPin.indexOfLast { it.isNotEmpty() }
                                if (index != -1) {
                                    val newPin = screenState.confirmPin.toMutableList()
                                    newPin[index] = ""
                                    onUiEvent(
                                        ConfirmPinScreenEvent.OnEnterConfirmPin(
                                            confirmPin = newPin,
                                            screenState.newPin
                                        )
                                    )
                                }
                            }

                            PassCodeKey.DONE -> {
                                onUiEvent(
                                    ConfirmPinScreenEvent.OnDoneClick(
                                        screenState.defaultPin,
                                        screenState.newPin,
                                        screenState.confirmPin
                                    ),
                                )
                            }

                            else -> {
                                val index =
                                    screenState.confirmPin.indexOfFirst { it.isEmpty() }
                                if (index != -1) {
                                    val newPin = screenState.confirmPin.toMutableList()
                                    newPin[index] = key.value
                                    onUiEvent(
                                        ConfirmPinScreenEvent.OnEnterConfirmPin(
                                            confirmPin = newPin,
                                            screenState.newPin
                                        )
                                    )
                                }
                            }
                        }
                    },
                    isDoneKeyEnabled = screenState.isDoneButtonEnabled,
                )
            }
        }
    }
    BanklyCenterDialog(
        title = stringResource(R.string.title_error),
        subtitle = screenState.errorDialogMessage,
        showDialog = screenState.showErrorDialog,
        icon = BanklyIcons.ErrorAlert,
        positiveActionText = stringResource(R.string.action_dismiss),
        positiveAction = {
            onUiEvent(ConfirmPinScreenEvent.OnDismissErrorDialog)
        },
        negativeAction = onBackPress,
        onDismissDialog = {
            onUiEvent(ConfirmPinScreenEvent.OnDismissErrorDialog)
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun ConfirmPinScreenPreview() {
    BanklyTheme {
        ConfirmPinScreen(
            onBackPress = {},
            screenState = ConfirmPinScreenState(),
            onUiEvent = {},
        )
    }
}
