package com.bankly.feature.networkchecker.ui

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.entity.BankNetwork
import com.bankly.feature.networkchecker.model.NetworkCheckerTab

internal data class NetworkCheckerScreenState(
    val isBankListLoading: Boolean = false,
    val bankNetworks: List<com.bankly.core.model.entity.BankNetwork> = emptyList(),
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val selectedTab: NetworkCheckerTab = NetworkCheckerTab.TRANSFERS,
) {
    val isLoading: Boolean
        get() = isBankListLoading
}

internal sealed interface NetworkCheckerScreenOneShotState : OneShotState {
    object OnSessionExpired : NetworkCheckerScreenOneShotState
}
