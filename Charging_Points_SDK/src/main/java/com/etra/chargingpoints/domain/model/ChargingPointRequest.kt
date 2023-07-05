package com.etra.chargingpoints.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChargingPointRequest(
    val longitude: Double,
    val latitude: Double,
    val altitude: Double?,
    val radius: Int,
    val offsetItems: Int,
    val maxItems: Int
) : Parcelable