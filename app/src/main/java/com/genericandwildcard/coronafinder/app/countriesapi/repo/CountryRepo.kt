package com.genericandwildcard.coronafinder.app.countriesapi.repo

import arrow.core.extensions.list.applicative.map
import com.genericandwildcard.coronafinder.app.application.CoronaApp
import com.genericandwildcard.coronafinder.app.coronadata.api.typeadapter.OffsetDateTimeAdapter
import com.genericandwildcard.coronafinder.app.coronadata.storage.getId
import com.genericandwildcard.coronafinder.app.countriesapi.api.CountryApi
import com.genericandwildcard.coronafinder.app.countriesapi.database.entity.ObjectBoxCountryInfo
import com.genericandwildcard.coronafinder.app.countriesapi.entity.CountryInfo
import com.squareup.moshi.Moshi
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CountryRepo {

    private val boxStore: BoxStore by lazy { CoronaApp.instance.boxStore }
    private val countryInfoBox: Box<ObjectBoxCountryInfo> by lazy { boxStore.boxFor() }

    init {
        if (countryInfoBox.isEmpty) {
            GlobalScope.launch(Dispatchers.IO) {
                downloadAll()
            }
        }
    }

    private val api: CountryApi by lazy {
        Retrofit.Builder()
            .baseUrl(CountryApi.baseUrl)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(OffsetDateTimeAdapter())
                        .build()
                )
            )
            .build()
            .create(CountryApi::class.java)
    }

    suspend fun all(): List<CountryInfo> = api.all()

    suspend fun byCode(countryCode: String): CountryInfo = api.byCode(countryCode)

    suspend fun downloadAll() {
        val countries = api.all()
        val dbCountries = countries
            .filter { it.alpha2Code != null && it.population != null }
            .map {
                ObjectBoxCountryInfo(
                    id = it.alpha2Code!!.getId(),
                    countryCode = it.alpha2Code,
                    population = it.population!!
                )
            }
        countryInfoBox.put(dbCountries)
    }

    suspend fun observeAll(): Flow<List<ObjectBoxCountryInfo>> = callbackFlow {
        val subscription = countryInfoBox.query().build().subscribe()
            .observer { data -> sendBlocking(data) }
        /*
         * Suspends until either 'onCompleted'/'onApiError' from the callback is invoked
         * or flow collector is cancelled (e.g. by 'take(1)' or because a collector's coroutine was cancelled).
         * In both cases, callback will be properly unregistered.
         */
        awaitClose { subscription.cancel() }
    }
}