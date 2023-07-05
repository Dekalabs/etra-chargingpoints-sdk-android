package com.etra.chargingpoints.di

import com.etra.chargingpoints.common.Constants
import com.etra.chargingpoints.common.di.ModuleProvider
import com.etra.chargingpoints.data.ChargingPointsNetworkProvider
import com.etra.chargingpoints.data.ChargingPointsService
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit

object NetworkModule : ModuleProvider {

    override fun getModule(isMocked: Boolean): Module {
        return module {
            /**
             * Charging Points OkHttp client
             */
            single {
                ChargingPointsNetworkProvider.getOkHttpClient()
            }

            /**
             * Charging Points Retrofit
             */
            single { (baseUrl: String) ->
                ChargingPointsNetworkProvider.getRetrofit(
                    baseUrl = baseUrl,
                    json = get(),
                    okHttpClient = get()
                )
            }

            /**
             * Charging Points retrofit service
             */
            single {
                val retrofit: Retrofit = get() { parametersOf(Constants.API_URL) }
                retrofit.create(ChargingPointsService::class.java)
            }

        }
    }
}