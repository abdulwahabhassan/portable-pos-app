package com.bankly.core.common.ui.processtransaction

import com.bankly.core.common.model.TransactionData

sealed interface ProcessTransactionScreenEvent {
    data class  ProcessTransaction(val transactionData: TransactionData): ProcessTransactionScreenEvent
}