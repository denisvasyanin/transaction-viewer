package ru.disav.transactionviewer.feature.domain.usecase

import ru.disav.transactionviewer.feature.data.TransactionRepository
import javax.inject.Inject

class LoadTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke() = repository.loadTransactions()
}