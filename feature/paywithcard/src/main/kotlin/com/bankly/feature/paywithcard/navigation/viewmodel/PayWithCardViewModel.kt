package com.bankly.feature.paywithcard.navigation.viewmodel

import androidx.lifecycle.viewModelScope
import com.bankly.core.common.viewmodel.BaseViewModel
import com.bankly.core.domain.usecase.SaveToEodUseCase
import com.bankly.core.model.sealed.TransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayWithCardViewModel @Inject constructor(
    private val saveToEodUseCase: SaveToEodUseCase,
) : BaseViewModel<PayWithCardScreenEvent, PayWithCardScreenState, PayWithCardScreenOneShotState>(
    PayWithCardScreenState(),
) {
    override suspend fun handleUiEvents(event: PayWithCardScreenEvent) {
        when (event) {
            is PayWithCardScreenEvent.InitPayWithCard -> {
                Tools.SetAccountType(event.acctType)
                Tools.transactionType =
                    com.bankly.kozonpaymentlibrarymodule.posservices.TransactionType.CASHOUT
                setOneShotState(PayWithCardScreenOneShotState.ProcessPayment)
            }

            is PayWithCardScreenEvent.OnCardPaymentFailed -> {
                saveToEod(event.cardPaymentReceipt)
                setOneShotState(
                    PayWithCardScreenOneShotState.GoToFailedTransactionRoute(
                        cardTransferReceipt = event.cardPaymentReceipt,
                        message = event.cardPaymentReceipt.message
                    )
                )
            }

            is PayWithCardScreenEvent.OnCardPaymentSuccessful -> {
                saveToEod(event.cardPaymentReceipt)
                setOneShotState(PayWithCardScreenOneShotState.GoToTransactionSuccessfulRoute(event.cardPaymentReceipt))
            }

        }
    }

    private fun saveToEod(transaction: TransactionReceipt) {
        viewModelScope.launch {
            saveToEodUseCase.invoke(transaction)
        }
    }
}
