package com.bankly.feature.dashboard.model

import com.bankly.core.designsystem.icon.BanklyIcons

enum class SupportOption(val title: String, val icon: Int) {
    FAQ("FAQ", BanklyIcons.Document),
    CONTACT_US("Contact Us", BanklyIcons.Chat),
    LOG_COMPLAINT("Log Complaint", BanklyIcons.BoxSadFace),
}
