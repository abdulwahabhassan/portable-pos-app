package com.bankly.core.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BankLogo(
    val id: Long,
    @SerialName("InstitutionCode")
    val institutionCode: String,
    @SerialName("InstitutionName")
    val institutionName: String,
    val logo: String
)