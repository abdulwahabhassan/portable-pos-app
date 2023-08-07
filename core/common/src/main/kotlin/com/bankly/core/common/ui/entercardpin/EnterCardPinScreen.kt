package com.bankly.core.common.ui.entercardpin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyPassCodeInputField
import com.bankly.core.designsystem.component.BanklyTitleBar
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme

@Composable
fun EnterCardPinRoute(
    onContinueClick: (String) -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit,
) {
    EnterCardPinScreen(
        onContinueClick = onContinueClick,
        onBackPress = onBackPress,
        onCloseClick = onCloseClick
    )
}

@Composable
fun EnterCardPinScreen(
    onContinueClick: (String) -> Unit,
    onBackPress: () -> Unit,
    onCloseClick: () -> Unit
) {
    var pin by rememberSaveable { mutableStateOf(List(4) { "" }) }
    var showActionDialog by rememberSaveable { mutableStateOf(false) }
    var actionTitle by rememberSaveable { mutableStateOf("") }
    var actionMessage by rememberSaveable { mutableStateOf("") }

    if (showActionDialog) {
        BanklyActionDialog(
            title = actionTitle,
            subtitle = actionMessage,
            positiveActionText = "No",
            positiveAction = {
                showActionDialog = false
            },
            negativeActionText = "Yes, cancel",
            negativeAction = {
                showActionDialog = false
                onCloseClick()
            }
        )
    }

    fun triggerCancelDialog() {
        actionTitle = "Cancel Transaction?"
        actionMessage = "Are you sure you want to cancel?"
        showActionDialog = true
    }

    BackHandler {
        triggerCancelDialog()
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BanklyTitleBar(
                title = "Enter Card PIN",
                onBackPress = {
                    triggerCancelDialog()
                },
                onCloseClick = {
                    triggerCancelDialog()
                }
            )
            BanklyPassCodeInputField(passCode = pin)
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(PassCodeKey.values().map {
                    when (it) {
                        PassCodeKey.DELETE -> PassCodeKey.DONE
                        PassCodeKey.DONE -> PassCodeKey.DELETE
                        else -> it
                    }
                }.toMutableList()) { item ->
                    if (item == PassCodeKey.DONE) {
                        Box {}
                    } else {
                        Box(contentAlignment = Alignment.Center) {
                            Button(
                                onClick = {
                                    when (item) {
                                        PassCodeKey.DELETE -> {
                                            val index =
                                                pin.indexOfLast { it.isNotEmpty() }
                                            if (index != -1) {
                                                val newPin = pin.toMutableList()
                                                newPin[index] = ""
                                                pin = newPin
                                            }
                                        }

                                        else -> {
                                            val index =
                                                pin.indexOfFirst { it.isEmpty() }
                                            if (index != -1) {
                                                val newPin = pin.toMutableList()
                                                newPin[index] = item.value
                                                pin = newPin
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(LocalConfiguration.current.screenHeightDp.dp * 0.08f)
                                    .align(Alignment.Center)
                                    .fillMaxSize(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(
                                        alpha = 0.3f
                                    ),
                                    contentColor = MaterialTheme.colorScheme.primary
                                ),
                                shape = RoundedCornerShape(25),
                                elevation = ButtonDefaults.buttonElevation(
                                    0.dp,
                                    0.dp,
                                    0.dp,
                                    0.dp,
                                    0.dp
                                ),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                if (item == PassCodeKey.DELETE) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        painter = painterResource(id = BanklyIcons.Delete),
                                        contentDescription = "Delete icon",
                                        tint = Color.Unspecified
                                    )
                                } else {
                                    Text(
                                        text = item.value,
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        BanklyFilledButton(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth(),
            text = "Continue",
            isEnabled = pin.none { it.isEmpty() },
            onClick = {
                onContinueClick(pin.joinToString { "" })
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PosScreenPreview() {
    BanklyTheme {
        EnterCardPinScreen(
            onContinueClick = {},
            onBackPress = {},
            onCloseClick = {}
        )
    }
}