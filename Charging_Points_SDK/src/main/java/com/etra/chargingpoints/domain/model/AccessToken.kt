package com.etra.chargingpoints.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class AccessToken(
    val token: String,
    val expiration: ZonedDateTime
) : Parcelable {

    fun isExpired(): Boolean  {
        return ZonedDateTime.now() >= expiration
    }
}