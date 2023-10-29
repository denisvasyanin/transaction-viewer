package ru.disav.transactionviewer.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.disav.transactionviewer.room.entity.TransactionEntity

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactionentity ORDER BY created ASC")
    fun load(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transactionEntity: TransactionEntity)

    @Query("DELETE FROM transactionentity")
    suspend fun clear()

    @Query("SELECT COUNT(*) FROM transactionentity")
    suspend fun count(): Int
}