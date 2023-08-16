package com.bankly.core.util


object Formatter {
    fun formatServerDateTime(value: String): String {
        return value.replace("T", " ").substring(0, 19)
    }
}