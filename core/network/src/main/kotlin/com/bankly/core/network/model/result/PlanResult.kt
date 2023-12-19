package com.bankly.core.network.model.result

import kotlinx.serialization.Serializable

@Serializable
data class PlanResult(
    val id: Long?,
    val billId: Long?,
    val code: String?,
    val name: String?,
    val description: String?,
    val amount: Double?,
    val active: Boolean?,
    val hasFixedAmount: Boolean?,
    val minimumAmount: Double?,
    val isTokenType: Boolean?,
    val available: Long?,
)
