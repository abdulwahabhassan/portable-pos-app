package com.bankly.feature.eod.ui.synceod

import com.bankly.core.common.viewmodel.OneShotState

internal data class SyncEodScreenState(
    val processedAmount: Double = 0.00,
    val settledAmount: Double = 0.00,
    private val isEodInfoLoading: Boolean = false,
    val isEodSyncLoading: Boolean = false,
    val errorDialogMessage: String = "",
    val showErrorDialog: Boolean = false,
    val successfulEodSyncMessage: String = "",
    val showSuccessfulEodSyncDialog: Boolean = false,
) {
    val isLoading: Boolean
        get() = isEodInfoLoading || isEodSyncLoading

    val isUserInputEnabled: Boolean
        get() = isLoading.not()
}

internal sealed interface SyncEodScreenOneShotState : OneShotState {
    object OnSessionExpired : SyncEodScreenOneShotState
}
