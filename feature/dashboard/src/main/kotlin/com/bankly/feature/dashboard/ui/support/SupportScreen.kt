package com.bankly.feature.dashboard.ui.support

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun SupportRoute() {
    SupportScreen()
}

@Composable
fun SupportScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Support Screen")
    }
}

@Composable
private fun SupportScreenPreview() {
    BanklyTheme {
        SupportScreen()
    }
}