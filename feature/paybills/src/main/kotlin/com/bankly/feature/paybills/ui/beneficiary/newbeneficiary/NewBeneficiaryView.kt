package com.bankly.feature.paybills.ui.beneficiary.newbeneficiary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.common.util.AmountFormatter
import com.bankly.core.common.util.AmountInputVisualTransformation
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklySwitchButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.paybills.R
import com.bankly.feature.paybills.model.BillType
import com.bankly.feature.paybills.ui.beneficiary.BeneficiaryScreenState

@Composable
internal fun NewBeneficiaryView(
    screenState: BeneficiaryScreenState,
    onProviderDropDownIconClick: () -> Unit,
    onPlanDropDownIconClick: (BillType) -> Unit,
    onEnterPhoneNumber: (TextFieldValue) -> Unit,
    onEnterMeterNumber: (TextFieldValue) -> Unit,
    onEnterIDorCardNumber: (TextFieldValue) -> Unit,
    onEnterAmount: (TextFieldValue) -> Unit,
    billType: BillType,
    onContinueClick: () -> Unit,
    onToggleSaveAsBeneficiary: (Boolean) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.padding(top = 16.dp))
            BanklyInputField(
                textFieldValue = screenState.providerTFV,
                onTextFieldValueChange = { },
                trailingIcon = BanklyIcons.ChevronDown,
                readOnly = true,
                placeholderText = stringResource(R.string.msg_select_provider),
                labelText = stringResource(
                    when (billType) {
                        BillType.AIRTIME, BillType.INTERNET_DATA -> R.string.msg_network_provider
                        BillType.ELECTRICITY -> R.string.msg_electricity_provider
                        BillType.CABLE_TV -> R.string.msg_cable_tv_provider
                    }
                ),
                isError = screenState.isProviderError,
                feedbackText = screenState.providerFeedBack,
                isEnabled = screenState.isProviderFieldEnable,
                onSurfaceAreaClick = onProviderDropDownIconClick
            )

            Spacer(modifier = Modifier.padding(top = 8.dp))

            when (billType) {
                BillType.INTERNET_DATA -> {
                    BanklyInputField(
                        textFieldValue = screenState.planTFV,
                        onTextFieldValueChange = { },
                        trailingIcon = BanklyIcons.ChevronDown,
                        readOnly = true,
                        placeholderText = stringResource(R.string.msg_select_data_plan),
                        labelText = stringResource(R.string.title_data_plan),
                        isError = screenState.isPlanError,
                        feedbackText = screenState.planFeedBack,
                        isEnabled = screenState.isPlanFieldEnabled,
                        onSurfaceAreaClick = {
                            onPlanDropDownIconClick(billType)
                        }
                    )

                    Spacer(modifier = Modifier.padding(top = 8.dp))

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
                        isEnabled = screenState.isAmountFieldEnabled,
                        visualTransformation = AmountInputVisualTransformation(AmountFormatter()),
                    )
                }

                BillType.CABLE_TV -> {
                    BanklyInputField(
                        textFieldValue = screenState.planTFV,
                        onTextFieldValueChange = { },
                        trailingIcon = BanklyIcons.ChevronDown,
                        readOnly = true,
                        placeholderText = stringResource(R.string.msg_select_cable_plan),
                        labelText = stringResource(R.string.title_cable_plan),
                        isError = screenState.isPlanError,
                        feedbackText = screenState.planFeedBack,
                        isEnabled = screenState.isPlanFieldEnabled,
                        onSurfaceAreaClick = {
                            onPlanDropDownIconClick(billType)
                        }
                    )

                    Spacer(modifier = Modifier.padding(top = 8.dp))

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
                        isEnabled = screenState.isAmountFieldEnabled,
                        visualTransformation = AmountInputVisualTransformation(AmountFormatter()),
                    )

                    Spacer(modifier = Modifier.padding(top = 8.dp))

                    BanklyInputField(
                        textFieldValue = screenState.cableTvNumberTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onEnterIDorCardNumber(textFieldValue)
                        },
                        placeholderText = stringResource(R.string.msg_enter_iuc_or_decoder_number),
                        labelText = stringResource(R.string.msg_id_or_card_number_label),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                        ),
                        trailingIcon = screenState.validationStatusIcon,
                        isError = screenState.isCableTvNumberError,
                        feedbackText = screenState.cableTvNumberFeedBack,
                        isEnabled = screenState.isCableTvNumberFieldEnabled,
                    )

                }

                BillType.ELECTRICITY -> {
                    BanklyInputField(
                        textFieldValue = screenState.planTFV,
                        onTextFieldValueChange = { },
                        trailingIcon = BanklyIcons.ChevronDown,
                        readOnly = true,
                        placeholderText = stringResource(R.string.msg_select_electricity_package),
                        labelText = stringResource(R.string.title_electricity_package),
                        isError = screenState.isPlanError,
                        feedbackText = screenState.planFeedBack,
                        isEnabled = screenState.isPlanFieldEnabled,
                        onSurfaceAreaClick = {
                            onPlanDropDownIconClick(billType)
                        }
                    )

                    Spacer(modifier = Modifier.padding(top = 8.dp))

                    BanklyInputField(
                        textFieldValue = screenState.meterNumberTFV,
                        onTextFieldValueChange = { textFieldValue ->
                            onEnterMeterNumber(textFieldValue)
                        },
                        placeholderText = stringResource(R.string.msg_enter_meter_number),
                        labelText = stringResource(R.string.msg_meter_number_label),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                        ),
                        trailingIcon = screenState.validationStatusIcon,
                        isError = screenState.isMeterNumberError,
                        feedbackText = screenState.meterNumberFeedBack,
                        isEnabled = screenState.isMeterNumberFieldEnabled,
                    )

                    Spacer(modifier = Modifier.padding(top = 8.dp))

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
                        isEnabled = screenState.isAmountFieldEnabled,
                        visualTransformation = AmountInputVisualTransformation(AmountFormatter()),
                    )
                }

                BillType.AIRTIME -> {
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
                        isEnabled = screenState.isAmountFieldEnabled,
                        visualTransformation = AmountInputVisualTransformation(AmountFormatter()),
                    )
                }
            }

            BanklyInputField(
                textFieldValue = screenState.phoneNumberTFV,
                onTextFieldValueChange = { textFieldValue ->
                    onEnterPhoneNumber(textFieldValue)
                },
                placeholderText = stringResource(R.string.msg_enter_phone_number),
                labelText = stringResource(R.string.msg_phone_number_label),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                ),
                isError = screenState.isPhoneNumberError,
                feedbackText = screenState.phoneNumberFeedBack,
                isEnabled = screenState.isPhoneNumberFieldEnabled,
            )

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
            ) {
                Text(
                    text = stringResource(R.string.title_save_as_beneficiary),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(16.dp))
                BanklySwitchButton(
                    checked = screenState.saveAsBeneficiary,
                    isEnabled = screenState.isSaveAsBeneficiarySwitchEnabled,
                    onCheckedChange = onToggleSaveAsBeneficiary
                )
            }
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
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun NewBeneficiaryViewPreview() {
    BanklyTheme {
        NewBeneficiaryView(
            screenState = BeneficiaryScreenState(),
            onProviderDropDownIconClick = {},
            onPlanDropDownIconClick = {},
            onEnterPhoneNumber = {},
            onEnterMeterNumber = {},
            onEnterAmount = {},
            billType = BillType.AIRTIME,
            onContinueClick = {},
            onEnterIDorCardNumber = {},
            onToggleSaveAsBeneficiary = {}
        )
    }
}
