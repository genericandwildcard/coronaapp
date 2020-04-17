package com.genericandwildcard.coronafinder.app.feature.countrylist

data class CountryListViewEntity(
    val countryCode: String,
    val isFavorite: Boolean,
    val name: String,
    val flagUrl: String,
    val totalConfirmed: String,
    val totalDeaths: String,
    val totalRecovered: String
)
