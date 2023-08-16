package com.bankly.feature.sendmoney.model

import com.bankly.core.designsystem.R

data class SavedBeneficiary(
    val name: String,
    val bankName: String,
    val accountNumber: String,
    val bankLogo: Int? = null,
    val bankId: Long
) {
    companion object {
        fun mockOtherBanks(): List<SavedBeneficiary> {
            return listOf(
                SavedBeneficiary(
                    "Tomi Adejana",
                    "Access Bank PLC",
                    "8299803022",
                    R.drawable.ic_access_bank,
                    1
                ),
                SavedBeneficiary(
                    "Hassan Abdulwahab",
                    "First Bank PLC",
                    "9844803022",
                    null,
                    2
                ),
                SavedBeneficiary(
                    "Josh Osazuwa",
                    "GTB",
                    "1020803022",
                    R.drawable.ic_gt_bank,
                    11
                ),
                SavedBeneficiary(
                    "Michael Alloy",
                    "Access Bank PLC",
                    "8299803022",
                    R.drawable.ic_access_bank,
                    4
                ),
                SavedBeneficiary(
                    "Cristabel Alongo Matthew",
                    "First Bank PLC",
                    "9844803022",
                    R.drawable.ic_first_bank,
                    12
                ),
                SavedBeneficiary(
                    "Peter Edokhume Paul Francis Jnr.",
                    "GTB",
                    "1020803022",
                    R.drawable.ic_gt_bank,
                    11
                ),
            )
        }

        fun mockBanklyBank(): List<SavedBeneficiary> {
            return listOf(

                SavedBeneficiary(
                    "Michael Alloy",
                    "Ampersand (Bankly MFB)",
                    "8299803022",
                    R.drawable.ic_bankly,
                    96
                ),
                SavedBeneficiary(
                    "Cristabel Alongo Matthew",
                    "Ampersand (Bankly MFB)",
                    "9844803022",
                    R.drawable.ic_bankly,
                    96
                ),
                SavedBeneficiary(
                    "Peter Edokhume Paul Francis Jnr.",
                    "Ampersand (Bankly MFB)",
                    "1020803022",
                    R.drawable.ic_bankly,
                    96
                ),
                SavedBeneficiary(
                    "Tomi Adejana",
                    "Ampersand (Bankly MFB)",
                    "8299803022",
                    R.drawable.ic_bankly,
                    96
                ),
                SavedBeneficiary(
                    "Hassan Abdulwahab",
                    "Ampersand (Bankly MFB)",
                    "9844803022",
                    R.drawable.ic_bankly,
                    96
                ),
                SavedBeneficiary(
                    "Josh Osazuwa",
                    "Ampersand (Bankly MFB)",
                    "1020803022",
                    R.drawable.ic_bankly,
                    96
                ),
            )
        }

    }
}

