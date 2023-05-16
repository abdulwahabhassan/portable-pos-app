package com.bankly.banklykozenpos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bankly.banklykozenpos.ui.BanklyApp
import com.bankly.core.designsystem.theme.BanklyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            BanklyTheme {
                BanklyApp()
            }
        }
    }
}
