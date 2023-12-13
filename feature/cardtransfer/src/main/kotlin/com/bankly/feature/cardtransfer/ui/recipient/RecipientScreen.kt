package com.bankly.feature.cardtransfer.ui.recipient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
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
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.ui.view.BankSearchView
import com.bankly.core.common.util.AmountFormatter
import com.bankly.core.common.util.AmountInputVisualTransformation
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.entity.Bank
import com.bankly.feature.cardtransfer.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
internal fun RecipientRoute(
    viewModel: RecipientViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    onContinueClick: (TransactionData) -> Unit,
    onSessionExpired: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    RecipientScreen(
        screenState = screenState,
        onBackPress = onBackPress,
    ) { uiEvent: RecipientScreenEvent -> viewModel.sendEvent(uiEvent) }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            viewModel.oneShotState.onEach { oneShotUiState ->
                when (oneShotUiState) {
                    is RecipientScreenOneShotState.GoToSelectAccountTypeScreen -> {
                        onContinueClick(oneShotUiState.transactionData)
                    }

                    RecipientScreenOneShotState.OnSessionExpired -> {
                        onSessionExpired()
                    }
                }
            }.launchIn(this)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipientScreen(
    screenState: RecipientScreenState,
    onBackPress: () -> Unit,
    onUiEvent: (RecipientScreenEvent) -> Unit,
) {
    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(
            SheetState(
                skipPartiallyExpanded = false,
                initialValue = SheetValue.Hidden
            )
        )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BanklyTitleBar(
                title = stringResource(R.string.title_card_transfer),
                isLoading = screenState.shouldShowLoadingIndicator,
                onBackPress = onBackPress,
            )
        },
        sheetPeekHeight = 0.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetDragHandle = { BottomSheetDefaults.DragHandle(width = 80.dp) },
        sheetContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        sheetContent = {
            BankSearchView(
                isBankListLoading = screenState.isBankListLoading,
                bankList = screenState.banks,
                onSelectBank = { bank: Bank ->
                    onUiEvent(
                        RecipientScreenEvent.OnSelectBank(
                            bank,
                            screenState.accountNumberTFV.text
                        )
                    )
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                },
                onCloseIconClick = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                }
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    BanklyInputField(
                        textFieldValue = screenState.bankNameTFV,
                        onTextFieldValueChange = { },
                        trailingIcon = BanklyIcons.ChevronDown,
                        readOnly = true,
                        placeholderText = stringResource(R.string.msg_bank_name),
                        labelText = stringResource(R.string.msg_select_bank),
                        isError = screenState.isBankNameError,
                        feedbackText = screenState.bankNameFeedBack,
                        isEnabled = screenState.isUserInputEnabled,
                        onSurfaceAreaClick = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
                    )

                    BanklyInputField(
                        textFieldValue = screenState.accountNumberTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onUiEvent(
                                RecipientScreenEvent.OnAccountNumber(
                                    textFieldValue,
                                    screenState.selectedBank?.id,
                                ),
                            )
                        },
                        placeholderText = stringResource(R.string.msg_enter_account_number),
                        labelText = stringResource(R.string.msg_account_number_label),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                        ),
                        isError = screenState.isAccountNumberError,
                        trailingIcon = screenState.validationIcon,
                        feedbackText = screenState.accountNumberFeedBack,
                        isEnabled = screenState.isUserInputEnabled,
                    )

                    BanklyInputField(
                        textFieldValue = screenState.amountTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onUiEvent(
                                RecipientScreenEvent.OnAmount(textFieldValue),
                            )
                        },
                        placeholderText = stringResource(R.string.msg_enter_amount),
                        labelText = stringResource(R.string.msg_amount_label),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Decimal,
                        ),
                        isError = screenState.isAmountError,
                        feedbackText = screenState.amountFeedBack,
                        isEnabled = screenState.isUserInputEnabled,
                        visualTransformation = AmountInputVisualTransformation(AmountFormatter()),
                    )

                    BanklyInputField(
                        textFieldValue = screenState.senderPhoneNumberTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onUiEvent(
                                RecipientScreenEvent.OnSenderPhoneNumber(
                                    textFieldValue,
                                ),
                            )
                        },
                        placeholderText = stringResource(R.string.msg_enter_sender_phone_number),
                        labelText = stringResource(R.string.msg_sender_phone_number_label),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Phone,
                        ),
                        isError = screenState.isSenderPhoneNumberError,
                        feedbackText = screenState.senderPhoneNumberFeedBack,
                        isEnabled = screenState.isUserInputEnabled,
                    )
                }
            }

            item {
                BanklyFilledButton(
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth(),
                    text = stringResource(R.string.action_continue),
                    onClick = {
                        onUiEvent(
                            RecipientScreenEvent.OnContinueClick(
                                bankName = screenState.bankNameTFV.text,
                                accountNumber = screenState.accountNumberTFV.text,
                                amount = screenState.amountTFV.text,
                                senderPhoneNumber = screenState.senderPhoneNumberTFV.text,
                                accountName = screenState.accountNumberTFV.text,
                                selectedBankId = screenState.selectedBank?.id,

                                ),
                        )
                    },
                    isEnabled = screenState.isContinueButtonEnabled,
                )
            }
        }
    }

    BanklyCenterDialog(
        title = stringResource(R.string.title_error),
        subtitle = screenState.errorDialogMessage,
        icon = BanklyIcons.ErrorAlert,
        positiveActionText = stringResource(R.string.action_dismiss),
        positiveAction = {
            onUiEvent(RecipientScreenEvent.OnDismissErrorDialog)
        },
        showDialog = screenState.showErrorDialog,
        onDismissDialog = {
            onUiEvent(RecipientScreenEvent.OnDismissErrorDialog)
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun EnterRecipientDetailsScreenPreview() {
    BanklyTheme {
        RecipientScreen(
            screenState = RecipientScreenState(),
            onBackPress = {},
        ) {}
    }
}
