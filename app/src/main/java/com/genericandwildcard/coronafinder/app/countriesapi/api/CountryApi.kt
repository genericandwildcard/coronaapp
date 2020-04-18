package com.genericandwildcard.coronafinder.app.countriesapi.api

import com.genericandwildcard.coronafinder.app.countriesapi.entity.CountryInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {
    companion object {
        const val baseUrl = "https://restcountries.eu/rest/v2/"
    }

    @GET("all")
    suspend fun all(): List<CountryInfo>

    @GET("alpha/{countryCode}")
    suspend fun byCode(@Path("countryCode") countryCode: String): CountryInfo
}
