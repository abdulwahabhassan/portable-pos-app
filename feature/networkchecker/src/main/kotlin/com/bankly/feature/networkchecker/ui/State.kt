package com.bankly.feature.networkchecker.ui

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.entity.BankNetwork
import com.bankly.feature.networkchecker.model.NetworkCheckerTab

internal data class NetworkCheckerScreenState(
    val isTransfersBankNetworkListLoading: Boolean = false,
    val isWithdrawalsBankNetworkListLoading: Boolean = false,
    val transfersBankNetworkList: List<BankNetwork> = emptyList(),
    val withdrawalBankNetworkList: List<BankNetwork> = emptyList(),
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val selectedTab: NetworkCheckerTab = NetworkCheckerTab.TRANSFERS,
    val transfersSearchQuery: String = "",
    val withdrawalsSearchQuery: String = "",
) {
    val isLoading: Boolean
        get() = isTransfersBankNetworkListLoading || isWithdrawalsBankNetworkListLoading
}

internal sealed interface NetworkCheckerScreenOneShotState : OneShotState {
    object OnSessionExpired : NetworkCheckerScreenOneShotState
}
