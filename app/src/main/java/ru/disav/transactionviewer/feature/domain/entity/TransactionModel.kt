package ru.disav.transactionviewer.feature.domain.entity

import java.time.LocalDateTime

data class TransactionModel(
    val id: Int,
    val type: String,
    val status: TransactionStatus,
    val amount: Int,
    val created: LocalDateTime
)
