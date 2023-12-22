package com.bankly.feature.paywithcard.navigation.viewmodel

import com.bankly.core.sealed.TransactionReceipt
import com.bankly.kozonpaymentlibrarymodule.posservices.AccountType

sealed interface PayWithCardScreenEvent {
    data class InitPayWithCard(
        val acctType: AccountType,
    ) :
        PayWithCardScreenEvent

    data class OnCardPaymentFailed(
        val cardPaymentReceipt: TransactionReceipt.CardPayment,
    ) : PayWithCardScreenEvent

    data class OnCardPaymentSuccessful(
        val cardPaymentReceipt: TransactionReceipt.CardPayment,
    ) : PayWithCardScreenEvent
}
