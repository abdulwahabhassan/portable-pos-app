package com.bankly.core.model

data class ConfirmTransactionDetails(
    val phoneNumber: String,
    val accountName: String,
    val amount: String,
    val vat: String,
    val fee: String
) {
    fun toDetailsMap(): Map<String, String> {
        return mapOf(
            "Phone Number" to phoneNumber,
            "Account Name" to accountName,
            "Amount" to amount,
            "VAT" to vat,
            "Fee" to fee
        )
    }
}