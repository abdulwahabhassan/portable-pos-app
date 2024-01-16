package com.bankly.banklykozenpos.ui

import com.bankly.core.model.entity.NotificationMessage
import com.bankly.feature.notification.model.TransactionPayload

internal sealed interface MainActivityEvent {
    data class AddDeviceToFirebase(
        val deviceId: String,
        val userId: String,
    ) : MainActivityEvent

    data class OnReceiveNotificationMessage(val notificationMessage: NotificationMessage) :
        MainActivityEvent
    data class OnReceiveTransactionPayload(val transactionPayload: TransactionPayload) :
        MainActivityEvent

    data class OnDismissNotificationMessageDialog(val notificationMessage: NotificationMessage) : MainActivityEvent
    data class OnDismissTransactionAlertDialog(val transactionPayload: TransactionPayload) : MainActivityEvent
}
