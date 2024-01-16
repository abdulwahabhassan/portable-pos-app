package com.bankly.feature.notification.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.GetNotificationMessageUseCase
import com.bankly.core.domain.usecase.GetNotificationMessagesUseCase
import com.bankly.core.domain.usecase.InsertNotificationMessageUseCase
import com.bankly.core.model.entity.NotificationMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor(
    private val getNotificationMessageUseCase: GetNotificationMessageUseCase,
    private val getNotificationMessagesUseCase: GetNotificationMessagesUseCase,
    private val insertNotificationMessageUseCase: InsertNotificationMessageUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<NotificationScreenEvent, NotificationScreenState, NotificationScreenOneShotState>(
    NotificationScreenState()
) {

    suspend fun insertNotification(notificationMessage: NotificationMessage) {
        Log.d("debug notification message", "notification message: $notificationMessage")
        insertNotificationMessageUseCase.invoke(notificationMessage)
    }

    private fun getNotificationMessages() {
        viewModelScope.launch {
            setUiState { copy(isNotificationsLoading = true) }
            getNotificationMessagesUseCase.invoke().onEach { messages: List<NotificationMessage> ->
                setUiState { copy(notifications = messages, isNotificationsLoading = false) }
            }.launchIn(viewModelScope)
        }
    }

    fun markMessageAsSeen(dateTime: String) {
        viewModelScope.launch {
            Log.d("debug mark as seen", "messageid to be marked as seen: $dateTime")
            val notificationMessage = getNotificationMessageUseCase.invoke(dateTime)
            if (notificationMessage != null && !notificationMessage.seen) {
                insertNotificationMessageUseCase.invoke(notificationMessage.copy(seen = true))
            }
        }
    }


    override suspend fun handleUiEvents(event: NotificationScreenEvent) {
        when (event) {
            NotificationScreenEvent.LoadUiData -> {
                getNotificationMessages()
            }
        }
    }

}
