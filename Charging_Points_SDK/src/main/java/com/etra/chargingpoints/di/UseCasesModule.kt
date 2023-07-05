package com.etra.chargingpoints.di

import com.etra.chargingpoints.common.di.ModuleProvider
import com.etra.chargingpoints.domain.usecases.auth.LoginUseCase
import com.etra.chargingpoints.domain.usecases.chargePoints.GetChargingPointsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

object UseCasesModule : ModuleProvider {

    override fun getModule(isMocked: Boolean): Module {
        return module {

            /**
             *  Login use case
             */
            single {
                LoginUseCase(
                    chargingPointsRepository = get()
                )
            }

            /**
             * Get Charging Points use case
             */
            single {
                GetChargingPointsUseCase(
                    chargingPointsRepository = get()
                )
            }
        }
    }
}