package ru.disav.transactionviewer.feature.domain.usecase

import ru.disav.transactionviewer.feature.data.TransactionRepository
import javax.inject.Inject

class ResetDataUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke() = repository.resetData()
}