package com.bankly.feature.dashboard.ui.pos

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bankly.core.common.util.Formatter.formatAmount
import com.bankly.core.designsystem.component.BanklyActionDialog
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.model.PassCodeKey
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.dashboard.R


@Composable
fun PosTab(
    onContinueClick: (Double) -> Unit
) {
    PosScreen(onContinueClick = onContinueClick)
}

@Composable
fun PosScreen(
    onContinueClick: (Double) -> Unit
) {
    var displayedAmount by remember { mutableStateOf("0.00") }
    var showActionDialog by remember { mutableStateOf(false) }
    var actionMessage by remember { mutableStateOf("") }

    if (showActionDialog) {
        BanklyActionDialog(
            title = stringResource(R.string.title_validation_error),
            subtitle = actionMessage,
            positiveActionText = "Okay",
            positiveAction = {
                showActionDialog = false
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.msg_enter_amount),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.symbol_naira),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    ),

                    )
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp),
                    text = displayedAmount,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                                    handleKeyPress(
                                        item = item,
                                        displayValue = displayedAmount,
                                        onDisplayValueUpdated = { updatedDisplayValue: String ->
                                            displayedAmount = updatedDisplayValue
                                        },
                                        onValidationError = { message: String ->
                                            actionMessage = message
                                            showActionDialog = true
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(LocalConfiguration.current.screenHeightDp.dp * 0.08f)
                                    .align(Alignment.Center)
                                    .fillMaxSize(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
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
            isEnabled = displayedAmount != "0.00",
            onClick = {
                onContinueClick(displayedAmount.replace(",", "").toDouble())
            }
        )
    }
}

private fun handleKeyPress(
    item: PassCodeKey,
    displayValue: String,
    onDisplayValueUpdated: (String) -> Unit,
    onValidationError: (String) -> Unit
) {
    when (item) {
        PassCodeKey.DELETE -> {
            val formattedAmount =
                if (displayValue != "0.00" && displayValue.length != 4) {
                    formatAmount(displayValue.dropLast(3).replace(",", "").dropLast(1))
                } else {
                    displayValue.replaceFirstChar { "0" }
                }
            onDisplayValueUpdated(formattedAmount)
        }

        else -> {
            val formattedAmount = formatAmount(
                displayValue.dropLast(3).replace(",", "") + item.value
            )
            val doubleValue = formattedAmount.replace(",", "").toDouble()
            if (doubleValue <= 1_000_000) {
                onDisplayValueUpdated(formattedAmount)
            } else {
                onValidationError("Maximum amount exceeded")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PosScreenPreview() {
    BanklyTheme {
        PosScreen {}
    }
}