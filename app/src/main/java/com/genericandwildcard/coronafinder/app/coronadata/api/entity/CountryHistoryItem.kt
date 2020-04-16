package com.genericandwildcard.coronafinder.app.coronadata.api.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import org.threeten.bp.OffsetDateTime

@Keep
@JsonClass(generateAdapter = true)
data class CountryHistoryItem(
    @Json(name = "Active")
    val active: Int?,
    @Json(name = "City")
    val city: String?,
    @Json(name = "CityCode")
    val cityCode: String?,
    @Json(name = "Confirmed")
    val confirmed: Int?,
    @Json(name = "Country")
    val country: String?,
    @Json(name = "CountryCode")
    val countryCode: String?,
    @Json(name = "Date")
    val date: OffsetDateTime?,
    @Json(name = "Deaths")
    val deaths: Int?,
    @Json(name = "Lat")
    val lat: String?,
    @Json(name = "Lon")
    val lon: String?,
    @Json(name = "Province")
    val province: String?,
    @Json(name = "Recovered")
    val recovered: Int?
)