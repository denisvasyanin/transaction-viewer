package ru.disav.transactionviewer.feature.presentation.model

import ru.disav.transactionviewer.feature.domain.entity.TransactionStatus

sealed class TransactionUiModel {
    data class TransactionItem(
        val id: String,
        val type: String,
        val status: TransactionStatus,
        val amount: String,
        val created: String
    ) : TransactionUiModel()

    data class TimeItem(
        val time: String
    ) : TransactionUiModel()
}