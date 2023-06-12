package com.bankly.core.common.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard =
        ContextCompat.getSystemService(this, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("label", text)
    clipboard?.setPrimaryClip(clip)
    Toast.makeText(this, "Account number copied", Toast.LENGTH_LONG).show()
}