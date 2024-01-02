package com.bankly.core.common.ui.sendreceipt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.common.R
import com.bankly.core.common.ui.view.ComingSoonView
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInfoText
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.feature.paywithtransfer.ui.sendreceipt.SendReceiptScreenEvent
import com.bankly.feature.paywithtransfer.ui.sendreceipt.SendReceiptScreenOneShotState
import com.bankly.feature.paywithtransfer.ui.sendreceipt.SendReceiptScreenState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SendReceiptRoute(
    viewModel: SendReceiptViewModel = hiltViewModel(),
    onGoToSuccessScreen: (String) -> Unit,
    onBackPress: () -> Unit,
    transactionReceipt: TransactionReceipt,
) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    SendReceiptScreen(
        screenState = screenState,
        onBackPress = onBackPress,
        onUiEvent = { uiEvent: SendReceiptScreenEvent -> viewModel.sendEvent(uiEvent) },
    )

    LaunchedEffect(key1 = Unit, block = {
        coroutineScope.launch {
            viewModel.oneShotState.collectLatest { oneShotUiState ->
                when (oneShotUiState) {
                    is SendReceiptScreenOneShotState.GoToSuccessfulScreen -> {
                        onGoToSuccessScreen("Receipt Sent")
                    }
                }
            }
        }
    })
}

@Composable
private fun SendReceiptScreen(
    screenState: SendReceiptScreenState,
    onBackPress: () -> Unit,
    onUiEvent: (SendReceiptScreenEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = stringResource(R.string.title_send_receipt),
                isLoading = screenState.isLoading,
            )
        },
    ) { paddingValues: PaddingValues ->
        ComingSoonView(
            modifier = Modifier.padding(paddingValues),
            onOkayButtonClick = onBackPress
        )
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding),
//            verticalArrangement = Arrangement.SpaceBetween,
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            item {
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    BanklyInputField(
//                        textFieldValue = screenState.phoneNumberTFV,
//                        onTextFieldValueChange = { textFieldValue ->
//                            onUiEvent(SendReceiptScreenEvent.OnEnterPhoneNumber(textFieldValue))
//                        },
//                        isEnabled = screenState.isUserInputEnabled,
//                        placeholderText = stringResource(R.string.msg_enter_phone_number),
//                        labelText = "Phone Number",
//                        keyboardOptions = KeyboardOptions.Default.copy(
//                            keyboardType = KeyboardType.Number,
//                        ),
//                        isError = screenState.isPhoneNumberError,
//                        feedbackText = screenState.phoneNumberFeedBack,
//                    )
//                    BanklyInfoText(text = stringResource(R.string.msg_send_receipt_fee_charge_warning))
//                }
//            }
//
//            item {
//                BanklyFilledButton(
//                    modifier = Modifier
//                        .padding(32.dp)
//                        .fillMaxWidth(),
//                    text = stringResource(R.string.action_continue),
//                    onClick = {
//                        onUiEvent(SendReceiptScreenEvent.OnContinueClick(screenState.phoneNumberTFV.text))
//                    },
//                    isEnabled = screenState.isContinueButtonEnabled,
//                )
//            }
//        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun SendReceiptScreenPreview() {
    BanklyTheme {
        SendReceiptScreen(
            screenState = SendReceiptScreenState(),
            onBackPress = {},
            onUiEvent = {},
        )
    }
}
