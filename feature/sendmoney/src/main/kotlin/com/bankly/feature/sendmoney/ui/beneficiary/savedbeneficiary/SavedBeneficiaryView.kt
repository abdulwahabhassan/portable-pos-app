package com.bankly.feature.sendmoney.ui.beneficiary.savedbeneficiary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.model.AccountNumberType
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.common.ui.view.EmptyStateView
import com.bankly.core.common.util.AmountFormatter
import com.bankly.core.common.util.AmountInputVisualTransformation
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.sendmoney.R
import com.bankly.feature.sendmoney.model.SavedBeneficiary
import com.bankly.feature.sendmoney.ui.beneficiary.BeneficiaryScreenState
import com.bankly.core.designsystem.R as DesignRes

@Composable
internal fun SavedBeneficiaryView(
    screenState: BeneficiaryScreenState,
    savedBeneficiaries: List<SavedBeneficiary>,
    selectedAccountNumberType: AccountNumberType,
    onBankNameDropDownIconClick: () -> Unit,
    onEnterPhoneOrAccountNumber: (TextFieldValue) -> Unit,
    onEnterNarration: (TextFieldValue) -> Unit,
    onEnterAmount: (TextFieldValue) -> Unit,
    channel: SendMoneyChannel,
    onContinueClick: () -> Unit,
    onChangeSelectedSavedBeneficiary: () -> Unit,
    onBeneficiarySelected: (SavedBeneficiary) -> Unit,
) {
    if (screenState.shouldShowSavedBeneficiaryList) {
        if (savedBeneficiaries.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
            ) {
                items(savedBeneficiaries) { item: SavedBeneficiary ->
                    SavedBeneficiaryItemView(
                        savedBeneficiary = item,
                        onClick = { beneficiary: SavedBeneficiary ->
                            onBeneficiarySelected(beneficiary)
                        },
                    )
                }
            }
        } else {
            EmptyStateView(stringResource(R.string.msg_no_saved_beneficiary))
        }
    } else {
        Column {
            Row(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.End),
            ) {
                BanklyClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.bodyMedium.toSpanStyle(),
                        ) {
                            append("Wrong beneficiary?")
                        }
                        append(" ")
                        withStyle(
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                                .toSpanStyle(),
                        ) {
                            append("Change")
                        }
                    },
                    onClick = onChangeSelectedSavedBeneficiary,
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    if (channel == SendMoneyChannel.BANKLY_TO_OTHER) {
                        Spacer(modifier = Modifier.padding(top = 8.dp))
                        BanklyInputField(
                            textFieldValue = screenState.bankNameTFV,
                            onTextFieldValueChange = { },
                            trailingIcon = BanklyIcons.ChevronDown,
                            readOnly = true,
                            onTrailingIconClick = onBankNameDropDownIconClick,
                            placeholderText = stringResource(R.string.msg_select_bank),
                            labelText = stringResource(R.string.msg_bank_name),
                            isError = screenState.isBankNameError,
                            feedbackText = screenState.bankNameFeedBack,
                            isEnabled = screenState.isUserInputEnabled,
                        )
                    }

                    BanklyInputField(
                        textFieldValue = screenState.accountOrPhoneTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onEnterPhoneOrAccountNumber(textFieldValue)
                        },
                        placeholderText = stringResource(
                            when (selectedAccountNumberType) {
                                AccountNumberType.ACCOUNT_NUMBER -> R.string.msg_enter_account_number
                                AccountNumberType.PHONE_NUMBER -> R.string.msg_enter_phone_number
                            },
                        ),
                        labelText = stringResource(
                            when (selectedAccountNumberType) {
                                AccountNumberType.ACCOUNT_NUMBER -> R.string.msg_account_number_label
                                AccountNumberType.PHONE_NUMBER -> R.string.msg_phone_number_label
                            },
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                        ),
                        trailingIcon = screenState.validationStatusIcon,
                        isError = screenState.isAccountOrPhoneError,
                        feedbackText = screenState.accountOrPhoneFeedBack,
                        isEnabled = screenState.isUserInputEnabled,
                    )

                    BanklyInputField(
                        textFieldValue = screenState.amountTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onEnterAmount(textFieldValue)
                        },
                        placeholderText = stringResource(R.string.msg_enter_amount),
                        labelText = stringResource(R.string.msg_amount_label),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Decimal,
                        ),
                        isError = screenState.isAmountError,
                        feedbackText = screenState.amountFeedBack,
                        isEnabled = screenState.isUserInputEnabled,
                        visualTransformation = AmountInputVisualTransformation(AmountFormatter()),
                    )

                    BanklyInputField(
                        textFieldValue = screenState.narrationTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onEnterNarration(textFieldValue)
                        },
                        placeholderText = stringResource(R.string.msg_enter_narration),
                        labelText = stringResource(R.string.msg_narration_label),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                        ),
                        isError = screenState.isNarrationError,
                        feedbackText = screenState.narrationFeedBack,
                        isEnabled = screenState.isUserInputEnabled,
                    )
                }

                item {
                    BanklyFilledButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        text = stringResource(R.string.action_continue),
                        onClick = onContinueClick,
                        isEnabled = screenState.isContinueButtonEnabled,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun SavedBeneficiaryPreview() {
    BanklyTheme {
        SavedBeneficiaryView(
            screenState = BeneficiaryScreenState(shouldShowSavedBeneficiaryList = false),
            savedBeneficiaries = SavedBeneficiary.mockOtherBanks(),
            selectedAccountNumberType = AccountNumberType.ACCOUNT_NUMBER,
            onBankNameDropDownIconClick = {},
            onEnterPhoneOrAccountNumber = {},
            onEnterNarration = {},
            onEnterAmount = {},
            channel = SendMoneyChannel.BANKLY_TO_BANKLY,
            onContinueClick = {},
            onChangeSelectedSavedBeneficiary = {},
            onBeneficiarySelected = {},
        )
    }
}

@Composable
private fun SavedBeneficiaryItemView(
    savedBeneficiary: SavedBeneficiary,
    onClick: (SavedBeneficiary) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(16.dp),
                clip = true,
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = {
                    onClick(savedBeneficiary)
                },
                enabled = true,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = true,
                    color = MaterialTheme.colorScheme.primary,
                ),
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp),
            )
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            painter = painterResource(id = savedBeneficiary.bankLogo ?: BanklyIcons.Bank),
            contentDescription = null,
            alignment = Alignment.Center,
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = savedBeneficiary.name,
                style = MaterialTheme.typography.bodyMedium,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = savedBeneficiary.bankName,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary),
                    minLines = 1,
                    maxLines = 1,
                )
                Icon(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    painter = painterResource(id = BanklyIcons.Dot),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                )
                Text(
                    text = savedBeneficiary.accountNumber,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.tertiary),
                    minLines = 1,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun SavedBeneficiaryItemPreview() {
    BanklyTheme {
        SavedBeneficiaryItemView(
            savedBeneficiary = SavedBeneficiary(
                "Hassan Abdulwahab",
                "Access Bank PLC",
                "9844803022",
                DesignRes.drawable.ic_first_bank,
                1,
            ),
            onClick = {},
        )
    }
}
