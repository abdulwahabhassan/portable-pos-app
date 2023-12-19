package com.bankly.core.enums

enum class TransactionType(val id: Long, val title: String) {
    PAYMENT(1, "Payment"),
    SAVINGS(2, "Savings"),
    BANKLYTRANSFER(3, "BanklyTransfer"),
    OTHER_BANK_TRANSFER(4, "OtherBankTransfer"),
    WITHDRAWAL(5, "Withdrawal"),
    OWNTRANSFER(6, "OwnTransfer"),
    BANKLY_CHARGES(7, "BanklyCharges"),
    PROVIDER_REMITTANCE(8, "ProviderRemittance"),
    AGENT_COMMISSION(9, "AgentCommission"),
    BILL_PAYMENT(10, "BillPayment"),
    BANKLY_WALLET_TOP_UP(11, "BanklyWalletTopUp"),
    VOUCHER_GENERATION(12, "VoucherGeneration"),
    TRANSACTION_REVERSAL(13, "TransactionReversal"),
    BANK_PAYMENT(14, "BankPayment"),
    VOUCHER_INVALIDATION(15, "VoucherInvalidation"),
    LOAN_APPROVAL_COMMISSION(16, "LoanApprovalCommission"),
    LOAN_REPAYMENT(17, "LoanRepayment"),
    LOAN_REPAYMENT_COMMISSION(18, "LoanRepaymentCommission"),
    AGENT_SAVINGS_COMMISSION(19, "AgentSavingsCommision"),
    AGGREGATOR_SAVINGS_WITHDRAWAL_COMMISSION(20, "AggregatorSavingsWithdrawalCommision"),
    AGGREGATOR_POS_WITHDRAWAL_COMMISSION(21, "AggregatorPosWithdrawalCommission"),
    TELLER_FUNDING(22, "TellerFunding"),
    TERMINAL_ACTIVATION_FEE(100, "TerminalActivationFee"),
    POS_OTHER_BANK_TRANSFER(101, "PosOtherBankTransfer"),
    POS_CASH_WITHDRAWAL(110, "PosCashWithdrawal"),
    AGGREAGTOR_POS_TRANSFER_COMMISSION(110, "AggregatorPosTransferCommission"),
    AGGREGATOR_TERMINAL_ACTIVATION_FEE_COMMISSION(170, "AggregatorTerminalActivationFeeCommission"),
}
