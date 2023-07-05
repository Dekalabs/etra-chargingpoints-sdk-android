package com.etra.chargingpoints.data.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChargingPointsRequest(
    @SerialName("data") val data: String,
)
