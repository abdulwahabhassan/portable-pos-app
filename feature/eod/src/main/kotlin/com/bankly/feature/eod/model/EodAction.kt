package com.bankly.feature.eod.model

import com.bankly.core.designsystem.icon.BanklyIcons

enum class EodAction(val title: String, val icon: Int) {
    SYNC_EOD("Sync EOD", BanklyIcons.SyncEOD),
    VIEW_EOD_TRANSACTIONS("View EOD Transactions", BanklyIcons.Chat),
    EXPORT_FULL_EOD("Export Full EOD", BanklyIcons.BoxSadFace)
}