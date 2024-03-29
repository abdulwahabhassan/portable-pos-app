package com.bankly.core.common.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Context.copyToClipboard(text: CharSequence, toastMessage: String) {
    val clipboard =
        ContextCompat.getSystemService(this, ClipboardManager::class.java)
    val clip = ClipData.newPlainText("label", text)
    clipboard?.setPrimaryClip(clip)
    Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
}
