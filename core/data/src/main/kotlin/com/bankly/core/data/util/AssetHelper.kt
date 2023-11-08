package com.bankly.core.data.util

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

inline fun <reified T> loadJsonFromAsset(context: Context, fileName: String, json: Json): T? {
    return try {
        val stream = context.assets.open(fileName)
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        val stringJson = String(buffer, charset("UTF-8"))
        json.decodeFromString(stringJson)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}