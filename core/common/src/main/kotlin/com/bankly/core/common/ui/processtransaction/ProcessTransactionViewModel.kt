package com.bankly.core.common.ui.processtransaction

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.TransactionType
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.TransferUseCase
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onReady
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProcessTransactionViewModel @Inject constructor(
    private val transferUseCase: TransferUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<ProcessTransactionScreenEvent, ProcessTransactionScreenState, ProcessTransactionScreenOneShotState>(
    ProcessTransactionScreenState(),
) {
    override suspend fun handleUiEvents(event: ProcessTransactionScreenEvent) {
        when (event) {
            is ProcessTransactionScreenEvent.ProcessTransaction -> {
                when (event.transactionData.transactionType) {
                    TransactionType.BANK_TRANSFER_WITH_ACCOUNT_NUMBER -> {
                        transferUseCase.performTransferToAccountNumber(
                            userPreferencesDataStore.data().token,
                            event.transactionData.toAccountNumberTransferData(),
                        ).onEach { resource ->
                            resource.onReady { transactionReceipt: TransactionReceipt ->
                                setOneShotState(
                                    ProcessTransactionScreenOneShotState.GoToTransactionSuccessScreen(
                                        transactionReceipt = transactionReceipt,
                                    ),
                                )
                            }
                            resource.onFailure { message: String ->
                                setOneShotState(
                                    ProcessTransactionScreenOneShotState.GoToTransactionFailedScreen(
                                        message = message,
                                    ),
                                )
                            }
                        }.catch {
                            it.printStackTrace()
                            setOneShotState(
                                ProcessTransactionScreenOneShotState.GoToTransactionFailedScreen(
                                    message = it.message ?: "An unexpected error occurred",
                                ),
                            )
                        }.launchIn(viewModelScope)
                    }

                    TransactionType.BANK_TRANSFER_WITH_PHONE_NUMBER -> {
                        transferUseCase.performPhoneNumberTransfer(
                            userPreferencesDataStore.data().token,
                            event.transactionData.toPhoneNumberTransferData(),
                        ).onEach { resource ->
                            resource.onReady { transactionReceipt: TransactionReceipt ->
                                setOneShotState(
                                    ProcessTransactionScreenOneShotState.GoToTransactionSuccessScreen(
                                        transactionReceipt = transactionReceipt,
                                    ),
                                )
                            }
                            resource.onFailure { message: String ->
                                setOneShotState(
                                    ProcessTransactionScreenOneShotState.GoToTransactionFailedScreen(
                                        message = message,
                                    ),
                                )
                            }
                        }.catch {
                            it.printStackTrace()
                            setOneShotState(
                                ProcessTransactionScreenOneShotState.GoToTransactionFailedScreen(
                                    message = it.message ?: "An unexpected error occurred",
                                ),
                            )
                        }.launchIn(viewModelScope)
                    }

                    TransactionType.CARD_WITHDRAWAL -> {
                        setOneShotState(
                            ProcessTransactionScreenOneShotState.GoToTransactionFailedScreen(
                                message = "This feature is still a work in progress!",
                            ),
                        )
                    }

                    TransactionType.CARD_TRANSFER -> {
                        setOneShotState(
                            ProcessTransactionScreenOneShotState.GoToTransactionFailedScreen(
                                message = "This feature is still a work in progress!",
                            ),
                        )
                    }
                }
            }
        }
    }
}
