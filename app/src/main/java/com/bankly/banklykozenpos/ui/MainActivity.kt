package com.bankly.banklykozenpos.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bankly.core.designsystem.theme.BanklyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            BanklyTheme {
                BanklyApp(
                    onCloseApp = {
                        finish()
                    },
                    activity = this,
                )
            }
        }
    }
}
