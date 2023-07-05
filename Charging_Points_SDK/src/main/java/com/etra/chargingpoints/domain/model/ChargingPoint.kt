package com.etra.chargingpoints.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChargingPoint(
    val id: String,
    val location: ChargingPointLocation,
    val connectors: List<ChargingPointConnector>
) : Parcelable

@Parcelize
data class ChargingPointLocation(
    val type: String,
    val coordinates: List<Double>
) : Parcelable


@Parcelize
data class ChargingPointConnector(
    val id: Int,
    val status: String,
    val power: Int,
    val type: String,
    val format: String,
    val speed: String,
) : Parcelable
