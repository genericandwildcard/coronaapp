package com.genericandwildcard.coronafinder.app.coronadata.api.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class Global(
    @Json(name = "NewConfirmed")
    val newConfirmed: Int,
    @Json(name = "NewDeaths")
    val newDeaths: Int,
    @Json(name = "NewRecovered")
    val newRecovered: Int,
    @Json(name = "TotalConfirmed")
    val totalConfirmed: Int,
    @Json(name = "TotalDeaths")
    val totalDeaths: Int,
    @Json(name = "TotalRecovered")
    val totalRecovered: Int
)
