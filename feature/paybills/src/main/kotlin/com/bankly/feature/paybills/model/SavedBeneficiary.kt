package com.bankly.feature.paybills.model

import com.bankly.core.designsystem.R

data class SavedBeneficiary(
    val name: String,
    val providerName: String,
    val uniqueId: String,
    val providerLogo: Int? = null,
    val providerId: Long,
) {
    companion object {
        fun mockOtherBanks(): List<SavedBeneficiary> {
            return listOf(
                SavedBeneficiary(
                    "Hassan Abdulwahab",
                    "FIRST BANK",
                    "3090232555",
                    R.drawable.ic_first_bank,
                    10,
                ),
                SavedBeneficiary(
                    "Hassan Abdulwahab",
                    "GTB",
                    "0428094437",
                    R.drawable.ic_gt_bank,
                    11,
                ),
            )
        }

        fun mockBanklyBank(): List<SavedBeneficiary> {
            return listOf(
                SavedBeneficiary(
                    "Hassan Abdulwahab",
                    "BANKLY MFB",
                    "5003004092",
                    R.drawable.ic_bankly,
                    96,
                ),
                SavedBeneficiary(
                    "Hassan Abdulwahab",
                    "BANKLY MFB",
                    "5010181913",
                    R.drawable.ic_bankly,
                    96,
                ),
            )
        }
    }
}
