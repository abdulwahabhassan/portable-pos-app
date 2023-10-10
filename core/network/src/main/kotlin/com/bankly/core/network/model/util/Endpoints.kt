package com.bankly.core.network.model.util

object EndPoints {

    //Notification
    const val ADD_DEVICE_TO_FIREBASE = "notification/user-device/add-device-to-firebase"

    //TMS
    const val FORCE_CONFIG = "terminal/force-config"

    //Pay with transfer
    const val RECENT_FUNDING = "wallet-funding/api/paywithtransfer/recent-fundings"
    const val UPDATE_AS_SYNCED = "wallet-funding/api/paywithtransfer/update-as-synced"
    const val AGENT_ACCOUNT = "wallet/get/account/agent/default"
    const val SEND_TRANSFER_RECEIPT = "wallet-funding/api/PayWithTransfer/send-receipt"

    //Pay with cash
    const val LOG_CASH = "Orders/create-CashDeposit"
    const val SEND_CASH_RECEIPT = "wallet-funding/api/PayWithTransfer/cashreceiptsend"

    //Network checker
    const val NETWORK_CHECKER = "Transaction/network-checker"

    //Dispute
    const val LOG_DISPUTE = "Orders/createdisputelog"
    const val DISPUTE_LOG_LIST = "Orders/disputeloglist/{page}/{pagesize}/{}"
}