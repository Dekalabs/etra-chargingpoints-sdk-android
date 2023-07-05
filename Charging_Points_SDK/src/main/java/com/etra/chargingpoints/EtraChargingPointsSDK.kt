package com.etra.chargingpoints

import com.etra.chargingpoints.common.error.EtraError
import com.etra.chargingpoints.domain.model.AccessToken
import com.etra.chargingpoints.domain.model.ChargingPoint
import com.etra.chargingpoints.domain.model.ChargingPointRequest
import com.etra.chargingpoints.domain.usecases.auth.LoginUseCase
import com.etra.chargingpoints.domain.usecases.chargePoints.GetChargingPointsUseCase
import com.etra.chargingpoints.domain.utils.JWTBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class EtraChargingPointsSDK private constructor() : EtraCPSDKInterface, KoinComponent {

    private val loginUseCase: LoginUseCase = get()
    private val getChargingPointsUseCase: GetChargingPointsUseCase = get()
    private var accessToken: AccessToken? = null

    override suspend fun chargingPoints(
        longitude: Double,
        latitude: Double,
        altitude: Double?,
        radius: Int,
        offsetItems: Int,
        maxItems: Int,
        onSuccess: (List<ChargingPoint>) -> Unit, onError: (String) -> Unit
    ) {
        if (username == null || password == null || lowSecurityKey == null) {
            onError.invoke(EtraError.INVALID_CREDENTIALS.errorMessage)
            return
        }
        val chargingPointRequest = ChargingPointRequest(
            longitude = longitude,
            latitude = latitude,
            altitude = altitude,
            radius = radius,
            offsetItems = offsetItems,
            maxItems = maxItems
        )

        if (accessToken == null || (accessToken?.isExpired() == true)) {
            loginUseCase.invoke(username!!, password!!).fold(
                { error ->
                    onError.invoke(EtraError.MISSING_DATA.errorMessage)
                    return
                },
                { accessToken ->
                    this.accessToken = accessToken
                }
            )
        }

        getChargingPointsUseCase.invoke(
            chargingPointRequest = chargingPointRequest,
            accessToken = accessToken!!.token
        ).fold({
            onError.invoke(EtraError.RETRIEVE_INFO.errorMessage)
        }, {
            onSuccess.invoke(it)
        }
        )
    }

    companion object {
        private var username: String? = null
        private var password: String? = null
        private var lowSecurityKey: String? = null

        @Volatile
        private var instance: EtraChargingPointsSDK? = null

        fun initEtraSDK(username: String, password: String, lowSecurityKey: String) {
            this.username = username
            this.password = password
            this.lowSecurityKey = lowSecurityKey

            JWTBuilder.lowSecurityKey = lowSecurityKey
        }

        fun getInstance(): EtraChargingPointsSDK {
            return instance ?: synchronized(this) {
                instance ?: EtraChargingPointsSDK().also { instance = it }
            }
        }
    }
}