package com.jaax_sensus.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfileUpdateDto(
    @param:Json(name = "Name") val name: String,
    @param:Json(name = "Country") val country: String,
    @param:Json(name = "State") val state: String,
    @param:Json(name = "City") val city: String
)
