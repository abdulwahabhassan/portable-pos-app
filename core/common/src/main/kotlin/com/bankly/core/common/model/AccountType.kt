package com.bankly.core.common.model

enum class AccountType(val title: String) {
    DEFAULT(title = "Default"),
    SAVINGS(title = "Savings"),
    CURRENT(title = "Current"),
    CREDIT(title = "Credit")
}