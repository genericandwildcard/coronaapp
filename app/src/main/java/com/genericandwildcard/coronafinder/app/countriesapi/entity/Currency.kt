package com.genericandwildcard.coronafinder.app.countriesapi.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class Currency(
    val code: String?,
    val name: String?,
    val symbol: String?
)
