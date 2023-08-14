package com.bankly.core.model

data class TransactionDetails(
    val transactionType: String,
    val status: String,
    val description: String,
    val terminalId: String,
    val cardType: String,
    val transactionRef: String,
    val cardNumber: String,
    val dateTime: String,
    val responseCode: String,
    val rrn: String,
    val stan: String,
    val amount: String,
    val message: String,
) {
    fun toDetailsMap(): Map<String, String> {
        return mapOf(
            "Transaction Type" to transactionType,
            "Status" to status,
            "Description" to description,
            "Terminal ID" to terminalId,
            "Card Type" to cardType,
            "Transaction REF" to transactionRef,
            "Card Number" to cardNumber.maskCardNumber(),
            "Date/Time" to dateTime,
            "Response Code" to responseCode,
            "RRN" to rrn,
            "STAN" to stan,
        )
    }
}

private fun String.maskCardNumber(): String {
    return if (this.length > 12) this.replaceRange(6..11, "******") else ""
}