package com.bankly.feature.sendmoney.ui.beneficiary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.ui.view.BankSearchView
import com.bankly.core.designsystem.component.BanklyTabBar
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.entity.Bank
import com.bankly.feature.sendmoney.model.BeneficiaryTab
import com.bankly.feature.sendmoney.model.SavedBeneficiary
import com.bankly.feature.sendmoney.ui.beneficiary.newbeneficiary.NewBeneficiaryView
import com.bankly.feature.sendmoney.ui.beneficiary.newbeneficiary.NewBeneficiaryViewModel
import com.bankly.feature.sendmoney.ui.beneficiary.savedbeneficiary.SavedBaseBeneficiaryViewModel
import com.bankly.feature.sendmoney.ui.beneficiary.savedbeneficiary.SavedBeneficiaryView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun BeneficiaryRoute(
    newBeneficiaryViewModel: NewBeneficiaryViewModel = hiltViewModel(),
    savedBeneficiaryViewModel: SavedBaseBeneficiaryViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    sendMoneyChannel: SendMoneyChannel,
    onContinueClick: (TransactionData) -> Unit,
    onCloseClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val newBeneficiaryScreenState by newBeneficiaryViewModel.uiState.collectAsStateWithLifecycle()
    val savedBeneficiaryScreenState by savedBeneficiaryViewModel.uiState.collectAsStateWithLifecycle()

    BeneficiaryScreen(
        coroutineScope = coroutineScope,
        newBeneficiaryScreenState = newBeneficiaryScreenState,
        savedBeneficiaryScreenState = savedBeneficiaryScreenState,
        onBackPress = onBackPress,
        channel = sendMoneyChannel,
        onNewBeneficiaryUiEvent = { uiEvent: BeneficiaryScreenEvent ->
            newBeneficiaryViewModel.sendEvent(uiEvent)
        },
        onSavedBeneficiaryUiEvent = { uiEvent: BeneficiaryScreenEvent ->
            savedBeneficiaryViewModel.sendEvent(uiEvent)
        },
        onNewBeneficiaryContinueButtonClick = {
            newBeneficiaryViewModel.sendEvent(
                BeneficiaryScreenEvent.OnContinueClick(
                    sendMoneyChannel = sendMoneyChannel,
                    accountOrPhoneNumber = newBeneficiaryScreenState.accountOrPhoneTFV.text,
                    accountName = newBeneficiaryScreenState.accountOrPhoneFeedBack,
                    amount = newBeneficiaryScreenState.amountTFV.text,
                    bankName = newBeneficiaryScreenState.bankNameTFV.text,
                    selectedBankId = newBeneficiaryScreenState.selectedBank?.id,
                    narration = newBeneficiaryScreenState.narrationTFV.text,
                    accountNumberType = newBeneficiaryScreenState.accountNumberType,

                ),
            )
        },
        onSavedBeneficiaryContinueButtonClick = {
            savedBeneficiaryViewModel.sendEvent(
                BeneficiaryScreenEvent.OnContinueClick(
                    sendMoneyChannel = sendMoneyChannel,
                    accountOrPhoneNumber = savedBeneficiaryScreenState.accountOrPhoneTFV.text,
                    accountName = savedBeneficiaryScreenState.accountOrPhoneFeedBack,
                    amount = savedBeneficiaryScreenState.amountTFV.text,
                    bankName = savedBeneficiaryScreenState.bankNameTFV.text,
                    selectedBankId = savedBeneficiaryScreenState.selectedBank?.id,
                    narration = savedBeneficiaryScreenState.narrationTFV.text,
                    accountNumberType = savedBeneficiaryScreenState.accountNumberType,
                ),
            )
        },
        onCloseClick = onCloseClick,
    )

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            newBeneficiaryViewModel.oneShotState.collectLatest { oneShotUiState ->
                when (oneShotUiState) {
                    is BeneficiaryScreenOneShotState.GoToConfirmTransactionScreen -> {
                        onContinueClick(oneShotUiState.transactionData)
                    }
                }
            }
        }
        coroutineScope.launch {
            savedBeneficiaryViewModel.oneShotState.collectLatest { oneShotUiState ->
                when (oneShotUiState) {
                    is BeneficiaryScreenOneShotState.GoToConfirmTransactionScreen -> {
                        onContinueClick(oneShotUiState.transactionData)
                    }
                }
            }
        }
        if (sendMoneyChannel == SendMoneyChannel.BANKLY_TO_BANKLY) {
            newBeneficiaryViewModel.sendEvent(
                BeneficiaryScreenEvent.OnSelectBank(
                    bank = Bank(id = BANKLY_BANK_ID, name = BANKLY_BANK_NAME),
                    accountOrPhoneNumber = newBeneficiaryScreenState.accountOrPhoneTFV.text,
                ),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BeneficiaryScreen(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    newBeneficiaryScreenState: BeneficiaryScreenState,
    savedBeneficiaryScreenState: BeneficiaryScreenState,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
    channel: SendMoneyChannel,
    onNewBeneficiaryUiEvent: (BeneficiaryScreenEvent) -> Unit,
    onSavedBeneficiaryUiEvent: (BeneficiaryScreenEvent) -> Unit,
    onNewBeneficiaryContinueButtonClick: () -> Unit,
    onSavedBeneficiaryContinueButtonClick: () -> Unit,
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        SheetState(
            skipPartiallyExpanded = false,
            initialValue = SheetValue.Hidden,
        ),
    )

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = channel.screenTitle,
                onCloseClick = onCloseClick,
                isLoading = newBeneficiaryScreenState.shouldShowLoadingIndicator || savedBeneficiaryScreenState.shouldShowLoadingIndicator,
            )
        },
        sheetPeekHeight = 0.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetDragHandle = { BottomSheetDefaults.DragHandle(width = 80.dp) },
        sheetContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        sheetContent = {
            BankSearchView(
                isBankListLoading = newBeneficiaryScreenState.isBankListLoading,
                bankList = newBeneficiaryScreenState.banks,
                onSelectBank = { bank: Bank ->
                    when (newBeneficiaryScreenState.selectedTab) {
                        BeneficiaryTab.NEW_BENEFICIARY -> {
                            onNewBeneficiaryUiEvent(
                                BeneficiaryScreenEvent.OnSelectBank(
                                    bank = bank,
                                    accountOrPhoneNumber = newBeneficiaryScreenState.accountOrPhoneTFV.text,
                                ),
                            )
                        }

                        BeneficiaryTab.SAVED_BENEFICIARY -> {
                            onSavedBeneficiaryUiEvent(
                                BeneficiaryScreenEvent.OnSelectBank(
                                    bank = bank,
                                    accountOrPhoneNumber = savedBeneficiaryScreenState.accountOrPhoneTFV.text,
                                ),
                            )
                        }
                    }
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.hide()
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column {
                Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 2.dp)) {
                    BanklyTabBar(
                        tabs = BeneficiaryTab.values().toList(),
                        onTabClick = { tab ->
                            onNewBeneficiaryUiEvent(BeneficiaryScreenEvent.OnTabSelected(tab))
                        },
                        selectedTab = newBeneficiaryScreenState.selectedTab,
                        selectedTabColor = MaterialTheme.colorScheme.surfaceVariant,
                        selectedTabTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTabTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        rippleColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
                when (newBeneficiaryScreenState.selectedTab) {
                    BeneficiaryTab.NEW_BENEFICIARY -> {
                        NewBeneficiaryView(
                            screenState = newBeneficiaryScreenState,
                            channel = channel,
                            onTypeSelected = { selectedType ->
                                onNewBeneficiaryUiEvent(
                                    BeneficiaryScreenEvent.OnTypeSelected(
                                        accountNumberType = selectedType,
                                        bankId = newBeneficiaryScreenState.selectedBank?.id,
                                        accountOrPhoneNumber = newBeneficiaryScreenState.accountOrPhoneTFV.text,
                                    ),
                                )
                            },
                            selectedAccountNumberType = newBeneficiaryScreenState.accountNumberType,
                            onBankNameDropDownIconClick = {
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            },
                            onEnterPhoneOrAccountNumber = { textFieldValue ->
                                onNewBeneficiaryUiEvent(
                                    BeneficiaryScreenEvent.OnInputAccountOrPhoneNumber(
                                        accountOrPhoneNumberTFV = textFieldValue,
                                        sendMoneyChannel = channel,
                                        selectedBankId = newBeneficiaryScreenState.selectedBank?.id,
                                        accountNumberType = newBeneficiaryScreenState.accountNumberType,
                                    ),
                                )
                            },
                            onEnterNarration = { textFieldValue ->
                                onNewBeneficiaryUiEvent(
                                    BeneficiaryScreenEvent.OnInputNarration(textFieldValue),
                                )
                            },
                            onEnterAmount = { textFieldValue ->
                                onNewBeneficiaryUiEvent(
                                    BeneficiaryScreenEvent.OnInputAmount(textFieldValue),
                                )
                            },
                            onContinueClick = onNewBeneficiaryContinueButtonClick,
                        )
                    }

                    BeneficiaryTab.SAVED_BENEFICIARY -> {
                        SavedBeneficiaryView(
                            screenState = savedBeneficiaryScreenState,
                            savedBeneficiaries = emptyList(),
                            channel = channel,
                            selectedAccountNumberType = savedBeneficiaryScreenState.accountNumberType,
                            onBankNameDropDownIconClick = {
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            },
                            onEnterPhoneOrAccountNumber = { textFieldValue ->
                                onSavedBeneficiaryUiEvent(
                                    BeneficiaryScreenEvent.OnInputAccountOrPhoneNumber(
                                        accountOrPhoneNumberTFV = textFieldValue,
                                        sendMoneyChannel = channel,
                                        selectedBankId = savedBeneficiaryScreenState.selectedBank?.id,
                                        accountNumberType = savedBeneficiaryScreenState.accountNumberType,
                                    ),
                                )
                            },
                            onEnterNarration = { textFieldValue ->
                                onSavedBeneficiaryUiEvent(
                                    BeneficiaryScreenEvent.OnInputNarration(textFieldValue),
                                )
                            },
                            onEnterAmount = { textFieldValue ->
                                onSavedBeneficiaryUiEvent(
                                    BeneficiaryScreenEvent.OnInputAmount(textFieldValue),
                                )
                            },
                            onContinueClick = onSavedBeneficiaryContinueButtonClick,
                            onChangeSelectedSavedBeneficiary = {
                                onSavedBeneficiaryUiEvent(BeneficiaryScreenEvent.OnChangeSelectedSavedBeneficiary)
                            },
                            onBeneficiarySelected = { beneficiary: SavedBeneficiary ->
                                onSavedBeneficiaryUiEvent(
                                    BeneficiaryScreenEvent.OnBeneficiarySelected(beneficiary),
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BeneficiaryScreenPreview() {
    BanklyTheme {
        BeneficiaryScreen(
            newBeneficiaryScreenState = BeneficiaryScreenState(),
            onNewBeneficiaryUiEvent = {},
            savedBeneficiaryScreenState = BeneficiaryScreenState(),
            onSavedBeneficiaryUiEvent = {},
            onBackPress = {},
            onCloseClick = {},
            channel = SendMoneyChannel.BANKLY_TO_BANKLY,
            onNewBeneficiaryContinueButtonClick = {},
            onSavedBeneficiaryContinueButtonClick = {},
        )
    }
}
