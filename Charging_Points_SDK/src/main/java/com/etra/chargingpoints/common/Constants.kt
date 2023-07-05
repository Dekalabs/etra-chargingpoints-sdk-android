package com.etra.chargingpoints.common


object Constants {
    const val API_TIMEOUT = 30L
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
    const val HEADER_RETRY_COUNT = "xInternalRetryCount"
    const val CONTENT_TYPE = "application/json"

    const val USER_TOKEN = "userToken"

    private const val BASE_URL = "www.valencia.es"
    const val API_URL = "https://${BASE_URL}/apps/recarregapp/"

}