package com.genericandwildcard.coronafinder.app.coronadata.entity

import org.threeten.bp.OffsetDateTime

data class CoronaCountryStats(
    var name: String,
    var countryCode: String,
    var date: OffsetDateTime,
    var newConfirmed: Int,
    var newDeaths: Int,
    var newRecovered: Int,
    var slug: String,
    var totalConfirmed: Int,
    var totalDeaths: Int,
    var totalRecovered: Int
)
