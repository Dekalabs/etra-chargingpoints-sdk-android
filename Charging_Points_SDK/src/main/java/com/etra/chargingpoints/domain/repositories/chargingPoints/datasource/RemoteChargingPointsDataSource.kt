package com.etra.chargingpoints.domain.repositories.chargingPoints.datasource

import com.etra.chargingpoints.data.ChargingPointsService
import com.etra.chargingpoints.data.requests.ChargingPointsRequest
import com.etra.chargingpoints.data.requests.LoginRequest
import com.etra.chargingpoints.domain.mapper.toDomain
import com.etra.chargingpoints.domain.model.AccessToken
import com.etra.chargingpoints.domain.model.ChargingPoint

class RemoteChargingPointsDataSource(
    private val service: ChargingPointsService,
) : ChargingPointsDataSource {

    /**
     * Auth
     */
    override suspend fun login(data: String): AccessToken {
        return service.login(
            LoginRequest(data = data)
        ).toDomain()
    }

    override suspend fun chargingPoints(data: String, accessToken: String): List<ChargingPoint> {
        val authorization = "Bearer $accessToken"
        val result = service.chargingPoints(body = ChargingPointsRequest(data = data), authorization = authorization)
        return result.chargingPoints.map {
            it.toDomain()
        }
    }
}
