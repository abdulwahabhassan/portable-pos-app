package com.bankly.banklykozenpos.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun PosScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "POS Screen")
    }
}

@Composable
private fun PosScreenPreview() {
    BanklyTheme {
        PosScreen()
    }
}