package com.bankly.banklykozenpos.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.AddDeviceToFirebaseUseCase
import com.bankly.core.domain.usecase.GetNotificationMessageUseCase
import com.bankly.core.domain.usecase.GetRecentFundUseCase
import com.bankly.core.domain.usecase.InsertNotificationMessageUseCase
import com.bankly.core.domain.usecase.InsertRecentFundUseCase
import com.bankly.core.domain.usecase.SyncRecentFundingUseCase
import com.bankly.core.model.data.AddDeviceTokenData
import com.bankly.core.model.data.SyncRecentFundingData
import com.bankly.core.model.sealed.onFailure
import com.bankly.core.model.sealed.onReady
import com.bankly.core.model.sealed.onSessionExpired
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MainActivityViewModel @Inject constructor(
    private val addDeviceToFirebaseUseCase: AddDeviceToFirebaseUseCase,
    private val getNotificationMessageUseCase: GetNotificationMessageUseCase,
    private val insertNotificationMessageUseCase: InsertNotificationMessageUseCase,
    private val insertRecentFundUseCase: InsertRecentFundUseCase,
    private val getRecentFundUseCase: GetRecentFundUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
    private val syncRecentFundingUseCase: SyncRecentFundingUseCase,
) : BaseViewModel<MainActivityEvent, MainActivityState, MainActivityOneShotState>(
    MainActivityState()
) {

    override suspend fun handleUiEvents(event: MainActivityEvent) {
        when (event) {
            is MainActivityEvent.AddDeviceToFirebase -> {
                addDeviceToFireBase(event.deviceId, event.userId)
            }

            is MainActivityEvent.OnReceiveNotificationMessage -> {
                setUiState {
                    copy(
                        showNotificationMessageDialog = true,
                        notificationMessage = event.notificationMessage
                    )
                }
                insertNotificationMessageUseCase.invoke(event.notificationMessage)
            }

            is MainActivityEvent.OnReceiveTransactionPayload -> {
                setUiState {
                    copy(
                        showTransactionAlertDialog = true,
                        transactionAlert = event.transactionPayload
                    )
                }
                insertRecentFundUseCase.invoke(event.transactionPayload.toRecentFund())
            }

            is MainActivityEvent.OnDismissNotificationMessageDialog -> {
                setUiState {
                    copy(
                        showNotificationMessageDialog = false,
                        notificationMessage = null,
                    )
                }
                markNotificationMessageAsSeen(dateTime = event.notificationMessage.dateTime)
            }

            is MainActivityEvent.OnDismissTransactionAlertDialog -> {
                setUiState { copy(showTransactionAlertDialog = false, transactionAlert = null) }
                event.transactionPayload.transactionReference?.let { ref: String ->
                    event.transactionPayload.sessionID?.let { sesId: String ->
                        markTransactionAlertAsSeen(
                            transactionRef = ref,
                            sessionId = sesId
                        )
                    }
                }
            }
        }
    }

    private fun addDeviceToFireBase(deviceToken: String, userId: String) {
        viewModelScope.launch {
            addDeviceToFirebaseUseCase.invoke(
                token = userPreferencesDataStore.data().token,
                body = AddDeviceTokenData(
                    userId = userId,
                    deviceId = deviceToken
                )
            ).onEach { resource ->
                resource.onReady { addDeviceToken ->
                    Log.d("debug add device to fcm", "onReady add device to fcm: $addDeviceToken")
                    userPreferencesDataStore.update { copy(fcmDeviceToken = deviceToken) }
                }
                resource.onFailure { message ->
                    Log.d("debug add device to fcm", "onFailure add device to fcm: $message")
                    setUiState { copy(showErrorDialog = true, errorDialogMessage = message) }
                }
                resource.onSessionExpired {
                    setOneShotState(MainActivityOneShotState.OnSessionExpired)
                }
            }.catch {
                Log.d(
                    "debug add device to fcm",
                    "catch add device to fcm: ${it.message}"
                )
                it.printStackTrace()
                setUiState {
                    copy(
                        showErrorDialog = true,
                        errorDialogMessage = it.message
                            ?: "Unable to register device for push notification"
                    )
                }
            }
                .launchIn(viewModelScope)
        }
    }

    private fun markNotificationMessageAsSeen(dateTime: String) {
        viewModelScope.launch {
            Log.d("debug mark as seen", "message id to be marked as seen: $dateTime")
            val notificationMessage = getNotificationMessageUseCase.invoke(dateTime)
            if (notificationMessage != null && !notificationMessage.seen) {
                insertNotificationMessageUseCase.invoke(notificationMessage.copy(seen = true))
            }
        }
    }

    private fun markTransactionAlertAsSeen(transactionRef: String, sessionId: String) {
        viewModelScope.launch {
            Log.d("debug", "Transaction Ref to be marked -> $transactionRef")
            val recentFund = getRecentFundUseCase.invoke(transactionRef, sessionId)
            if (recentFund != null && !recentFund.seen) {
                Log.d("debug", "Update as synced called")
                //this notifies the server that this transaction has been seen by the user
                syncRecentFundingUseCase.invoke(
                    userPreferencesDataStore.data().token,
                    SyncRecentFundingData(sessionId, Tools.serialNumber)
                )
                //update the app's local database as well
                insertRecentFundUseCase.invoke(recentFund.copy(seen = true))
            }
        }
    }

}
