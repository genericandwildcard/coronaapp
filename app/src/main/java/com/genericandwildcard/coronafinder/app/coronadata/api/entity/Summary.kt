package com.genericandwildcard.coronafinder.app.coronadata.api.entity


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import org.threeten.bp.OffsetDateTime

@Keep
@JsonClass(generateAdapter = true)
data class Summary(
    @Json(name = "Countries")
    val countries: List<Country>,
    @Json(name = "Date")
    val date: OffsetDateTime,
    @Json(name = "Global")
    val global: Global
)
