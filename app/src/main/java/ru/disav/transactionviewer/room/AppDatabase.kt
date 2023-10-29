package ru.disav.transactionviewer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.disav.transactionviewer.room.dao.TransactionDao
import ru.disav.transactionviewer.room.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
}