package com.etra.chargingpoints.data.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChargingPointDataResponse(
    @SerialName("chargingPoints") val chargingPoints: List<ChargingPointResponse>
)

@Serializable
data class ChargingPointResponse(
    @SerialName("id") val id: String,
    @SerialName("location") val location: LocationResponse,
    @SerialName("connectors") val connectors: List<ChargingPointConnectorResponse>
)

@Serializable
data class LocationResponse(
    @SerialName("type") val type: String,
    @SerialName("coordinates") val coordinates: List<Double>,
)

@Serializable
data class ChargingPointConnectorResponse(
    @SerialName("id") val id: Int,
    @SerialName("status") val status: String,
    @SerialName("power") val power: Int,
    @SerialName("type") val type: String,
    @SerialName("format") val format: String,
    @SerialName("speed") val speed: String
)
