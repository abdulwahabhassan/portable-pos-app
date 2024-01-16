package com.bankly.core.common.ui.view

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import com.bankly.core.common.R
import com.bankly.core.designsystem.component.BanklyClickableIcon
import com.bankly.core.designsystem.component.BanklyClickableText
import com.bankly.core.designsystem.component.BanklyDateInputField
import com.bankly.core.designsystem.component.BanklyFilledButton
import com.bankly.core.designsystem.component.BanklyFilterChip
import com.bankly.core.designsystem.component.BanklyInputField
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor
import com.bankly.core.model.entity.CashFlow
import com.bankly.core.model.entity.TransactionFilter
import com.bankly.core.model.entity.TransactionFilterType
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilterView(
    transactionReferenceTFV: TextFieldValue,
    accountNameTFV: TextFieldValue,
    onEnterAccountName: (TextFieldValue) -> Unit,
    accountNameFeedback: String,
    isAccountNameFeedbackError: Boolean,
    isUserInputEnabled: Boolean,
    onEnterTransactionReference: (TextFieldValue) -> Unit,
    isTransactionReferenceError: Boolean,
    transactionReferenceFeedback: String,
    onCashFlowFilterChipClick: (com.bankly.core.model.entity.CashFlow) -> Unit,
    onTransactionFilterTypeSelected: (TransactionFilterType) -> Unit,
    onShowMoreTypesClick: () -> Unit,
    onShowLessTypesClick: () -> Unit,
    shouldShowAllTransactionFilterType: Boolean,
    startDateFilter: LocalDate?,
    endDateFilter: LocalDate?,
    onStartDateFilterClick: (LocalDate?) -> Unit,
    isStartDateFilterError: Boolean,
    startDateFilterFeedBack: String,
    onEndDateFilterClick: (LocalDate?) -> Unit,
    isEndDateFilterError: Boolean,
    endDateFilterFeedBack: String,
    cashFlows: List<com.bankly.core.model.entity.CashFlow>,
    transactionFilterTypes: List<TransactionFilterType>,
    onApplyClick: (TransactionFilter) -> Unit,
    onCloseClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(start = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.title_filter),
            style = MaterialTheme.typography.titleMedium,
        )
        BanklyClickableIcon(icon = BanklyIcons.Close, onClick = onCloseClick, shape = CircleShape)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        flingBehavior = ScrollableDefaults.flingBehavior(),
    ) {
        item {
            BanklyInputField(
                textFieldValue = transactionReferenceTFV,
                onTextFieldValueChange = { textFieldValue ->
                    onEnterTransactionReference(textFieldValue)
                },
                isEnabled = isUserInputEnabled,
                placeholderText = stringResource(R.string.msg_enter_reference_number),
                labelText = stringResource(R.string.label_transaction_reference),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                ),
                isError = isTransactionReferenceError,
                feedbackText = transactionReferenceFeedback,
            )

            BanklyInputField(
                textFieldValue = accountNameTFV,
                onTextFieldValueChange = { textFieldValue ->
                    onEnterAccountName(textFieldValue)
                },
                isEnabled = isUserInputEnabled,
                placeholderText = stringResource(R.string.msg_enter_accout_name),
                labelText = stringResource(R.string.label_account_name),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                ),
                isError = isAccountNameFeedbackError,
                feedbackText = accountNameFeedback,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.title_cashflow),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(horizontal = 24.dp),
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(cashFlows, key = com.bankly.core.model.entity.CashFlow::title) { filter: com.bankly.core.model.entity.CashFlow ->
                    BanklyFilterChip(
                        title = filter.title,
                        isSelected = filter.isSelected,
                        onClick = { onCashFlowFilterChipClick(filter) },
                    )
                }
            }
            Spacer(modifier = Modifier.height(36.dp))
            Text(
                text = stringResource(R.string.title_type),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(horizontal = 24.dp),
            )
            LazyHorizontalStaggeredGrid(
                rows = StaggeredGridCells.Fixed(
                    if (shouldShowAllTransactionFilterType.not()) 3 else 7,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (shouldShowAllTransactionFilterType.not()) 120.dp else 280.dp)
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalItemSpacing = 4.dp,
            ) {
                items(
                    if (shouldShowAllTransactionFilterType.not()) {
                        transactionFilterTypes.take(6)
                    } else {
                        transactionFilterTypes
                    },
                    key = TransactionFilterType::id,
                ) { type: TransactionFilterType ->
                    BanklyFilterChip(
                        title = type.name,
                        isSelected = type.isSelected,
                        onClick = { onTransactionFilterTypeSelected(type) },
                    )
                }
            }
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                BanklyClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                            ).toSpanStyle(),
                        ) {
                            append(
                                stringResource(
                                    if (shouldShowAllTransactionFilterType) {
                                        R.string.action_show_less
                                    } else {
                                        R.string.action_show_more
                                    },
                                ),
                            )
                        }
                    },
                    onClick = if (shouldShowAllTransactionFilterType) {
                        onShowLessTypesClick
                    } else {
                        onShowMoreTypesClick
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(
                                id =
                                if (shouldShowAllTransactionFilterType) {
                                    BanklyIcons.ChevronUp
                                } else {
                                    BanklyIcons.ChevronDown
                                },
                            ),
                            contentDescription = null,
                            tint = Color.Unspecified,
                        )
                    },
                    backgroundShape = MaterialTheme.shapes.small,
                    indicationColor = Color.White,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    BanklyDateInputField(
                        date = startDateFilter,
                        title = stringResource(R.string.title_date_range),
                        onQueryChange = {},
                        placeHolder = stringResource(R.string.msg_date_place_holder),
                        onClick = {
                            onStartDateFilterClick(startDateFilter)
                        },
                        isError = isStartDateFilterError,
                        feedBackText = startDateFilterFeedBack,
                        isEnabled = isUserInputEnabled,
                        horizontalPadding = 0.dp,
                        textStyle = MaterialTheme.typography.bodySmall,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    BanklyDateInputField(
                        date = endDateFilter,
                        title = " ",
                        onQueryChange = {},
                        placeHolder = stringResource(R.string.msg_date_place_holder),
                        onClick = {
                            onEndDateFilterClick(endDateFilter)
                        },
                        isError = isEndDateFilterError,
                        feedBackText = endDateFilterFeedBack,
                        isEnabled = isUserInputEnabled,
                        horizontalPadding = 0.dp,
                        textStyle = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            BanklyFilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                text = stringResource(R.string.action_apply),
                onClick = {
                    onApplyClick(
                        TransactionFilter(
                            transactionReference = transactionReferenceTFV.text,
                            accountName = accountNameTFV.text,
                            cashFlows = cashFlows,
                            transactionTypes = transactionFilterTypes,
                            dateFrom = startDateFilter,
                            dateTo = endDateFilter,
                        ),
                    )
                },
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun FilterPreview() {
    BanklyTheme {
        FilterView(
            transactionReferenceTFV = TextFieldValue(),
            accountNameTFV = TextFieldValue(),
            onEnterAccountName = {},
            accountNameFeedback = "",
            isAccountNameFeedbackError = false,
            isUserInputEnabled = true,
            onEnterTransactionReference = {},
            isTransactionReferenceError = false,
            transactionReferenceFeedback = "",
            onCashFlowFilterChipClick = {},
            onShowMoreTypesClick = {},
            onShowLessTypesClick = {},
            shouldShowAllTransactionFilterType = true,
            startDateFilter = LocalDate(2022, 10, 1),
            endDateFilter = LocalDate(2022, 10, 1),
            onStartDateFilterClick = {},
            isStartDateFilterError = false,
            startDateFilterFeedBack = "",
            onEndDateFilterClick = {},
            isEndDateFilterError = false,
            endDateFilterFeedBack = "",
            cashFlows = listOf(com.bankly.core.model.entity.CashFlow.Debit(false), com.bankly.core.model.entity.CashFlow.Credit(false)),
            transactionFilterTypes = emptyList(),
            onTransactionFilterTypeSelected = {},
            onApplyClick = {},
            onCloseClick = {},
        )
    }
}
