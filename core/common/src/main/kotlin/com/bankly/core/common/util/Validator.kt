package com.bankly.core.common.util

object Validator {
    fun validatePhoneNumber(phoneNumber: String): Boolean {
        return if (phoneNumber.length == 11) return true else false
    }
}