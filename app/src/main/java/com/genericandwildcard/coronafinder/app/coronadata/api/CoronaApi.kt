package com.genericandwildcard.coronafinder.app.coronadata.api

import com.genericandwildcard.coronafinder.app.coronadata.api.entity.Summary
import retrofit2.http.GET

interface CoronaApi {
    companion object {
        const val baseUrl = "https://api.covid19api.com/"
    }

    @GET("summary")
    suspend fun getSummary(): Summary
}
