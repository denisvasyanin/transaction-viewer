package ru.disav.transactionviewer.feature.presentation.model

import ru.disav.transactionviewer.feature.domain.entity.TransactionModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.format.DateTimeFormatter

val createdFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("DD MMM HH:mm:ss")
val currencyFormatter: NumberFormat = DecimalFormat("###,###,##0.00")

fun TransactionModel.toUiModel(): TransactionUiModel.TransactionItem {
    return TransactionUiModel.TransactionItem(
        id = this.id.toString(),
        type = this.type,
        status = this.status,
        amount = currencyFormatter.format(this.amount),
        created = createdFormatter.format(this.created),
    )
}
