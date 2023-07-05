@file:UseSerializers(ZonedDateTimeSerializer::class)

package com.etra.chargingpoints.data.responses

import com.etra.chargingpoints.common.serializers.ZonedDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.ZonedDateTime

@Serializable
data class AccessTokenResponse(
    @SerialName("token") val token: String,
    @SerialName("expiration") val expiration: ZonedDateTime
)
