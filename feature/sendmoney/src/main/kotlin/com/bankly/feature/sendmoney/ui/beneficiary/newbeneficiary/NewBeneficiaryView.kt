package com.bankly.feature.sendmoney.ui.beneficiary.newbeneficiary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.model.AccountNumberType
import com.bankly.core.common.model.SendMoneyChannel
import com.bankly.core.common.util.AmountFormatter
import com.bankly.core.common.util.AmountInputVisualTransformation
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklySelectionDialogMenu
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.feature.sendmoney.R
import com.bankly.feature.sendmoney.ui.beneficiary.BeneficiaryScreenState

@Composable
internal fun NewBeneficiaryView(
    screenState: BeneficiaryScreenState,
    onTypeSelected: (AccountNumberType) -> Unit,
    selectedAccountNumberType: AccountNumberType,
    onBankNameDropDownIconClick: () -> Unit,
    onEnterPhoneOrAccountNumber: (TextFieldValue) -> Unit,
    onEnterNarration: (TextFieldValue) -> Unit,
    onEnterAmount: (TextFieldValue) -> Unit,
    channel: SendMoneyChannel,
    onContinueClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.padding(top = 16.dp))
            if (channel == SendMoneyChannel.BANKLY_TO_BANKLY) {
                BanklySelectionDialogMenu(
                    label = stringResource(id = R.string.msg_type_label),
                    items = AccountNumberType.values().toList().map { accountNumberType: AccountNumberType -> accountNumberType.title },
                    selectedIndex = AccountNumberType.values().map { accountNumberType: AccountNumberType -> accountNumberType.title }
                        .indexOf(screenState.accountNumberTypeTFV.text),
                    onItemSelected = { index, _ ->
                        onTypeSelected(AccountNumberType.values().toList()[index])
                    },
                    enabled = screenState.isUserInputEnabled,
                    isError = screenState.isTypeError,
                    feedBack = screenState.typeFeedBack,
                    placeholder = stringResource(id = R.string.msg_select_type),
                )
            }

            if (channel == SendMoneyChannel.BANKLY_TO_OTHER) {
                Spacer(modifier = Modifier.padding(top = 8.dp))
                BanklyInputField(
                    textFieldValue = screenState.bankNameTFV,
                    onTextFieldValueChange = { },
                    trailingIcon = BanklyIcons.ChevronDown,
                    readOnly = true,
                    placeholderText = stringResource(R.string.msg_select_bank),
                    labelText = stringResource(R.string.msg_bank_name),
                    isError = screenState.isBankNameError,
                    feedbackText = screenState.bankNameFeedBack,
                    isEnabled = screenState.isUserInputEnabled,
                    onSurfaceAreaClick = onBankNameDropDownIconClick,
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

@Composable
@Preview(showBackground = true)
private fun NewBeneficiaryViewPreview() {
    BanklyTheme {
        NewBeneficiaryView(
            screenState = BeneficiaryScreenState(),
            onTypeSelected = {},
            selectedAccountNumberType = AccountNumberType.ACCOUNT_NUMBER,
            onBankNameDropDownIconClick = {},
            onEnterPhoneOrAccountNumber = {},
            onEnterNarration = {},
            onEnterAmount = {},
            channel = SendMoneyChannel.BANKLY_TO_BANKLY,
            onContinueClick = {},
        )
    }
}
