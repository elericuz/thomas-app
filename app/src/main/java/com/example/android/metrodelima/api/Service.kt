package com.example.android.metrodelima.api

import com.example.android.metrodelima.Constants.BASE_URL
import com.example.android.metrodelima.Constants.LOGGER_LEVEL
import com.example.android.metrodelima.Constants.LOGGER_TAG
import com.example.android.metrodelima.models.TransactionModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber
import java.util.concurrent.TimeUnit

interface TransactionService {
    @Json
    @GET("transactions/get")
    fun getTransactions(
        @Query("external_number") externalNumber: String,
        @Query("page") page: Int
    ): Deferred<TransactionModel>
}

object ApiService {
    val logger = HttpLoggingInterceptor {
        Timber.tag(LOGGER_TAG).d(it)
    }

    val client = OkHttpClient
        .Builder()
        .readTimeout(12, TimeUnit.SECONDS)
        .connectTimeout(12, TimeUnit.SECONDS)
        .addInterceptor(logger.setLevel(LOGGER_LEVEL))
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ConverterScalarOrJson.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    val retrofitService: TransactionService by lazy {
        retrofit.create(TransactionService::class.java)
    }
}