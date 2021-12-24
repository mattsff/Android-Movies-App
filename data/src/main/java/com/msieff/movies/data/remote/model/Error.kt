package com.msieff.movies.data.remote.model

import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("status_code")
    val code: Int = 0,
    @SerializedName("status_message")
    val message: String? = null
)