package com.bankly.core.common.util

object Validator {
    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return if (phoneNumber.length == 11) return true else false
    }

    fun doPassCodesMatch(passCode: String, confirmPassCode: String): Boolean {
        return passCode == confirmPassCode
    }

    fun isAccountNumberValid(accountNumber: String): Boolean {
        return accountNumber.length == 10
    }

    fun isAmountValid(amount: Double, minimumAmount: Double = 0.00): Boolean {
        return amount in minimumAmount..1_000_000.00
    }
}
