package com.genericandwildcard.coronafinder.app.countriesapi.api

import com.genericandwildcard.coronafinder.app.countriesapi.entity.CountryInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApi {
    companion object {
        const val baseUrl = "https://restcountries.eu/rest/v2/"
    }

    @GET("alpha/{countryCode}")
    suspend fun byCode(@Path("countryCode") countryCode: String): CountryInfo
}
