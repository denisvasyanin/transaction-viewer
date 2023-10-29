package ru.disav.transactionviewer.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    @PrimaryKey val id: Int,
    val type: String,
    val status: String,
    val amount: Int,
    val created: Long
)