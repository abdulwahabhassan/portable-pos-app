package com.bankly.core.common.util

import java.text.DecimalFormat

object Formatter {
    fun formatAmount(value: Any, includeNairaSymbol: Boolean = false, addSpace: Boolean = false): String {
        val valueToBeFormatted: Number = if (value is String) {
            value.toDouble()
        } else {
            value as Number
        }

        val df = DecimalFormat("##,###,##0.00")
        return if (includeNairaSymbol) "₦${if (addSpace) " " else ""}${df.format(valueToBeFormatted)}" else df.format(
            valueToBeFormatted
        )
    }
}
