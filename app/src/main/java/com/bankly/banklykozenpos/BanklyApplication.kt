package com.bankly.banklykozenpos

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BanklyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}

