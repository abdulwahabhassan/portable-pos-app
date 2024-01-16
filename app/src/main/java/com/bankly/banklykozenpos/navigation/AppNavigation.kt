package com.bankly.banklykozenpos.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.bankly.banklykozenpos.R
import com.bankly.banklykozenpos.ui.BanklyAppState
import com.bankly.core.common.ui.notification.NotificationMessageView
import com.bankly.core.common.ui.notification.TransactionAlertView
import com.bankly.core.designsystem.component.BanklyCenterDialog
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.model.entity.Feature
import com.bankly.core.model.entity.NotificationMessage
import com.bankly.feature.authentication.navigation.authenticationNavGraph
import com.bankly.feature.authentication.navigation.authenticationNavGraphRoute
import com.bankly.feature.authentication.navigation.isValidatePassCodeArg
import com.bankly.feature.cardtransfer.navigation.cardTransferNavGraph
import com.bankly.feature.checkcardbalance.navigation.checkCardBalanceNavGraph
import com.bankly.feature.contactus.navigation.contactUsNavGraph
import com.bankly.feature.dashboard.model.SupportOption
import com.bankly.feature.dashboard.navigation.dashBoardNavGraph
import com.bankly.feature.eod.navigation.eodNavGraph
import com.bankly.feature.faq.navigation.faqNavGraph
import com.bankly.feature.logcomplaints.navigation.logComplaintNavGraph
import com.bankly.feature.networkchecker.navigation.networkCheckerNavGraph
import com.bankly.feature.notification.model.TransactionPayload
import com.bankly.feature.paybills.navigation.billPaymentNavGraph
import com.bankly.feature.paywithcard.navigation.payWithCardNavGraph
import com.bankly.feature.paywithtransfer.navigation.payWithTransferNavGraph
import com.bankly.feature.sendmoney.navigation.sendMoneyNavGraph
import com.bankly.feature.settings.navigation.settingsNavGraph
import com.bankly.feature.transactiondetails.navigation.transactionDetailsNavGraph

@Composable
internal fun AppNavHost(
    appState: BanklyAppState,
    modifier: Modifier = Modifier,
    startDestination: String = "$authenticationNavGraphRoute/{$isValidatePassCodeArg}",
    onExitApp: () -> Unit,
    activity: Activity,
    isSessionExpired: Boolean,
    onSessionExpired: () -> Unit,
    onSessionRenewed: () -> Unit,
    onClosNotificationMessageDialog: (NotificationMessage) -> Unit,
    onCloseTransactionAlertDialog: (TransactionPayload) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = appState.navHostController,
        startDestination = startDestination,
    ) {
        authenticationNavGraph(
            appNavController = appState.navHostController,
            onLoginSuccess = {
                onSessionRenewed()
                appState.navigateTo(AppTopLevelDestination.DASHBOARD)
            },
            onBackPress = onExitApp,
            onGoToSettingsRoute = {
                appState.navHostController.navigateToSettingsNavGraph(
                    navOptions = navOptions {
                        popUpTo("$authenticationNavGraphRoute/{$isValidatePassCodeArg}") {
                            inclusive = true
                        }
                    },
                )
            },
            onPopBackStack = {
                appState.navHostController.popBackStack()
            },
            onContactSupportPress = {
                appState.navHostController.navigateToContactUsNavGraph()
            },
        )
        dashBoardNavGraph(
            onExitApp = onExitApp,
            onFeatureClick = { feature: Feature ->
                appState.navigateTo(
                    when (feature) {
                        is Feature.PayWithCard -> AppTopLevelDestination.PAY_WITH_CARD
                        is Feature.PayWithTransfer -> AppTopLevelDestination.PAY_WITH_TRANSFER
                        is Feature.CardTransfer -> AppTopLevelDestination.CARD_TRANSFER
                        is Feature.SendMoney -> AppTopLevelDestination.SEND_MONEY
                        is Feature.PayBills -> AppTopLevelDestination.PAY_BILLS
                        is Feature.CheckBalance -> AppTopLevelDestination.CHECK_BALANCE
                        is Feature.PayWithUssd -> AppTopLevelDestination.PAY_WITH_USSD
                        is Feature.Float -> AppTopLevelDestination.FLOAT
                        is Feature.EndOfDay -> AppTopLevelDestination.EOD
                        is Feature.NetworkChecker -> AppTopLevelDestination.NETWORK_CHECKER
                        is Feature.Settings -> AppTopLevelDestination.SETTINGS
                    },
                )
            },
            onContinueToPayWithCardClick = { amount: Double ->
                appState.navHostController.navigateToPayWithCardNavGraph(
                    amount = amount,
                    navOptions = navOptions {
                        launchSingleTop = true
                        restoreState = true
                    },
                )
            },
            onGoToTransactionDetailsScreen = { transactionReceipt ->
                appState.navHostController.navigateToTransactionDetailsNavGraph(transactionReceipt)
            },
            onSupportOptionClick = { supportOption ->
                when (supportOption) {
                    SupportOption.FAQ -> {
                        appState.navHostController.navigateToFaqNavGraph()
                    }

                    SupportOption.CONTACT_US -> {
                        appState.navHostController.navigateToContactUsNavGraph()
                    }

                    SupportOption.LOG_COMPLAINT -> {
                        appState.navHostController.navigateToLogComplaintNavGraph()
                    }
                }
            },
            onLogOutClick = {
                appState.navHostController.logOut()
            },
            activity = activity,
            onSessionExpired = onSessionExpired,
        )
        payWithCardNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            appNavController = appState.navHostController,
            onViewTransactionDetailsClick = { transactionReceipt ->
                appState.navHostController.navigateToTransactionDetailsNavGraph(transactionReceipt)
            },
        )
        cardTransferNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            onSessionExpired = onSessionExpired,
            onViewTransactionDetailsClick = { transactionReceipt ->
                appState.navHostController.navigateToTransactionDetailsNavGraph(transactionReceipt)
            },
        )
        payWithTransferNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            onSessionExpired = onSessionExpired,
            onViewTransactionDetailsClick = { transactionReceipt ->
                appState.navHostController.navigateToTransactionDetailsNavGraph(transactionReceipt)
            },
        )
        sendMoneyNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            onForgotPinClick = {},
            onSessionExpired = onSessionExpired,
            onViewTransactionDetailsClick = { transactionReceipt ->
                appState.navHostController.navigateToTransactionDetailsNavGraph(transactionReceipt)
            },
        )
        networkCheckerNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            onSessionExpired = onSessionExpired,
        )
        billPaymentNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            onForgotPinClick = {},
            onSessionExpired = onSessionExpired,
            onViewTransactionDetailsClick = { transactionReceipt ->
                appState.navHostController.navigateToTransactionDetailsNavGraph(transactionReceipt)
            },
        )
        transactionDetailsNavGraph(
            appNavController = appState.navHostController,
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            onLogComplaintClick = {
                appState.navHostController.navigateToLogComplaintNavGraph()
            }
        )
        eodNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
            onSessionExpired = onSessionExpired,
            onViewTransactionDetailsClick = { transactionReceipt ->
                appState.navHostController.navigateToTransactionDetailsNavGraph(transactionReceipt)
            },
        )
        faqNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
        )
        contactUsNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
        )

        logComplaintNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
        )
        settingsNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
        )
        checkCardBalanceNavGraph(
            onBackPress = {
                appState.navHostController.popBackStack()
            },
        )

    }

    BanklyCenterDialog(
        title = stringResource(R.string.title_session_expired),
        subtitle = stringResource(R.string.msg_session_expired_log_in_to_continue),
        icon = BanklyIcons.ErrorAlert,
        showDialog = isSessionExpired,
        positiveActionText = stringResource(R.string.action_okay),
        positiveAction = {
            appState.navHostController.logOut()
            onSessionRenewed()
        },
        onDismissDialog = {
            appState.navHostController.logOut()
            onSessionRenewed()
        },
    )

    BanklyCenterDialog(
        title = appState.mainActivityState.transactionAlert?.title
            ?: stringResource(R.string.title_transaction_alert),
        icon = BanklyIcons.Successful,
        showDialog = appState.mainActivityState.showTransactionAlertDialog,
        onDismissDialog = {
            appState.mainActivityState.transactionAlert?.let { onCloseTransactionAlertDialog(it) }
        },
        extraContent = {
            TransactionAlertView(
                amount = appState.mainActivityState.transactionAlert?.amount ?: 0.00,
                senderAccountName = appState.mainActivityState.transactionAlert?.senderAccountName
                    ?: "",
                onCloseClick = {
                    appState.mainActivityState.transactionAlert?.let { transactionPayload: TransactionPayload ->
                        onCloseTransactionAlertDialog(
                            transactionPayload
                        )
                    }
                },
                onViewTransactionDetailsClick = {
                    appState.mainActivityState.transactionAlert?.let { transactionPayload: TransactionPayload ->
                        onCloseTransactionAlertDialog(transactionPayload)
                        appState.navHostController.navigateToTransactionDetailsNavGraph(
                            transactionPayload.toTransactionReceipt()
                        )
                    }
                }
            )
        },
    )

    BanklyCenterDialog(
        title = appState.mainActivityState.notificationMessage?.title
            ?: stringResource(R.string.title_notification),
        subtitle = appState.mainActivityState.notificationMessage?.message,
        showDialog = appState.mainActivityState.showNotificationMessageDialog,
        onDismissDialog = {
            appState.mainActivityState.notificationMessage?.let { onClosNotificationMessageDialog(it) }
        },
        extraContent = {
            NotificationMessageView(
                message = appState.mainActivityState.notificationMessage?.message ?: "",
            )
        },
        showCloseIcon = true
    )
}
