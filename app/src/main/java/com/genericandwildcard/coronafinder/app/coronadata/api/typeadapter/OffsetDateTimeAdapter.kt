package com.genericandwildcard.coronafinder.app.coronadata.api.typeadapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.OffsetDateTime


class OffsetDateTimeAdapter {
    @ToJson
    fun toJson(input: OffsetDateTime): String = input.toString()

    @FromJson
    fun fromJson(input: String): OffsetDateTime = OffsetDateTime.parse(input)
}
