package com.bankly.core.common.ui.processtransaction

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.model.TransactionData
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.data.datastore.UserPreferencesDataStore
import com.bankly.core.domain.usecase.BankTransferUseCase
import com.bankly.core.domain.usecase.CardTransferUseCase
import com.bankly.core.domain.usecase.PayBillUseCase
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.core.sealed.onFailure
import com.bankly.core.sealed.onReady
import com.bankly.core.sealed.onSessionExpired
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProcessTransactionViewModel @Inject constructor(
    private val bankTransferUseCase: BankTransferUseCase,
    private val payBillUseCase: PayBillUseCase,
    private val cardTransferUseCase: CardTransferUseCase,
    private val userPreferencesDataStore: UserPreferencesDataStore,
) : BaseViewModel<ProcessTransactionScreenEvent, ProcessTransactionScreenState, ProcessTransactionScreenOneShotState>(
    ProcessTransactionScreenState(),
) {
    override suspend fun handleUiEvents(event: ProcessTransactionScreenEvent) {
        when (event) {
            is ProcessTransactionScreenEvent.ProcessTransaction -> {
                when (event.transactionData) {
                    is TransactionData.BankTransfer -> {
                        bankTransferUseCase.invoke(
                            userPreferencesDataStore.data().token,
                            event.transactionData.toBankTransferData(),
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
                            resource.onSessionExpired {
                                setOneShotState(ProcessTransactionScreenOneShotState.OnSessionExpired)
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

                    is TransactionData.BillPayment -> {
                        payBillUseCase.invoke(
                            userPreferencesDataStore.data().token,
                            event.transactionData.toBillPaymentData(),
                        ).onEach { resource ->
                            resource.onReady { billPayment: TransactionReceipt.BillPayment ->
                                setOneShotState(
                                    ProcessTransactionScreenOneShotState.GoToTransactionSuccessScreen(
                                        transactionReceipt = billPayment,
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
                            resource.onSessionExpired {
                                setOneShotState(ProcessTransactionScreenOneShotState.OnSessionExpired)
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

                    is TransactionData.CardTransfer -> {
                        cardTransferUseCase.invoke(
                            userPreferencesDataStore.data().token,
                            event.transactionData.toCardTransferData(),
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
                            resource.onSessionExpired {
                                setOneShotState(ProcessTransactionScreenOneShotState.OnSessionExpired)
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
                }
            }
        }
    }
}
