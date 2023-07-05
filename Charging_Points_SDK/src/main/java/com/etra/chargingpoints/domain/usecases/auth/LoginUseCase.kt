package com.etra.chargingpoints.domain.usecases.auth

import arrow.core.Either
import com.etra.chargingpoints.domain.repositories.chargingPoints.ChargingPointsRepository
import com.etra.chargingpoints.domain.utils.JWTBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginUseCase(
    private val chargingPointsRepository: ChargingPointsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(username: String, password: String) = withContext(dispatcher) {
        return@withContext chargingPointsRepository.login(data = JWTBuilder.loginJWT(username = username, password = password)).fold({
            Either.Left(it)
        }, {
            Either.Right(it)
        })
    }
}