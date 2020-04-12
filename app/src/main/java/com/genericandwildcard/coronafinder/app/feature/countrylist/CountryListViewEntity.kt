package com.genericandwildcard.coronafinder.app.feature.countrylist

data class CountryListViewEntity(
    val name: String,
    val flagUrl: String,
    val totalConfirmed: String,
    val totalDeaths: String,
    val totalRecovered: String,
    val totalConfirmedRaw: Int,
    val totalDeathsRaw: Int,
    val totalRecoveredRaw: Int
)
