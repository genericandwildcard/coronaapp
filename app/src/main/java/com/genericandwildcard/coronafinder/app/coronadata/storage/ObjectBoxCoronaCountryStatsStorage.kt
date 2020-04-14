package com.genericandwildcard.coronafinder.app.coronadata.storage

import arrow.core.extensions.list.applicative.map
import com.genericandwildcard.coronafinder.app.core.definitions.ObservableStorage
import com.genericandwildcard.coronafinder.app.coronadata.entity.CoronaCountryStats
import com.genericandwildcard.coronafinder.app.coronadata.entity.CoronaCountryStatsList
import com.genericandwildcard.coronafinder.app.coronadata.storage.entity.ObjectBoxCoronaCountryStats
import com.genericandwildcard.coronafinder.app.coronadata.storage.entity.ObjectBoxCoronaCountryStats_
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.threeten.bp.OffsetDateTime

class ObjectBoxCoronaCountryStatsStorage(
    private val boxStore: BoxStore
) {

    private val box: Box<ObjectBoxCoronaCountryStats> = boxStore.boxFor()
    private val query: Query<ObjectBoxCoronaCountryStats> = box.query().build()

    val isEmpty: Boolean get() = query.count() == 0L

    fun observe(): Flow<CoronaCountryStatsList> = callbackFlow {
        val subscription = query.subscribe()
            .observer { data -> sendBlocking(data.map { it.toDomainModel() }) }
        /*
         * Suspends until either 'onCompleted'/'onApiError' from the callback is invoked
         * or flow collector is cancelled (e.g. by 'take(1)' or because a collector's coroutine was cancelled).
         * In both cases, callback will be properly unregistered.
         */
        awaitClose { subscription.cancel() }
    }

    fun replaceAll(value: CoronaCountryStatsList) {
        box.removeAll()
        box.put(value.map { it.toDatabaseModel() })
    }

    fun put(value: CoronaCountryStatsList) {
        box.put(value.map { it.toDatabaseModel() })
    }

    private fun CoronaCountryStats.toDatabaseModel(): ObjectBoxCoronaCountryStats {
        return ObjectBoxCoronaCountryStats(
            id = countryCode.toLong(36), // countryCode is unique and short, so we can create a stable id from it
            name = name,
            countryCode = countryCode,
            date = date,
            newConfirmed = newConfirmed,
            newDeaths = newDeaths,
            newRecovered = newRecovered,
            slug = slug,
            totalConfirmed = totalConfirmed,
            totalDeaths = totalDeaths,
            totalRecovered = totalRecovered
        )
    }

    private fun ObjectBoxCoronaCountryStats.toDomainModel(): CoronaCountryStats {
        return CoronaCountryStats(
            name = name ?: "",
            countryCode = countryCode ?: "",
            date = date ?: OffsetDateTime.MIN,
            newConfirmed = newConfirmed ?: 0,
            newDeaths = newDeaths ?: 0,
            newRecovered = newRecovered ?: 0,
            slug = slug ?: "",
            totalConfirmed = totalConfirmed ?: 0,
            totalDeaths = totalDeaths ?: 0,
            totalRecovered = totalRecovered ?: 0
        )
    }

    fun observe(countryCode: String): Flow<CoronaCountryStats> = callbackFlow {
        val query: Query<ObjectBoxCoronaCountryStats> =
            box.query().equal(ObjectBoxCoronaCountryStats_.countryCode, countryCode).build()

        val subscription = query.subscribe()
            .observer { data: List<ObjectBoxCoronaCountryStats> ->
                sendBlocking(
                    data.first().toDomainModel()
                )
            }
        /*
         * Suspends until either 'onCompleted'/'onApiError' from the callback is invoked
         * or flow collector is cancelled (e.g. by 'take(1)' or because a collector's coroutine was cancelled).
         * In both cases, callback will be properly unregistered.
         */
        awaitClose { subscription.cancel() }
    }
}
