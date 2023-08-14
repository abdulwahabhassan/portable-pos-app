package com.bankly.feature.sendmoney.model

import com.bankly.core.designsystem.R

data class SavedBeneficiary(
    val name: String,
    val bankName: String,
    val accountNumber: String,
    val bankLogo: Int? = null,
    val bankId: Long? = null
) {
    companion object {
        fun mockOtherBanks(): List<SavedBeneficiary> {
            return listOf(
                SavedBeneficiary(
                    "Tomi Adejana",
                    "Access Bank PLC",
                    "8299803022",
                    R.drawable.ic_access_bank
                ),
                SavedBeneficiary(
                    "Hassan Abdulwahab",
                    "First Bank PLC",
                    "9844803022",
                    null
                ),
                SavedBeneficiary(
                    "Josh Osazuwa",
                    "GTB",
                    "1020803022",
                    R.drawable.ic_gt_bank
                ),
                SavedBeneficiary(
                    "Michael Alloy",
                    "Access Bank PLC",
                    "8299803022",
                    R.drawable.ic_access_bank
                ),
                SavedBeneficiary(
                    "Cristabel Alongo Matthew",
                    "First Bank PLC",
                    "9844803022",
                    R.drawable.ic_first_bank
                ),
                SavedBeneficiary(
                    "Peter Edokhume Paul Francis Jnr.",
                    "GTB",
                    "1020803022",
                    R.drawable.ic_gt_bank
                ),
            )
        }

        fun mockBanklyBank(): List<SavedBeneficiary> {
            return listOf(

                SavedBeneficiary(
                    "Michael Alloy",
                    "Ampersand (Bankly MFB)",
                    "8299803022",
                    R.drawable.ic_bankly
                ),
                SavedBeneficiary(
                    "Cristabel Alongo Matthew",
                    "Ampersand (Bankly MFB)",
                    "9844803022",
                    R.drawable.ic_bankly
                ),
                SavedBeneficiary(
                    "Peter Edokhume Paul Francis Jnr.",
                    "Ampersand (Bankly MFB)",
                    "1020803022",
                    R.drawable.ic_bankly
                ),
                SavedBeneficiary(
                    "Tomi Adejana",
                    "Ampersand (Bankly MFB)",
                    "8299803022",
                    R.drawable.ic_bankly
                ),
                SavedBeneficiary(
                    "Hassan Abdulwahab",
                    "Ampersand (Bankly MFB)",
                    "9844803022",
                    R.drawable.ic_bankly
                ),
                SavedBeneficiary(
                    "Josh Osazuwa",
                    "Ampersand (Bankly MFB)",
                    "1020803022",
                    R.drawable.ic_bankly
                ),
            )
        }

    }
}

