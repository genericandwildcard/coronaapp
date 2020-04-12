package com.genericandwildcard.coronafinder.app.coronadata.repo

import android.util.Log
import arrow.core.extensions.list.applicative.map
import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.handleError
import com.genericandwildcard.coronafinder.app.core.definitions.ObservableStorage
import com.genericandwildcard.coronafinder.app.coronadata.api.CoronaApi
import com.genericandwildcard.coronafinder.app.coronadata.api.entity.mapToCountryStats
import com.genericandwildcard.coronafinder.app.coronadata.entity.CoronaCountryStatsList
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class CoronaRepoImpl(
    private val api: CoronaApi,
    private val countryStatsStorage: ObservableStorage<CoronaCountryStatsList>,
    private val ioDispatcher: CoroutineContext
) : CoronaRepo {

    init {
        if (countryStatsStorage.isEmpty) {
            reload()
        }
    }

//    private fun <F> Async<F>.fetchCountries(): Kind<F, CoronaCountryStatsList> =
//        fx.async {
//            val summary = api.getSummary()
//            val countries = summary.countries.map { it.mapToCountryStats() }
//            countries
//        }

    override fun reload() {
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

    override fun observeCountryStats(): Flow<CoronaCountryStatsList> = countryStatsStorage.observe()

}
