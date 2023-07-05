package com.etra.chargingpoints.domain.repositories.chargingPoints.datasource

import com.etra.chargingpoints.domain.model.AccessToken
import com.etra.chargingpoints.domain.model.ChargingPoint

interface ChargingPointsDataSource {


    /**
     * Auth
     */
    suspend fun login(data: String): AccessToken

    /**
     * Charging Points
     */
    suspend fun chargingPoints(data: String, accessToken: String): List<ChargingPoint>

}
