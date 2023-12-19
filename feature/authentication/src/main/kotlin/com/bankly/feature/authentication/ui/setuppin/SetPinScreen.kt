package com.bankly.feature.authentication.ui.setuppin

import androidx.activity.compose.BackHandler
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
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.authentication.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun SetPinRoute(
    viewModel: SetPinViewModel = hiltViewModel(),
    onGoToConfirmPinScreen: (defaultPin: String, newPin: String) -> Unit,
    onBackPress: () -> Unit,
    defaultPin: String,
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()

    SetPinScreen(
        onBackPress = onBackPress,
        screenState = screenState,
        onUiEvent = { uiEvent: SetPinScreenEvent -> viewModel.sendEvent(uiEvent) },
    )

    LaunchedEffect(key1 = Unit, block = {
        viewModel.oneShotState.onEach { oneShotState: SetPinScreenOneShotState ->
            when (oneShotState) {
                is SetPinScreenOneShotState.OnGoToConfirmPinScreen -> onGoToConfirmPinScreen(
                    oneShotState.defaultPin,
                    oneShotState.newPin,
                )
            }
        }.launchIn(this)
    })

    LaunchedEffect(key1 = Unit, block = {
        viewModel.sendEvent(SetPinScreenEvent.SetDefaultPin(defaultPin))
    })
}

@Composable
private fun SetPinScreen(
    onBackPress: () -> Unit,
    screenState: SetPinScreenState,
    onUiEvent: (SetPinScreenEvent) -> Unit,
) {
    BackHandler {
        onUiEvent(SetPinScreenEvent.OnBackPress)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BanklyTitleBar(
                onBackPress = {
                    onUiEvent(SetPinScreenEvent.OnBackPress)
                },
                title = stringResource(R.string.msg_set_pin),
                currentPage = 1,
                totalPage = 2,
                subTitle = buildAnnotatedString {
                    append(stringResource(R.string.msg_set_up_your_access_pin_subtitle))
                },
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
                    passCode = screenState.newPin,
                    isError = screenState.isNewPinError,
                )
            }

            item {
                BanklyNumericKeyboard(
                    onKeyPressed = { key ->
                        when (key) {
                            PassCodeKey.DELETE -> {
                                val index =
                                    screenState.newPin.indexOfLast { it.isNotEmpty() }
                                if (index != -1) {
                                    val newPin = screenState.newPin.toMutableList()
                                    newPin[index] = ""
                                    onUiEvent(SetPinScreenEvent.OnEnterNewPin(pin = newPin))
                                }
                            }

                            PassCodeKey.DONE -> {
                                onUiEvent(
                                    SetPinScreenEvent.OnEnterNewPinDoneClick(
                                        screenState.defaultPin,
                                        screenState.newPin.joinToString(""),
                                    ),
                                )
                            }

                            else -> {
                                val index =
                                    screenState.newPin.indexOfFirst { it.isEmpty() }
                                if (index != -1) {
                                    val newPin = screenState.newPin.toMutableList()
                                    newPin[index] = key.value
                                    onUiEvent(SetPinScreenEvent.OnEnterNewPin(pin = newPin))
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
        title = stringResource(R.string.exit_warning),
        subtitle = stringResource(R.string.msg_are_you_sure_you_want_to_discontinue_setting_up_your_access_pin),
        showDialog = screenState.showOnBackPressScreenWarningDialog,
        icon = BanklyIcons.ErrorAlert,
        positiveActionText = stringResource(R.string.action_no),
        negativeActionText = stringResource(R.string.action_yes),
        positiveAction = {
            onUiEvent(SetPinScreenEvent.OnDismissOnBackPressWarningDialog)
        },
        negativeAction = onBackPress,
        onDismissDialog = {
            onUiEvent(SetPinScreenEvent.OnDismissOnBackPressWarningDialog)
        },
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun SetPinScreenPreview() {
    BanklyTheme {
        SetPinScreen(
            onBackPress = {},
            screenState = SetPinScreenState(),
            onUiEvent = {},
        )
    }
}
