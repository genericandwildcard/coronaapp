package com.genericandwildcard.coronafinder.app.coronadata.api.entity


import androidx.annotation.Keep
import com.genericandwildcard.coronafinder.app.coronadata.entity.CoronaCountryStats
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.OffsetDateTime

@Keep
@JsonClass(generateAdapter = true)
data class Country(
    @Json(name = "Country")
    val name: String,
    @Json(name = "CountryCode")
    val countryCode: String,
    @Json(name = "Date")
    val date: OffsetDateTime,
    @Json(name = "NewConfirmed")
    val newConfirmed: Int,
    @Json(name = "NewDeaths")
    val newDeaths: Int,
    @Json(name = "NewRecovered")
    val newRecovered: Int,
    @Json(name = "Slug")
    val slug: String,
    @Json(name = "TotalConfirmed")
    val totalConfirmed: Int,
    @Json(name = "TotalDeaths")
    val totalDeaths: Int,
    @Json(name = "TotalRecovered")
    val totalRecovered: Int
)

fun Country.mapToCountryStats(): CoronaCountryStats =
    CoronaCountryStats(
        name = name,
        countryCode = countryCode,
        date = date,
        newConfirmed = newConfirmed,
        newDeaths = newDeaths,
        newRecovered = newRecovered,
        slug = slug,
        totalConfirmed = totalConfirmed,
        totalDeaths = totalDeaths,
        totalRecovered = totalRecovered
    )
