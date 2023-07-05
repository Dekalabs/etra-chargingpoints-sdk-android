package com.etra.chargingpoints.domain.usecases.chargePoints

import arrow.core.Either
import com.etra.chargingpoints.domain.model.ChargingPointRequest
import com.etra.chargingpoints.domain.repositories.chargingPoints.ChargingPointsRepository
import com.etra.chargingpoints.domain.utils.JWTBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetChargingPointsUseCase(
    private val chargingPointsRepository: ChargingPointsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(
        chargingPointRequest: ChargingPointRequest,
        accessToken: String
    ) = withContext(dispatcher) {

        return@withContext chargingPointsRepository.chargingPoints(
            data = JWTBuilder.chargingPointsJWT(
                longitude = chargingPointRequest.longitude,
                latitude = chargingPointRequest.latitude,
                altitude = chargingPointRequest.altitude,
                radius = chargingPointRequest.radius,
                offsetItems = chargingPointRequest.offsetItems,
                maxItems = chargingPointRequest.maxItems
            ),
            accessToken = accessToken
        ).fold({
            Either.Left(it)
        }, {
            Either.Right(it)
        })
    }
}