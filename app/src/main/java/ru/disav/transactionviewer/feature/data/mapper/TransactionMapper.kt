package ru.disav.transactionviewer.feature.data.mapper

import ru.disav.transactionviewer.feature.data.entity.TransactionApiModel
import ru.disav.transactionviewer.feature.domain.entity.TransactionModel
import ru.disav.transactionviewer.feature.domain.entity.TransactionStatus
import ru.disav.transactionviewer.room.entity.TransactionEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun TransactionEntity.toDomain(): TransactionModel {
    return TransactionModel(
        id = this.id,
        type = this.type,
        status = when (this.status) {
            "SUCCESS" -> TransactionStatus.SUCCESS
            "FAILURE" -> TransactionStatus.FAILURE
            else -> TransactionStatus.IN_PROGRESS
        },
        amount = this.amount,
        created = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this.created),
            ZoneId.systemDefault()
        )
    )
}

fun TransactionApiModel.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = this.id,
        type = this.type,
        status = this.status,
        amount = this.amount,
        created = this.created
    )
}
