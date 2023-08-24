package com.bankly.banklykozenpos.ui

import ProcessPayment
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bankly.core.data.util.NetworkMonitor
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.kozonpaymentlibrarymodule.helper.ConfigParameters
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkMonitor: NetworkMonitor
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            BanklyTheme {
                BanklyApp(
                    networkMonitor = networkMonitor,
                    onCloseApp = {
                        finish()
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        ConfigParameters.downloadTmsParams(this)
    }
}
