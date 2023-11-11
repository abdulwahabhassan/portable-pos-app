package com.bankly.core.data.util

import android.util.Log
import com.google.android.gms.common.util.Base64Utils

fun getUsernameFromAccessToken(token: String): String? {
    return try {
        val decodeString = Base64Utils.decode(token).decodeToString()
        val result = Regex("\"username\":\"\\d+\"").find(decodeString)
        Log.d("debug token", "Decoded Access token Regex match result for username -> ${result?.value}")
        val username = result?.value?.let { Regex("\\d+").find(it) }
        Log.d("debug token", "Decoded Access token username -> ${username?.value}")
        username?.value
    } catch (e: Exception) {
        Log.d("debug token", "Get Agent user name from Access token: ${e.message}")
        null
    }
}