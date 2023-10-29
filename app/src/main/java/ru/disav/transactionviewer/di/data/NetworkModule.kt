package ru.disav.transactionviewer.di.data

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.disav.transactionviewer.R
import ru.disav.transactionviewer.feature.data.TransactionApi
import ru.disav.transactionviewer.di.annotations.ServerUrl
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        @ServerUrl baseUrl: String,
    ) = Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create(Gson())
        )
        .baseUrl(baseUrl)
        .client(
            OkHttpClient
                .Builder()
                .build()
        )
        .build()


    @Provides
    @Singleton
    fun provideTransactionApi(retrofit: Retrofit) = retrofit.create(TransactionApi::class.java)

    @ServerUrl
    @Provides
    @Singleton
    fun provideServerUrl(@ApplicationContext appContext: Context) =
        appContext.getString(R.string.server_url)

}