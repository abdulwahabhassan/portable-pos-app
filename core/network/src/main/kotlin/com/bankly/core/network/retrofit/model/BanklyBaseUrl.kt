package com.bankly.core.network.retrofit.model

import com.bankly.core.network.BuildConfig.BANKLY_NOTIFICATION_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_NOTIFICATION_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_POS_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_POS_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_TMS_PROD_BASE_URL
import com.bankly.core.network.BuildConfig.BANKLY_TMS_TEST_BASE_URL
import com.bankly.core.network.BuildConfig.DEBUG

enum class BanklyBaseUrl(val value: String) {
    Base(if (DEBUG) BANKLY_PROD_BASE_URL else BANKLY_TEST_BASE_URL),
    Pos(if (DEBUG) BANKLY_POS_PROD_BASE_URL else BANKLY_POS_TEST_BASE_URL),
    Notification(if (DEBUG) BANKLY_NOTIFICATION_PROD_BASE_URL else BANKLY_NOTIFICATION_TEST_BASE_URL),
    Tms(if (DEBUG) BANKLY_TMS_PROD_BASE_URL else BANKLY_TMS_TEST_BASE_URL)
}