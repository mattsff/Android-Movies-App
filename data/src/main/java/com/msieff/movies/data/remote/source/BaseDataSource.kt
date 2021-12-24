package com.msieff.movies.data.remote.source

import com.google.gson.Gson
import com.msieff.movies.data.remote.model.Error
import com.msieff.movies.data.remote.model.exception.ApiException
import com.msieff.movies.domain.model.common.State
import retrofit2.Response

/**
 * Base remote source class with error handling
 */
abstract class BaseDataSource constructor(
    private val gson: Gson
) {

    protected suspend fun <T> getResponse(
        request: suspend () -> Response<T>
    ): State<T> {
        return try {
            val response = request.invoke()
            if (response.isSuccessful) {
                State.Data(response.body())
            } else {
                val error = gson.fromJson(response.errorBody()?.string(), Error::class.java)
                State.Error(ApiException(error.message))
            }
        } catch (e: Exception) {
            State.Error(e)
        }
    }
}
