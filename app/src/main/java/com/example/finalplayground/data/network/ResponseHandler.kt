package com.example.finalplayground.data.network

import com.example.finalplayground.PlaygroundApplication
import com.example.finalplayground.R
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * A utility class responsible for handling response received from the repository layer
 */
class ResponseHandler {

    companion object {
        /**
         * Handles the successful response from the repository layer
         * @param data the data received
         * @return The [Resource] object with success state and data
         */
        fun <T> handleSuccess(data: T) = Resource.success(data)

        /**
         * Handles the exception received from the repository layer
         * @param e the exception received
         * @return The [Resource] object with error state and data
         */
        fun <T : Any?> handleException(e: Exception): Resource<T> {
            return when (e) {
                is HttpException -> Resource.error(getErrorMessage(e.code()), null)
                is SocketTimeoutException -> Resource.error(getString(R.string.timeout), null)
                is ConnectException, is UnknownHostException -> Resource.error(getString(R.string.connection_error), null)
                else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
            }
        }

        private fun getErrorMessage(code: Int): String {
            return when (code) {
                ErrorCodes.UN_AUTH.code -> getString(R.string.unauthorized)
                ErrorCodes.NOT_FOUND.code -> getString(R.string.not_found)
                ErrorCodes.INTERNAL_ERR.code -> getString(R.string.internal_err)
                ErrorCodes.SVC_UN_AVAIL.code -> getString(R.string.svc_un_avail)
                else -> getString(R.string.response_error)
            }
        }

        private fun getString(id: Int): String = PlaygroundApplication.get()?.getString(id) ?: ""
    }

    enum class ErrorCodes(val code: Int) {
        UN_AUTH(401),
        NOT_FOUND(404),
        INTERNAL_ERR(500),
        SVC_UN_AVAIL(503)
    }
}

/**
 * A Class that encapsulates a successful outcome with a value of type [T]
 * or a failure with a message.
 *
 * @param status The status of the request [Status.SUCCESS], [Status.ERROR] or [Status.LOADING]
 * @param data successfully fetched value of type [T]
 * @param message error message to be shown to the user.
 */
class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        /**
         * Returns [Resource] with status [Status.SUCCESS] and data of type [T]
         * @param data the response of type [T]
         */
        fun <T> success(data: T) = Resource(Status.SUCCESS, data, null)

        /**
         * Returns [Resource] with status [Status.ERROR], data of type [T] and message
         * @param msg the error message
         * @param data the response of type [T]
         */
        fun <T> error(msg: String, data: T?) = Resource(Status.ERROR, data, msg)

        /**
         * Returns [Resource] with status [Status.LOADING]
         * @param data returns data of type [T] if applicable.
         */
        fun <T> loading(data: T? = null) = Resource(Status.LOADING, data, null)
    }
}
