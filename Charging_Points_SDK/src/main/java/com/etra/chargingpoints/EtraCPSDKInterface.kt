package com.etra.chargingpoints

import com.etra.chargingpoints.domain.model.ChargingPoint

interface EtraCPSDKInterface {

    suspend fun chargingPoints(
        longitude: Double,
        latitude: Double,
        altitude: Double? = null,
        radius: Int = 1000,
        offsetItems: Int = 1,
        maxItems: Int = 10, onSuccess: (List<ChargingPoint>) -> Unit, onError: (String) -> Unit
    )
}

