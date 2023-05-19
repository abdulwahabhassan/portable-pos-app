package com.bankly.core.common.decoder

interface StringDecoder {
    fun decodeString(encodedString: String): String
}
