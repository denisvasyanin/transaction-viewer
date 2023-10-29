package ru.disav.transactionviewer.feature.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.disav.transactionviewer.feature.data.mapper.toDomain
import ru.disav.transactionviewer.feature.data.mapper.toEntity
import ru.disav.transactionviewer.feature.domain.entity.TransactionModel
import ru.disav.transactionviewer.app.room.dao.TransactionDao
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val service: TransactionApi,
    private val dao: TransactionDao
) {
    suspend fun requestUpdate(): Flow<Unit> = flow {
        val transactions = service.getLatestTransactions()

        transactions.map {
            dao.insert(it.toEntity())
        }

        emit(Unit)
    }

    fun loadTransactions(): Flow<List<TransactionModel>> = dao
        .load()
        .map { transactions ->
            transactions.map {
                it.toDomain()
            }
        }


    fun resetData(): Flow<Unit> = flow {
        service.reset()
        dao.clear()

        emit(Unit)
    }

}