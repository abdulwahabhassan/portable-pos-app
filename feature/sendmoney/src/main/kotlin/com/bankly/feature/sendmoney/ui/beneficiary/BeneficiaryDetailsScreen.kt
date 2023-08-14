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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bankly.core.common.ui.view.BankSearchView
import com.bankly.core.designsystem.component.BanklyTabBar
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.model.Bank
import com.bankly.feature.sendmoney.model.BeneficiaryTab
import com.bankly.feature.sendmoney.model.SavedBeneficiary
import com.bankly.feature.sendmoney.model.SendMoneyChannel
import com.bankly.feature.sendmoney.ui.beneficiary.newbeneficiary.NewBeneficiaryDetailsViewModel
import com.bankly.feature.sendmoney.ui.beneficiary.newbeneficiary.NewBeneficiaryView
import com.bankly.feature.sendmoney.ui.beneficiary.savedbeneficiary.SavedBeneficiaryDetailsViewModel
import com.bankly.feature.sendmoney.ui.beneficiary.savedbeneficiary.SavedBeneficiaryView
import kotlinx.coroutines.launch

@Composable
internal fun BeneficiaryRoute(
    newBeneficiaryDetailsViewModel: NewBeneficiaryDetailsViewModel = hiltViewModel(),
    savedBeneficiaryDetailsViewModel: SavedBeneficiaryDetailsViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    destination: SendMoneyChannel,
    onContinueClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    val newBeneficiaryScreenState by newBeneficiaryDetailsViewModel.uiState.collectAsStateWithLifecycle()
    val savedBeneficiaryScreenState by savedBeneficiaryDetailsViewModel.uiState.collectAsStateWithLifecycle()

    BeneficiaryScreen(
        newBeneficiaryScreenState = newBeneficiaryScreenState,
        savedBeneficiaryScreenState = savedBeneficiaryScreenState,
        onBackPress = onBackPress,
        channel = destination,
        onNewBeneficiaryUiEvent = { uiEvent: BeneficiaryDetailsScreenEvent ->
            newBeneficiaryDetailsViewModel.sendEvent(uiEvent)
        },
        onSavedBeneficiaryUiEvent = { uiEvent: BeneficiaryDetailsScreenEvent ->
            savedBeneficiaryDetailsViewModel.sendEvent(uiEvent)
        },
        onContinueClick = onContinueClick,
        onCloseClick = onCloseClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeneficiaryScreen(
    newBeneficiaryScreenState: BeneficiaryDetailsScreenState,
    savedBeneficiaryScreenState: BeneficiaryDetailsScreenState,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
    channel: SendMoneyChannel,
    onNewBeneficiaryUiEvent: (BeneficiaryDetailsScreenEvent) -> Unit,
    onSavedBeneficiaryUiEvent: (BeneficiaryDetailsScreenEvent) -> Unit,
    onContinueClick: () -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
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
                onBackPress = onBackPress,
                title = channel.screenTitle,
                onCloseClick = onCloseClick,
                isLoading = newBeneficiaryScreenState.shouldShowLoadingIndicator || savedBeneficiaryScreenState.shouldShowLoadingIndicator
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
                                BeneficiaryDetailsScreenEvent.OnSelectBank(bank = bank)
                            )
                        }

                        BeneficiaryTab.SAVED_BENEFICIARY -> {
                            onSavedBeneficiaryUiEvent(
                                BeneficiaryDetailsScreenEvent.OnSelectBank(bank = bank)
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 2.dp)) {
                    BanklyTabBar(
                        tabs = BeneficiaryTab.values().toList(),
                        onTabClick = { tab ->
                            onNewBeneficiaryUiEvent(BeneficiaryDetailsScreenEvent.OnTabSelected(tab))
                        },
                        selectedTab = newBeneficiaryScreenState.selectedTab,
                        selectedTabColor = MaterialTheme.colorScheme.surfaceVariant,
                        selectedTabTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTabTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        rippleColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
                when (newBeneficiaryScreenState.selectedTab) {
                    BeneficiaryTab.NEW_BENEFICIARY -> {
                        NewBeneficiaryView(
                            screenState = newBeneficiaryScreenState,
                            channel = channel,
                            onTypeSelected = { selectedType ->
                                onNewBeneficiaryUiEvent(
                                    BeneficiaryDetailsScreenEvent.OnTypeSelected(
                                        typeTFV = newBeneficiaryScreenState.typeTFV.copy(text = selectedType.title),
                                    )
                                )
                            },
                            selectedType = newBeneficiaryScreenState.type,
                            onBankNameDropDownIconClick = {
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            },
                            onEnterPhoneOrAccountNumber = { textFieldValue ->
                                onNewBeneficiaryUiEvent(
                                    BeneficiaryDetailsScreenEvent.OnEnterAccountOrPhoneNumber(
                                        accountOrPhoneNumberTFV = textFieldValue,
                                        sendMoneyChannel = channel,
                                    )
                                )
                            },
                            onEnterNarration = { textFieldValue ->
                                onNewBeneficiaryUiEvent(
                                    BeneficiaryDetailsScreenEvent.OnEnterNarration(textFieldValue)
                                )
                            },
                            onEnterAmount = { textFieldValue ->
                                onNewBeneficiaryUiEvent(
                                    BeneficiaryDetailsScreenEvent.OnEnterAmount(textFieldValue)
                                )
                            },
                            onContinueClick = onContinueClick
                        )
                    }

                    BeneficiaryTab.SAVED_BENEFICIARY -> {
                        SavedBeneficiaryView(
                            screenState = savedBeneficiaryScreenState,
                            savedBeneficiaries = when (channel) {
                                SendMoneyChannel.BANKLY_TO_OTHER -> SavedBeneficiary.mockOtherBanks()
                                SendMoneyChannel.BANKLY_TO_BANKLY -> SavedBeneficiary.mockBanklyBank()
                            },
                            channel = channel,
                            selectedType = savedBeneficiaryScreenState.type,
                            onBankNameDropDownIconClick = {
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            },
                            onEnterPhoneOrAccountNumber = { textFieldValue ->
                                onSavedBeneficiaryUiEvent(
                                    BeneficiaryDetailsScreenEvent.OnEnterAccountOrPhoneNumber(
                                        accountOrPhoneNumberTFV = textFieldValue,
                                        sendMoneyChannel = channel,
                                    )
                                )
                            },
                            onEnterNarration = { textFieldValue ->
                                onSavedBeneficiaryUiEvent(
                                    BeneficiaryDetailsScreenEvent.OnEnterNarration(textFieldValue)
                                )
                            },
                            onEnterAmount = { textFieldValue ->
                                onSavedBeneficiaryUiEvent(
                                    BeneficiaryDetailsScreenEvent.OnEnterAmount(textFieldValue)
                                )
                            },
                            onContinueClick = onContinueClick,
                            onChangeSelectedSavedBeneficiary = {
                                onSavedBeneficiaryUiEvent(BeneficiaryDetailsScreenEvent.OnChangeSelectedSavedBeneficiary)
                            },
                            onBeneficiarySelected = { beneficiary: SavedBeneficiary ->
                                onSavedBeneficiaryUiEvent(
                                    BeneficiaryDetailsScreenEvent.OnBeneficiarySelected(beneficiary)
                                )
                            }
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
            newBeneficiaryScreenState = BeneficiaryDetailsScreenState(),
            onNewBeneficiaryUiEvent = {},
            savedBeneficiaryScreenState = BeneficiaryDetailsScreenState(),
            onSavedBeneficiaryUiEvent = {},
            onBackPress = {},
            onCloseClick = {},
            channel = SendMoneyChannel.BANKLY_TO_BANKLY,
            onContinueClick = {}
        )
    }
}