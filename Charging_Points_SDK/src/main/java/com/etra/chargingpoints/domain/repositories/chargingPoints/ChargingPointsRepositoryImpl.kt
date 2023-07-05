package com.etra.chargingpoints.domain.repositories.chargingPoints

import arrow.core.Either
import com.etra.chargingpoints.common.error.CustomError
import com.etra.chargingpoints.common.error.toError
import com.etra.chargingpoints.domain.model.AccessToken
import com.etra.chargingpoints.domain.model.ChargingPoint
import com.etra.chargingpoints.domain.repositories.chargingPoints.datasource.ChargingPointsDataSource

class ChargingPointsRepositoryImpl(
    private val dataSource: ChargingPointsDataSource
) : ChargingPointsRepository {

    /**
     * Auth
     */
    override suspend fun login(data: String): Either<CustomError, AccessToken> {
        return Either.catch {
            dataSource.login(
                data = data
            )
        }
            .mapLeft { it.toError() }
    }

    /**
     * Charging Points
     */
    override suspend fun chargingPoints(data: String, accessToken: String): Either<CustomError, List<ChargingPoint>> {
        return Either.catch { dataSource.chargingPoints(data = data, accessToken = accessToken) }
            .mapLeft { it.toError() }
    }
}

