package ru.disav.transactionviewer.feature.data.entity

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class TransactionApiModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("type")
    val type: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("amount")
    val amount: Int,

    @SerializedName("created")
    val created: Long
)

