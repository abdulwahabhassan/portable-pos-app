package com.bankly.banklykozenpos.ui

import com.bankly.core.common.viewmodel.OneShotState
import com.bankly.core.model.entity.NotificationMessage
import com.bankly.feature.notification.model.TransactionPayload

internal data class MainActivityState(
    val showErrorDialog: Boolean = false,
    val errorDialogMessage: String = "",
    val showNotificationMessageDialog: Boolean = false,
    val notificationMessage: NotificationMessage? = null,
    val showTransactionAlertDialog: Boolean = false,
    val transactionAlert: TransactionPayload? = null,
)

internal sealed interface MainActivityOneShotState : OneShotState {
    object OnSessionExpired : MainActivityOneShotState
}
