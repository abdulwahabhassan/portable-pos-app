package com.bankly.core.network.model.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BanklyPhoneTransactionResult(
    val id: Long?,
    val reference: String?,
    val transferType: String?,
    val description: String?,
    val narration: String?,
    @SerialName("accountId")
    val accountID: Long?,
    val accountNumber: String?,
    val accountName: String?,
    val amount: Double?,
    @SerialName("currencyId")
    val currencyID: Long?,
    val recipientBank: String?,
    val recipientAccountNo: String?,
    val recipientAccountName: String?,
    val transferredOn: String?,
    val polled: Boolean?,
    val account: String? = null,
    val currency: String? = null,
)
