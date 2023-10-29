package ru.disav.transactionviewer.feature.data

import retrofit2.http.*
import ru.disav.transactionviewer.feature.data.entity.TransactionApiModel

interface TransactionApi {

    @GET("/dummy/transaction")
    suspend fun getLatestTransactions(): List<TransactionApiModel>

    @GET("/dummy/transaction?transaction?clear=true")
    suspend fun reset()
}