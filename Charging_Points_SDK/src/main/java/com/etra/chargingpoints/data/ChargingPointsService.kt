package com.etra.chargingpoints.data


import com.etra.chargingpoints.common.Constants
import com.etra.chargingpoints.data.requests.ChargingPointsRequest
import com.etra.chargingpoints.data.requests.LoginRequest
import com.etra.chargingpoints.data.responses.AccessTokenResponse
import com.etra.chargingpoints.data.responses.ChargingPointDataResponse
import com.etra.chargingpoints.data.responses.ChargingPointResponse
import retrofit2.http.*

interface ChargingPointsService {

    /**
     * Auth
     */
    @POST("vlciapi/login")
    suspend fun login(
        @Body body: LoginRequest
    ): AccessTokenResponse

    /**
     * Charging Points
     */
    @POST("vlciapi/chargingpoints")
    suspend fun chargingPoints(
        @Header(Constants.HEADER_AUTHORIZATION) authorization: String,
        @Body body: ChargingPointsRequest
    ): ChargingPointDataResponse

}
