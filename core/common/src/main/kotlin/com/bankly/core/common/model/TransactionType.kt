package com.bankly.core.common.model

enum class TransactionType(val title: String) {
    CARD_WITHDRAWAL("Card Withdrawal"),
    CARD_TRANSFER("Card Transfer"),
    BANK_TRANSFER_WITH_PHONE_NUMBER("Bank Transfer"),
    BANK_TRANSFER_WITH_ACCOUNT_NUMBER("Bank Transfer"),
    BILL_PAYMENT_AIRTIME("Airtime"),
    BILL_PAYMENT_INTERNET_DATA("Internet Data"),
    BILL_PAYMENT_ELECTRICITY("Electricity"),
    BILL_PAYMENT_CABLE_TV("Cable TV")
}
