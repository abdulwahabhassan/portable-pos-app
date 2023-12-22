package com.bankly.core.database.util

object Formatter {
    fun formatServerDateTimeToLocalDate(value: String): String {
        return value.takeIf { it.length > 10 }?.substring(0, 10) ?: value
    }
}