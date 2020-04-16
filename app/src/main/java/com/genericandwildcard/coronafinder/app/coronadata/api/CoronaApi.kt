package com.genericandwildcard.coronafinder.app.coronadata.api

import com.genericandwildcard.coronafinder.app.coronadata.api.entity.CountryHistoryItem
import com.genericandwildcard.coronafinder.app.coronadata.api.entity.Summary
import retrofit2.http.GET
import retrofit2.http.Path

interface CoronaApi {
    companion object {
        const val baseUrl = "https://api.covid19api.com/"
    }

    @GET("summary")
    suspend fun getSummary(): Summary

    @GET("total/dayone/country/{slug}")
    suspend fun getHistorical(@Path("slug") slug: String): List<CountryHistoryItem>
}
