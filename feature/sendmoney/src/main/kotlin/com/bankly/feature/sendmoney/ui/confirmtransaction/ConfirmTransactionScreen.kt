package com.bankly.feature.sendmoney.ui.confirmtransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.common.model.State
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyDetailRow
import com.bankly.core.designsystem.component.BanklyNumericKeyboard
import com.bankly.core.designsystem.component.BanklyPassCodeInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.model.ConfirmTransactionDetails
import com.bankly.feature.sendmoney.R


@Composable
fun ConfirmTransactionRoute(
    viewModel: ConfirmTransactionViewModel = hiltViewModel(),
    confirmTransactionDetails: ConfirmTransactionDetails,
    onConfirmationSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    ConfirmTransactionScreen(
        screenState = screenState,
        confirmTransactionDetails = confirmTransactionDetails,
        onConfirmationSuccess = onConfirmationSuccess,
        onBackPress = onBackPress,
        onCloseClick = onCloseClick,
        onUiEvent = { uiEvent ->
            viewModel.sendEvent(uiEvent)
        }
    )
}

@Composable
internal fun ConfirmTransactionScreen(
    screenState: ConfirmTransactionScreenState,
    confirmTransactionDetails: ConfirmTransactionDetails,
    onConfirmationSuccess: () -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
    onUiEvent: (ConfirmTransactionScreenEvent) -> Unit
) {

    if (screenState.shouldShowWarningDialog) {
        BanklyActionDialog(
            title = stringResource(id = R.string.title_cancel_transaction),
            subtitle = stringResource(id = R.string.msg_are_you_sure),
            positiveActionText = stringResource(R.string.action_no),
            positiveAction = {
                onUiEvent(ConfirmTransactionScreenEvent.OnDismissWarningDialog)
            },
            negativeActionText = stringResource(R.string.action_yes_cancel) ,
            negativeAction = {
                onCloseClick()
            }
        )
    }

    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.title_confirm_transaction),
                isLoading = screenState.isLoading,
                onCloseClick = {
                    onUiEvent(ConfirmTransactionScreenEvent.OnCloseClick)
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                    confirmTransactionDetails.toDetailsMap().filter { it.value.isNotEmpty() }
                        .forEach { (label, value) ->
                            BanklyDetailRow(label = label, value = value)
                        }
                }
            }

            item {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Divider(
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = stringResource(R.string.msg_enter_transaction_pin),
                            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
                        )
                        Divider(
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        BanklyPassCodeInputField(
                            passCode = screenState.pin
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BanklyClickableText(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = MaterialTheme.typography.bodyMedium.toSpanStyle()
                                        .copy(
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                ) {
                                    append(stringResource(R.string.action_forgot_pin))
                                }
                            },
                            backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                            onClick = { })
                    }

                    BanklyNumericKeyboard(
                        onKeyPressed = { key ->
                            when (key) {
                                PassCodeKey.DELETE -> {
                                    val index =
                                        screenState.pin.indexOfLast { it.isNotEmpty() }
                                    if (index != -1) {
                                        val newPin = screenState.pin.toMutableList()
                                        newPin[index] = ""
                                        onUiEvent(ConfirmTransactionScreenEvent.OnEnterPin(pin = newPin))
                                    }
                                }

                                PassCodeKey.DONE -> {
//                                    onUiEvent(
//                                        ConfirmTransactionScreenEvent.OnDoneClick(
//                                            screenState.pin.joinToString(""),
//                                        )
//                                    )
                                    onConfirmationSuccess()
                                }

                                else -> {
                                    val index =
                                        screenState.pin.indexOfFirst { it.isEmpty() }
                                    if (index != -1) {
                                        val newPin = screenState.pin.toMutableList()
                                        newPin[index] = key.value
                                        onUiEvent(ConfirmTransactionScreenEvent.OnEnterPin(pin = newPin))
                                    }
                                }
                            }
                        },
                        isKeyPadEnabled = screenState.isKeyPadEnabled,
                        isDoneKeyEnabled = screenState.isDoneButtonEnabled
                    )
                }
            }
        }
    }

    when (val state = screenState.confirmTransactionState) {
        is State.Initial, is State.Loading -> {}
        is State.Error -> {
            BanklyActionDialog(
                title = stringResource(R.string.title_confirm_transaction_error),
                subtitle = state.message,
                positiveActionText = stringResource(R.string.action_okay)
            )
        }

        is State.Success -> {

        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ConfirmTransactionScreenPreview() {
    BanklyTheme {
        ConfirmTransactionScreen(
            screenState = ConfirmTransactionScreenState(),
            confirmTransactionDetails = ConfirmTransactionDetails(
                "080999200291",
                "Hassan Abdulwahab",
                "#20,0000",
                "#0.00",
                "#0.00",
            ),
            onConfirmationSuccess = {},
            onBackPress = {},
            onUiEvent = {},
            onCloseClick = {}
        )
    }
}
