package com.etra.chargingpoints.di

import com.etra.chargingpoints.common.di.ModuleProvider
import com.etra.chargingpoints.domain.repositories.chargingPoints.ChargingPointsRepository
import com.etra.chargingpoints.domain.repositories.chargingPoints.ChargingPointsRepositoryImpl
import com.etra.chargingpoints.domain.repositories.chargingPoints.datasource.RemoteChargingPointsDataSource
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModule : ModuleProvider {

    override fun getModule(isMocked: Boolean): Module {
        return module {
            /**
             * Json
             */
            single {
                Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    coerceInputValues = true
                }
            }

            /**
             * Remote Charging Points data source
             */
            single {
                RemoteChargingPointsDataSource(
                    service = get()
                )
            }

            /**
             * Charging Points repository
             */
            single<ChargingPointsRepository> {
                ChargingPointsRepositoryImpl(
                    dataSource = get<RemoteChargingPointsDataSource>()
                )
            }

        }
    }
}

