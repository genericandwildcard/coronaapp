package com.genericandwildcard.coronafinder.app.countriesapi.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class Language(
    @Json(name = "iso639_1")
    val iso6391: String?,
    @Json(name = "iso639_2")
    val iso6392: String?,
    val name: String?,
    val nativeName: String?
)
