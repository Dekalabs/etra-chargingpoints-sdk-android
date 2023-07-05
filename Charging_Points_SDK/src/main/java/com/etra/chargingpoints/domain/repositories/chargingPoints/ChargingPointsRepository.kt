package com.etra.chargingpoints.domain.repositories.chargingPoints

import arrow.core.Either
import com.etra.chargingpoints.common.error.CustomError
import com.etra.chargingpoints.domain.model.AccessToken
import com.etra.chargingpoints.domain.model.ChargingPoint
import com.etra.chargingpoints.domain.model.ChargingPointRequest

interface ChargingPointsRepository {

    /**
     * Auth
     */
    suspend fun login(data: String): Either<CustomError, AccessToken>

    /**
     * Charging Points
     */
    suspend fun chargingPoints(data: String, accessToken: String): Either<CustomError, List<ChargingPoint>>

}
