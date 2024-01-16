package com.bankly.core.common.util

import android.content.Intent
import android.os.Build
import android.os.Bundle

inline fun <reified T> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else ->
        @Suppress("DEPRECATION")
        getParcelable(key)
            as? T
}
inline fun <reified T> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else ->
        @Suppress("DEPRECATION")
        getParcelableExtra(key)
            as? T
}
