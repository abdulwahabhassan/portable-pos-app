package com.bankly.core.network.retrofit.model

import com.bankly.core.network.BuildConfig.BANKLY_IDENTITY_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_IDENTITY_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_NOTIFICATION_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_NOTIFICATION_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_POS_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_POS_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_TMS_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_TMS_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_WALLET_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_WALLET_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BUILD_TYPE

enum class BanklyBaseUrl(val value: String) {
    Identity(if (BUILD_TYPE == "debug") BANKLY_IDENTITY_TEST_BASE_URL else BANKLY_IDENTITY_PROD_BASE_URL),
    Pos(if (BUILD_TYPE == "debug") BANKLY_POS_TEST_BASE_URL else BANKLY_POS_PROD_BASE_URL),
    Notification(if (BUILD_TYPE == "debug") BANKLY_NOTIFICATION_TEST_BASE_URL else BANKLY_NOTIFICATION_PROD_BASE_URL),
    Tms(if (BUILD_TYPE == "debug") BANKLY_TMS_TEST_BASE_URL else BANKLY_TMS_PROD_BASE_URL),
    Wallet(if (BUILD_TYPE == "debug") BANKLY_WALLET_TEST_BASE_URL else BANKLY_WALLET_PROD_BASE_URL),
}