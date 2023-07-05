package com.etra.chargingpoints.domain.utils

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.json.JSONArray
import org.json.JSONObject
import javax.crypto.SecretKey

internal object JWTBuilder {
    private var key: SecretKey? = null
    internal var lowSecurityKey: String? = null
        set(value) {
            field = value
            lowSecurityKey?.let {
                key = Keys.hmacShaKeyFor(it.toByteArray())
            }
        }

    internal fun loginJWT(username: String, password: String): String {
        val payload = JSONObject().put("username", username).put("password", password).toString()
        val jwt = Jwts.builder()
            .setPayload(payload)
            .signWith(key)
            .compact()
        return jwt.toString()
    }

    internal fun chargingPointsJWT(
        longitude: Double, latitude: Double, altitude: Double? = null, radius: Int, offsetItems: Int = 1, maxItems: Int = 10
    ): String {
        val coordinates = arrayListOf(longitude, latitude)
        if (altitude != null)  {
            coordinates.add(altitude)
        }

        val locationObject = JSONObject().put("type", "Point").put("coordinates", JSONArray(coordinates))
        val payload =
            JSONObject().put("location", locationObject).put("radius", radius).put("offsetItems", offsetItems).put("maxItems", maxItems).toString()

        val jwt = Jwts.builder()
            .setPayload(payload)
            .signWith(key)
            .compact()
        return jwt.toString()
    }
}