package com.etra.chargingpoints.common.error

import android.content.Context
import androidx.annotation.StringRes
import com.etra.chargingpoints.R
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class CustomError(
    val type: Type = Type.UNKNOWN,
    val throwable: Throwable = Throwable(),
    val errorMessage: ErrorMessage = ErrorMessage(),
    val jsonError: String? = null
) : Error(throwable) {
    companion object {
        fun getHttpError(exception: HttpException, response: Response<*>?): CustomError {
            try {
                var message: String? = null
                val jsonError = response?.errorBody()?.string()
                val jsonObject = JSONObject(jsonError!!)

                if (jsonObject.keys().hasNext()) {
                    val errorKey = jsonObject.keys().next()

                    message = try {
                        val jsonArray = jsonObject.getJSONArray(errorKey)
                        jsonArray[0].toString()
                    } catch (e: Exception) {
                        if (jsonObject.has("error_description")) {
                            jsonObject.getString("error_description")
                        } else {
                            jsonObject.getString(errorKey)
                        }
                    }
                }

                val errorMessage =
                    ErrorMessage(
                        messageString = message ?: response.errorBody()?.string()
                    )

                return CustomError(
                    type = Type.HTTP,
                    throwable = exception,
                    errorMessage = errorMessage,
                    jsonError = jsonError
                )
            } catch (e: Exception) {
                return CustomError(
                    type = Type.HTTP,
                    throwable = exception
                )
            }
        }
    }

    enum class Type {
        UNKNOWN,

        NO_INTERNET,
        TIMEOUT,
        HTTP,

        NO_ACCESS_TOKEN;
    }

    fun getMessageError(context: Context): String? {
        return when {
            errorMessage.messageId != -1 -> {
                context.getString(errorMessage.messageId)
            }

            errorMessage.messageString != null -> {
                errorMessage.messageString
            }

            else -> {
                localizedMessage
            }
        }
    }

    data class ErrorMessage(var messageString: String? = null, @StringRes var messageId: Int = -1)
}

fun Throwable.toError(): CustomError {

    return when (this) {
        is HttpException -> CustomError.getHttpError(this, response())

        is UnknownHostException -> CustomError(
            type = CustomError.Type.NO_INTERNET,
            throwable = this,
            errorMessage = CustomError.ErrorMessage(messageId = R.string.ERROR_NO_INTERNET)
        )

        is TimeoutException -> CustomError(
            type = CustomError.Type.TIMEOUT,
            throwable = this,
            errorMessage = CustomError.ErrorMessage(messageId = R.string.ERROR_TIMEOUT)
        )

        else -> CustomError(
            type = CustomError.Type.UNKNOWN,
            throwable = this,
            errorMessage = CustomError.ErrorMessage(messageId = R.string.ERROR_UNKNOWN)
        )
    }
}

enum class EtraError(val errorMessage: String) {
    INVALID_CREDENTIALS("Error en las credenciales"),
    MISSING_DATA("Error en la incialización de la librería. Faltan datos esenciales"),
    RETRIEVE_INFO("Error al recuperar los puntos de carga");
}