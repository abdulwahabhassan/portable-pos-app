package com.bankly.core.util

object Formatter {
    fun formatServerDateTime(value: String): String {
        return value.takeIf { it.length > 19 }?.replace("T", " ")?.substring(0, 19) ?: value
    }
}
