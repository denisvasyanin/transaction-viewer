package ru.disav.transactionviewer.app.di.data

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.disav.transactionviewer.app.room.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "transaction_app.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTransactionDao(db: AppDatabase) = db.transactionDao

}