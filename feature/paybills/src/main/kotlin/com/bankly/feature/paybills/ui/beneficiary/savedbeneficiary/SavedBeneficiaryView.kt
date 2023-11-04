package com.bankly.feature.paybills.ui.beneficiary.savedbeneficiary

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
import androidx.compose.foundation.layout.width
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
import com.bankly.core.common.ui.view.EmptyStateView
import com.bankly.core.common.util.AmountFormatter
import com.bankly.core.common.util.AmountInputVisualTransformation
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.component.BanklySwitchButton
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.feature.paybills.R
import com.bankly.feature.paybills.model.BillType
import com.bankly.feature.paybills.model.SavedBeneficiary
import com.bankly.feature.paybills.ui.beneficiary.BeneficiaryScreenState
import com.bankly.core.designsystem.R as DesignRes

@Composable
internal fun SavedBeneficiaryView(
    screenState: BeneficiaryScreenState,
    savedBeneficiaries: List<SavedBeneficiary>,
    onProviderDropDownIconClick: () -> Unit,
    onPlanDropDownIconClick: (BillType) -> Unit,
    onEnterPhoneNumber: (TextFieldValue) -> Unit,
    onEnterMeterNumber: (TextFieldValue) -> Unit,
    onEnterIDorCardNumber: (TextFieldValue) -> Unit,
    onEnterAmount: (TextFieldValue) -> Unit,
    billType: BillType,
    onContinueClick: () -> Unit,
    onChangeSelectedSavedBeneficiary: () -> Unit,
    onBeneficiarySelected: (SavedBeneficiary) -> Unit,
    onToggleSaveAsBeneficiary: (Boolean) -> Unit
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
            EmptyStateView()
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
                    BanklyInputField(
                        textFieldValue = screenState.providerTFV,
                        onTextFieldValueChange = { },
                        trailingIcon = BanklyIcons.ChevronDown,
                        readOnly = true,
                        onTrailingIconClick = onProviderDropDownIconClick,
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
                    )

                    Spacer(modifier = Modifier.padding(top = 8.dp))

                    when (billType) {
                        BillType.INTERNET_DATA -> {
                            BanklyInputField(
                                textFieldValue = screenState.planTFV,
                                onTextFieldValueChange = { },
                                trailingIcon = BanklyIcons.ChevronDown,
                                readOnly = true,
                                onTrailingIconClick = {
                                    onPlanDropDownIconClick(billType)
                                },
                                placeholderText = stringResource(R.string.msg_select_data_plan),
                                labelText = stringResource(R.string.title_data_plan),
                                isError = screenState.isPlanError,
                                feedbackText = screenState.planFeedBack,
                                isEnabled = screenState.isPlanFieldEnabled,
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
                                onTrailingIconClick = {
                                    onPlanDropDownIconClick(billType)
                                },
                                placeholderText = stringResource(R.string.msg_select_cable_plan),
                                labelText = stringResource(R.string.title_cable_plan),
                                isError = screenState.isPlanError,
                                feedbackText = screenState.planFeedBack,
                                isEnabled = screenState.isPlanFieldEnabled,
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
                                placeholderText = stringResource(R.string.msg_enter_id_or_card_number),
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
                                onTrailingIconClick = {
                                    onPlanDropDownIconClick(billType)
                                },
                                placeholderText = stringResource(R.string.msg_select_electricity_package),
                                labelText = stringResource(R.string.title_electricity_package),
                                isError = screenState.isPlanError,
                                feedbackText = screenState.planFeedBack,
                                isEnabled = screenState.isPlanFieldEnabled,
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
                            onCheckedChange = onToggleSaveAsBeneficiary,
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
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.grey)
private fun SavedBeneficiaryPreview() {
    BanklyTheme {
        SavedBeneficiaryView(
            screenState = BeneficiaryScreenState(shouldShowSavedBeneficiaryList = false),
            savedBeneficiaries = SavedBeneficiary.mockOtherBanks(),
            onProviderDropDownIconClick = {},
            onPlanDropDownIconClick = {},
            onEnterAmount = {},
            billType = BillType.AIRTIME,
            onContinueClick = {},
            onChangeSelectedSavedBeneficiary = {},
            onBeneficiarySelected = {},
            onEnterMeterNumber = {},
            onEnterIDorCardNumber = {},
            onEnterPhoneNumber = {},
            onToggleSaveAsBeneficiary = {}
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
            painter = painterResource(id = savedBeneficiary.providerLogo ?: BanklyIcons.Bank),
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
                    text = savedBeneficiary.providerName,
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
                    text = savedBeneficiary.uniqueId,
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
