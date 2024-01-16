package com.bankly.feature.paywithtransfer.ui

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.entity.AgentAccountDetails
import com.bankly.core.model.entity.RecentFund

internal data class PayWithTransferScreenState(
    val isRecentFundsLoading: Boolean = false,
    val isAgentAccountDetailsLoading: Boolean = false,
    val agentAccountDetails: com.bankly.core.model.entity.AgentAccountDetails? = null,
    val isAccountDetailsExpanded: Boolean = true,
    val recentFunds: List<com.bankly.core.model.entity.RecentFund> = emptyList(),
    val showRecentFundDialog: Boolean = false,
    val selectedRecentFund: com.bankly.core.model.entity.RecentFund? = null,
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
) {
    val isLoading: Boolean
        get() = isRecentFundsLoading || isAgentAccountDetailsLoading
}

internal sealed interface PayWithTransferScreenOneShotState : OneShotState {
    object OnSessionExpired : PayWithTransferScreenOneShotState
}
