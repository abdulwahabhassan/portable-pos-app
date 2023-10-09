package com.bankly.feature.paybills.ui.beneficiary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.ui.view.SearchableSelectionListView
import com.bankly.core.common.ui.view.SelectableListItem
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyTabBar
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.entity.Plan
import com.bankly.core.entity.Provider
import com.bankly.feature.paybills.R
import com.bankly.feature.paybills.model.BeneficiaryTab
import com.bankly.feature.paybills.model.BillType
import com.bankly.feature.paybills.model.BottomSheetType
import com.bankly.feature.paybills.model.SavedBeneficiary
import com.bankly.feature.paybills.ui.beneficiary.newbeneficiary.NewBeneficiaryView
import com.bankly.feature.paybills.ui.beneficiary.newbeneficiary.NewBeneficiaryViewModel
import com.bankly.feature.paybills.ui.beneficiary.savedbeneficiary.SavedBaseBeneficiaryViewModel
import com.bankly.feature.paybills.ui.beneficiary.savedbeneficiary.SavedBeneficiaryView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
internal fun BeneficiaryRoute(
    newBeneficiaryViewModel: NewBeneficiaryViewModel = hiltViewModel(),
    savedBeneficiaryViewModel: SavedBaseBeneficiaryViewModel = hiltViewModel(),
    onBackPress: () -> Unit,
    billType: BillType,
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
        billType = billType,
        onNewBeneficiaryUiEvent = { uiEvent: BeneficiaryScreenEvent ->
            newBeneficiaryViewModel.sendEvent(uiEvent)
        },
        onSavedBeneficiaryUiEvent = { uiEvent: BeneficiaryScreenEvent ->
            savedBeneficiaryViewModel.sendEvent(uiEvent)
        },
        onNewBeneficiaryContinueButtonClick = {
            newBeneficiaryViewModel.sendEvent(
                BeneficiaryScreenEvent.OnContinueClick(
                    billType = billType,
                    phoneNumber = newBeneficiaryScreenState.phoneNumberTFV.text,
                    amount = newBeneficiaryScreenState.amountTFV.text,
                ),
            )
        },
        onSavedBeneficiaryContinueButtonClick = {
            savedBeneficiaryViewModel.sendEvent(
                BeneficiaryScreenEvent.OnContinueClick(
                    billType = billType,
                    phoneNumber = savedBeneficiaryScreenState.phoneNumberTFV.text,
                    amount = savedBeneficiaryScreenState.amountTFV.text,
                ),
            )
        },
        onCloseClick = onCloseClick,
    )

    LaunchedEffect(key1 = Unit) {
        merge(
            newBeneficiaryViewModel.oneShotState,
            savedBeneficiaryViewModel.oneShotState
        ).onEach { beneficiaryScreenOneShotState ->
            when (beneficiaryScreenOneShotState) {
                is BeneficiaryScreenOneShotState.GoToConfirmTransactionScreen -> {
                    onContinueClick(beneficiaryScreenOneShotState.transactionData)
                }
            }

        }.launchIn(this)

        newBeneficiaryViewModel.oneShotState.onEach { oneShotUiState ->
            when (oneShotUiState) {
                is BeneficiaryScreenOneShotState.GoToConfirmTransactionScreen -> {
                    onContinueClick(oneShotUiState.transactionData)
                }
            }
        }.launchIn(this)

        this.launch {
            newBeneficiaryViewModel.sendEvent(BeneficiaryScreenEvent.UpdateBillType(billType))
            savedBeneficiaryViewModel.sendEvent(BeneficiaryScreenEvent.UpdateBillType(billType))
        }
        this.launch {
            newBeneficiaryViewModel.sendEvent(BeneficiaryScreenEvent.FetchProviders(billType))
        }

    }

    if (newBeneficiaryScreenState.showErrorDialog) {
        BanklyActionDialog(
            title = stringResource(R.string.title_error),
            subtitle = newBeneficiaryScreenState.errorDialogMessage,
            positiveActionText = stringResource(R.string.action_dismiss),
            positiveAction = {
                newBeneficiaryViewModel.sendEvent(BeneficiaryScreenEvent.OnDismissDialog)
            }
        )
    }

    if (savedBeneficiaryScreenState.showErrorDialog) {
        BanklyActionDialog(
            title = stringResource(R.string.title_error),
            subtitle = savedBeneficiaryScreenState.errorDialogMessage,
            positiveActionText = stringResource(R.string.action_dismiss),
            positiveAction = {
                savedBeneficiaryViewModel.sendEvent(BeneficiaryScreenEvent.OnDismissDialog)
            }
        )
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
    billType: BillType,
    onNewBeneficiaryUiEvent: (BeneficiaryScreenEvent) -> Unit,
    onSavedBeneficiaryUiEvent: (BeneficiaryScreenEvent) -> Unit,
    onNewBeneficiaryContinueButtonClick: () -> Unit,
    onSavedBeneficiaryContinueButtonClick: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var bottomSheetType: BottomSheetType? by remember { mutableStateOf(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BanklyTitleBar(
                onBackPress = onBackPress,
                title = savedBeneficiaryScreenState.billType?.title ?: "",
                onCloseClick = onCloseClick,
                isLoading = newBeneficiaryScreenState.showLoadingIndicator || savedBeneficiaryScreenState.showLoadingIndicator,
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        content = { paddingValues ->
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
                                billType = billType,
                                onProviderDropDownIconClick = {
                                    bottomSheetType = BottomSheetType.PROVIDER
                                    coroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                },
                                onPlanDropDownIconClick = {
                                    bottomSheetType = BottomSheetType.PLAN
                                    coroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                },
                                onEnterPhoneNumber = { textFieldValue ->
                                    onNewBeneficiaryUiEvent(
                                        BeneficiaryScreenEvent.OnInputPhoneNumber(
                                            phoneNumberTFV = textFieldValue,
                                            billType = billType,
                                        ),
                                    )
                                },
                                onEnterAmount = { textFieldValue ->
                                    onNewBeneficiaryUiEvent(
                                        BeneficiaryScreenEvent.OnInputAmount(textFieldValue),
                                    )
                                },
                                onContinueClick = onNewBeneficiaryContinueButtonClick,
                                onEnterIDorCardNumber = { textFieldValue ->
                                    if (newBeneficiaryScreenState.selectedPlan != null) {
                                        onNewBeneficiaryUiEvent(
                                            BeneficiaryScreenEvent.OnInputCableTvNumber(
                                                textFieldValue,
                                                newBeneficiaryScreenState.selectedPlan.billId
                                            ),
                                        )
                                    }
                                },
                                onEnterMeterNumber = { textFieldValue ->
                                    if (newBeneficiaryScreenState.selectedPlan != null) {
                                        onNewBeneficiaryUiEvent(
                                            BeneficiaryScreenEvent.OnInputMeterNumber(
                                                textFieldValue,
                                                newBeneficiaryScreenState.selectedPlan.billId,
                                                newBeneficiaryScreenState.selectedPlan.id
                                            ),
                                        )
                                    }
                                },
                                onToggleSaveAsBeneficiary = { state: Boolean ->
                                    onNewBeneficiaryUiEvent(
                                        BeneficiaryScreenEvent.OnToggleSaveAsBeneficiary(state),
                                    )
                                }
                            )
                        }

                        BeneficiaryTab.SAVED_BENEFICIARY -> {
                            SavedBeneficiaryView(
                                screenState = savedBeneficiaryScreenState,
                                savedBeneficiaries = when (billType) {
                                    BillType.AIRTIME -> SavedBeneficiary.mockOtherBanks()
                                    BillType.INTERNET_DATA -> SavedBeneficiary.mockOtherBanks()
                                    BillType.ELECTRICITY -> SavedBeneficiary.mockOtherBanks()
                                    BillType.CABLE_TV -> SavedBeneficiary.mockOtherBanks()
                                },
                                billType = billType,
                                onProviderDropDownIconClick = {
                                    bottomSheetType = BottomSheetType.PROVIDER
                                    coroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                },
                                onPlanDropDownIconClick = {
                                    bottomSheetType = BottomSheetType.PLAN
                                    coroutineScope.launch {
                                        bottomSheetState.show()
                                    }
                                },
                                onEnterPhoneNumber = { textFieldValue ->
                                    onSavedBeneficiaryUiEvent(
                                        BeneficiaryScreenEvent.OnInputPhoneNumber(
                                            phoneNumberTFV = textFieldValue,
                                            billType = billType,
                                        ),
                                    )
                                },
                                onEnterAmount = { textFieldValue ->
                                    onSavedBeneficiaryUiEvent(
                                        BeneficiaryScreenEvent.OnInputAmount(
                                            amountTFV = textFieldValue
                                        ),
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
                                onEnterIDorCardNumber = { textFieldValue ->
                                    if (savedBeneficiaryScreenState.selectedPlan != null) {
                                        onSavedBeneficiaryUiEvent(
                                            BeneficiaryScreenEvent.OnInputCableTvNumber(
                                                textFieldValue,
                                                savedBeneficiaryScreenState.selectedPlan.billId
                                            ),
                                        )
                                    }
                                },
                                onEnterMeterNumber = { textFieldValue ->
                                    if (savedBeneficiaryScreenState.selectedPlan != null) {
                                        onSavedBeneficiaryUiEvent(
                                            BeneficiaryScreenEvent.OnInputMeterNumber(
                                                textFieldValue,
                                                savedBeneficiaryScreenState.selectedPlan.billId,
                                                savedBeneficiaryScreenState.selectedPlan.id
                                            ),
                                        )
                                    }
                                },
                                onToggleSaveAsBeneficiary = { state: Boolean ->
                                    onSavedBeneficiaryUiEvent(
                                        BeneficiaryScreenEvent.OnToggleSaveAsBeneficiary(state),
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    )
    if (when (bottomSheetState.currentValue) {
            SheetValue.PartiallyExpanded, SheetValue.Expanded -> true
            SheetValue.Hidden -> false
        }
    ) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            onDismissRequest = { coroutineScope.launch { bottomSheetState.hide() } },
            dragHandle = { BottomSheetDefaults.DragHandle(width = 80.dp) },
            windowInsets = WindowInsets(top = 24.dp),
        ) {
            SearchableSelectionListView(
                isListLoading = when (bottomSheetType) {
                    BottomSheetType.PROVIDER -> newBeneficiaryScreenState.isProviderListLoading
                    BottomSheetType.PLAN -> newBeneficiaryScreenState.isPlanListLoading
                    null -> false
                },
                listItems = when (bottomSheetType) {
                    BottomSheetType.PROVIDER -> newBeneficiaryScreenState.providerList
                    BottomSheetType.PLAN -> newBeneficiaryScreenState.planList
                    null -> emptyList()
                },
                itemToString = { item ->
                    when (bottomSheetType) {
                        BottomSheetType.PROVIDER -> (item as Provider).name
                        BottomSheetType.PLAN -> (item as Plan).name
                        null -> ""
                    }
                },
                onItemSelected = { _, item ->
                    when (newBeneficiaryScreenState.selectedTab) {
                        BeneficiaryTab.NEW_BENEFICIARY -> {
                            bottomSheetType?.let { type: BottomSheetType ->
                                onNewBeneficiaryUiEvent(
                                    when (type) {
                                        BottomSheetType.PROVIDER -> BeneficiaryScreenEvent.OnSelectProvider(
                                            billType = billType,
                                            provider = item as Provider,
                                        )

                                        BottomSheetType.PLAN -> BeneficiaryScreenEvent.OnSelectPlan(
                                            billType = billType,
                                            plan = item as Plan,
                                        )
                                    }
                                )
                            }
                        }

                        BeneficiaryTab.SAVED_BENEFICIARY -> {
                            bottomSheetType?.let { type: BottomSheetType ->
                                onSavedBeneficiaryUiEvent(
                                    when (type) {
                                        BottomSheetType.PROVIDER -> BeneficiaryScreenEvent.OnSelectProvider(
                                            billType = billType,
                                            provider = item as Provider,
                                        )

                                        BottomSheetType.PLAN -> BeneficiaryScreenEvent.OnSelectPlan(
                                            billType = billType,
                                            plan = item as Plan,
                                        )
                                    }
                                )
                            }
                        }
                    }
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                },
                title = when (bottomSheetType) {
                    BottomSheetType.PROVIDER -> {
                        stringResource(R.string.title_select_network_provider)
                    }

                    BottomSheetType.PLAN -> {
                        when (billType) {
                            BillType.INTERNET_DATA -> stringResource(R.string.title_select_data_plan)
                            BillType.CABLE_TV -> stringResource(R.string.title_select_cable_plan)
                            BillType.ELECTRICITY -> stringResource(R.string.title_select_electricity_plan)
                            BillType.AIRTIME -> ""
                        }
                    }

                    null -> ""
                },
                onCloseIconClick = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                    }
                },
                drawItem = { item, selected, itemEnabled, onClick ->
                    SelectableListItem(
                        text = when (bottomSheetType) {
                            BottomSheetType.PROVIDER -> (item as Provider).name
                            BottomSheetType.PLAN -> when (billType) {
                                BillType.CABLE_TV, BillType.INTERNET_DATA -> (item as Plan).description
                                BillType.ELECTRICITY -> (item as Plan).name
                                BillType.AIRTIME -> ""
                            }

                            null -> ""
                        },
                        selected = selected,
                        enabled = itemEnabled,
                        onClick = onClick,
                        startIcon = {
                            when (bottomSheetType) {
                                BottomSheetType.PROVIDER -> {
                                    AsyncImage(
                                        model = when (bottomSheetType) {
                                            BottomSheetType.PROVIDER -> (item as Provider).billImageUrl
                                            BottomSheetType.PLAN -> ""
                                            null -> ""
                                        },
                                        modifier = Modifier.size(32.dp),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit
                                    )
                                }

                                BottomSheetType.PLAN, null -> {}
                            }
                        },
                    )
                }
            )
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
            billType = BillType.AIRTIME,
            onNewBeneficiaryContinueButtonClick = {},
            onSavedBeneficiaryContinueButtonClick = {},
        )
    }
}
