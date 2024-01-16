package com.bankly.feature.notification.ui

import com.bankly.core.common.viewmodel.OneShotState

internal data class NotificationScreenState(
    val notifications: List<com.bankly.core.model.entity.NotificationMessage> = emptyList(),
    val isNotificationsLoading: Boolean = false,
) {

    val isLoading: Boolean
        get() = isNotificationsLoading

    val isUserInputEnabled: Boolean
        get() = isNotificationsLoading.not()
}

internal sealed interface NotificationScreenOneShotState : OneShotState {
    object OnSessionExpired : NotificationScreenOneShotState
}
