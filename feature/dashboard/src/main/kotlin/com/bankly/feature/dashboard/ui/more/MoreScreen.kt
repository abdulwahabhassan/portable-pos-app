package com.bankly.feature.dashboard.ui.more

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun MoreScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "More Screen")
    }
}

@Composable
private fun MoreScreenPreview() {
    BanklyTheme {
        MoreScreen()
    }
}