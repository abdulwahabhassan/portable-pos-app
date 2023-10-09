package com.bankly.core.network.retrofit.model

import com.bankly.core.network.BuildConfig.BANKLY_AGENT_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_AGENT_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_BILLS_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_BILLS_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_FUND_TRANSFER_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_FUND_TRANSFER_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_IDENTITY_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_IDENTITY_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_NOTIFICATION_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_NOTIFICATION_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_POS_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_POS_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_TMS_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_TMS_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_TRANSFER_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_TRANSFER_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_WALLET_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_WALLET_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BUILD_TYPE

private const val DEBUG = "debug"
enum class BanklyBaseUrl(val value: String) {
    Identity(if (BUILD_TYPE == DEBUG) BANKLY_IDENTITY_TEST_BASE_URL else BANKLY_IDENTITY_PROD_BASE_URL),
    Pos(if (BUILD_TYPE == DEBUG) BANKLY_POS_TEST_BASE_URL else BANKLY_POS_PROD_BASE_URL),
    Notification(if (BUILD_TYPE == DEBUG) BANKLY_NOTIFICATION_TEST_BASE_URL else BANKLY_NOTIFICATION_PROD_BASE_URL),
    Tms(if (BUILD_TYPE == DEBUG) BANKLY_TMS_TEST_BASE_URL else BANKLY_TMS_PROD_BASE_URL),
    Wallet(if (BUILD_TYPE == DEBUG) BANKLY_WALLET_TEST_BASE_URL else BANKLY_WALLET_PROD_BASE_URL),
    FundTransfer(if (BUILD_TYPE == DEBUG) BANKLY_FUND_TRANSFER_TEST_BASE_URL else BANKLY_FUND_TRANSFER_PROD_BASE_URL),
    Transfer(if (BUILD_TYPE == DEBUG) BANKLY_TRANSFER_TEST_BASE_URL else BANKLY_TRANSFER_PROD_BASE_URL),
    Agent(if (BUILD_TYPE == DEBUG) BANKLY_AGENT_TEST_BASE_URL else BANKLY_AGENT_PROD_BASE_URL),
    Bills(if (BUILD_TYPE == DEBUG) BANKLY_BILLS_TEST_BASE_URL else BANKLY_BILLS_PROD_BASE_URL),
}
