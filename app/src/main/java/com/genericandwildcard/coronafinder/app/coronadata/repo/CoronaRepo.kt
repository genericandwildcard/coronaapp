package com.genericandwildcard.coronafinder.app.coronadata.repo

import android.util.Log
import arrow.core.extensions.list.applicative.map
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.handleError
import com.genericandwildcard.coronafinder.app.coronadata.api.CoronaApi
import com.genericandwildcard.coronafinder.app.coronadata.api.entity.CountryHistoryItem
import com.genericandwildcard.coronafinder.app.coronadata.api.entity.mapToCountryStats
import com.genericandwildcard.coronafinder.app.coronadata.entity.CoronaCountryStats
import com.genericandwildcard.coronafinder.app.coronadata.entity.CoronaCountryStatsList
import com.genericandwildcard.coronafinder.app.coronadata.storage.ObjectBoxCoronaCountryStatsStorage
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class CoronaRepo(
    private val api: CoronaApi,
    private val countryStatsStorage: ObjectBoxCoronaCountryStatsStorage,
    private val ioDispatcher: CoroutineContext
) {

    init {
        if (countryStatsStorage.isEmpty) {
            reload()
        }
    }

    fun reload() {
        IO.fx {
            continueOn(ioDispatcher)
            val summary = effect { api.getSummary() }.bind()
            val countries = summary.countries
            countryStatsStorage.replaceAll(countries.map { it.mapToCountryStats() })
        }.handleError {
            Log.e("CoronaError", it.localizedMessage)
            it.printStackTrace()
        }.unsafeRunAsync { }
    }

    suspend fun getCountryHistory(slug: String): List<CountryHistoryItem> {
        return api.getHistorical(slug)
    }

    fun observeCountryStats(): Flow<CoronaCountryStatsList> = countryStatsStorage.observe()

    fun observeCountryDetails(countryCode: String): Flow<CoronaCountryStats> =
        countryStatsStorage.observe(countryCode)

}
