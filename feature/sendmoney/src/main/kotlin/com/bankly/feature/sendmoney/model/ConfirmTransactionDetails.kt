package com.bankly.feature.sendmoney.model

data class ConfirmTransactionDetails(
    val phoneOrAccountNumber: String,
    val accountName: String,
    val amount: Double,
    val vat: Double,
    val fee: Double,
    val sendMoneyChannel: SendMoneyChannel,
    val bankName: String,
    val bankId: String,
    val narration: String
) {
    fun toDetailsMap(): Map<String, String> {
        return mapOf(
            "Phone Number" to phoneOrAccountNumber,
            "Account Name" to accountName,
            "Amount" to amount.toString(),
            "VAT" to vat.toString(),
            "Fee" to fee.toString()
        )
    }
}