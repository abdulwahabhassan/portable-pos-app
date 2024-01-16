package com.bankly.core.model.entity

data class Bank(
    val name: String,
    val id: Long,
    val categoryId: Int = -1,
    val categoryName: String = "",
)
