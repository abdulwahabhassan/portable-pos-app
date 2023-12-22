package com.bankly.feature.cardtransfer.navigation.viewmodel

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.domain.usecase.SaveToEodUseCase
import com.bankly.core.sealed.TransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardTransferViewModel @Inject constructor(
    private val saveToEodUseCase: SaveToEodUseCase,
) : BaseViewModel<CardTransferScreenEvent, CardTransferScreenState, CardTransactionScreenOneShotState>(
    CardTransferScreenState(),
) {
    override suspend fun handleUiEvents(event: CardTransferScreenEvent) {
        when (event) {
            is CardTransferScreenEvent.InitCardTransferData -> {
                Tools.SetAccountType(event.acctType)
                Tools.TransactionAmount = event.transactionData.transactionAmount
                Tools.transactionType = com.bankly.kozonpaymentlibrarymodule.posservices.TransactionType.CASHOUT
                setOneShotState(CardTransactionScreenOneShotState.ProcessPayment(event.transactionData))
            }

            is CardTransferScreenEvent.OnCardTransferFailed -> {
                val cardTransferReceipt = TransactionReceipt.CardTransfer(
                    beneficiaryAccountNumber = event.transactionData.accountNumber,
                    beneficiaryBankName = event.transactionData.bankName,
                    beneficiaryName = event.transactionData.accountName,
                    amount = event.transactionData.amount,
                    reference = event.cardPaymentReceipt.reference,
                    message = event.cardPaymentReceipt.message,
                    dateCreated = event.cardPaymentReceipt.dateTime,
                    statusName = event.cardPaymentReceipt.statusName,
                    sessionId = "",
                    terminalId = event.cardPaymentReceipt.terminalId,
                    cardType = event.cardPaymentReceipt.cardType,
                    cardNumber = event.cardPaymentReceipt.cardNumber,
                    responseCode = event.cardPaymentReceipt.responseCode,
                    rrn = event.cardPaymentReceipt.rrn,
                    stan = event.cardPaymentReceipt.stan
                )
                saveToEod(cardTransferReceipt)
                setOneShotState(CardTransactionScreenOneShotState.GoToFailedTransactionRoute(cardTransferReceipt, event.message))
            }

            is CardTransferScreenEvent.OnCardTransferSuccessful -> {
                val cardTransferReceipt = event.cardTransferReceipt.copy(
                    terminalId = event.cardPaymentReceipt.terminalId,
                    cardType = event.cardPaymentReceipt.cardType,
                    cardNumber = event.cardPaymentReceipt.cardNumber,
                    responseCode = event.cardPaymentReceipt.responseCode,
                    rrn = event.cardPaymentReceipt.rrn,
                    stan = event.cardPaymentReceipt.stan
                )
                saveToEod(cardTransferReceipt)
                setOneShotState(CardTransactionScreenOneShotState.GoToTransactionSuccessfulRoute(cardTransferReceipt))
            }
        }
    }

    private fun saveToEod(transaction: TransactionReceipt) {
        viewModelScope.launch {
            saveToEodUseCase.invoke(transaction)
        }
    }

}
