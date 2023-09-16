package com.bankly.banklykozenpos

import android.app.Application
import com.bankly.kozonpaymentlibrarymodule.posservices.Tools
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BanklyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Tools.initializeEmv(this.applicationContext)
    }
}
