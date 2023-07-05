package com.etra.chargingpoints.domain.mapper

import com.etra.chargingpoints.data.responses.AccessTokenResponse
import com.etra.chargingpoints.data.responses.ChargingPointConnectorResponse
import com.etra.chargingpoints.data.responses.ChargingPointResponse
import com.etra.chargingpoints.data.responses.LocationResponse
import com.etra.chargingpoints.domain.model.AccessToken
import com.etra.chargingpoints.domain.model.ChargingPoint
import com.etra.chargingpoints.domain.model.ChargingPointConnector
import com.etra.chargingpoints.domain.model.ChargingPointLocation

fun ChargingPointResponse.toDomain() = ChargingPoint(
    id = id,
    location = location.toDomain(),
    connectors = connectors.map { it.toDomain() }
)

fun LocationResponse.toDomain() = ChargingPointLocation(
    type = type,
    coordinates = coordinates
)

fun AccessTokenResponse.toDomain() = AccessToken(
    token = token,
    expiration = expiration
)

fun ChargingPointConnectorResponse.toDomain() = ChargingPointConnector(
    id = id,
    status = status,
    power = power,
    type = type,
    format = format,
    speed = speed
)