package com.etra.chargingpoints.data

import com.etra.chargingpoints.EtraChargingPointsSDK
import com.etra.chargingpoints.common.Constants
import com.etra.chargingpoints.common.Constants.HEADER_ACCEPT_LANGUAGE
import com.etra.chargingpoints.common.Constants.HEADER_AUTHORIZATION
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit

object ChargingPointsNetworkProvider : KoinComponent {
    val etraSDK = EtraChargingPointsSDK.getInstance()

    fun getOkHttpClient(): OkHttpClient {
        with(OkHttpClient.Builder()) {
            connectTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(getLoggingInterceptor())
            return build()
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getRetrofit(baseUrl: String, okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = Constants.CONTENT_TYPE.toMediaType()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    private fun getLoggingInterceptor(): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .build()
    }
}
