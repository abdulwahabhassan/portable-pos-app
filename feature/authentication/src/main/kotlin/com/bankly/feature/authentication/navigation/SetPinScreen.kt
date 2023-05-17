package com.bankly.feature.authentication.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bankly.core.designsystem.component.BanklyNumericKeyboard
import com.bankly.core.designsystem.component.BanklyPassCodeInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.authentication.R

@Composable
fun SetPinScreen() {
    val isError by remember { mutableStateOf(false) }
    var pin by remember { mutableStateOf(List(4) { "" }) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            BanklyTitleBar(
                onBackClick = {},
                title = stringResource(R.string.msg_set_pin),
                currentPage = 2,
                totalPage = 3
            )
            BanklyPassCodeInputField(passCode = pin, isError = isError)
        }

        item {
            BanklyNumericKeyboard(onKeyPressed = { key ->
                when (key) {
                    PassCodeKey.DELETE -> {
                        val index = pin.indexOfLast { it.isNotEmpty() }
                        if (index != -1) {
                            val newPin = pin.toMutableList()
                            newPin[index] = ""
                            pin = newPin
                        }
                    }
                    PassCodeKey.DONE -> {}
                    else -> {
                        val index = pin.indexOfFirst { it.isEmpty() }
                        if (index != -1) {
                            val newPin = pin.toMutableList()
                            newPin[index] = key.value
                            pin = newPin
                        }
                    }
                }
            })
        }
    }
}


@Composable
@Preview(showBackground = true)
fun SetPinScreenPreview() {
    BanklyTheme {
        SetPinScreen()
    }
}