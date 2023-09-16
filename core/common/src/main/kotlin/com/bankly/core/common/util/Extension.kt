package com.bankly.core.common.util

fun String.maskCardNumber(): String {
    return if (this.length > 12) this.replaceRange(6..11, "******") else ""
}
