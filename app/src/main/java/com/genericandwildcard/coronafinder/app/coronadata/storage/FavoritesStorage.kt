package com.genericandwildcard.coronafinder.app.coronadata.storage

import arrow.core.extensions.list.applicative.map
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import io.objectbox.query.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class FavoritesRepo(
    private val boxStore: BoxStore
) {

    private val box: Box<CountryFavorite> = boxStore.boxFor()

    fun observe(): Flow<List<String>> = callbackFlow {
        val query: Query<CountryFavorite> = box.query().build()

        val subscription = query.subscribe()
            .observer { data -> sendBlocking(data.map { it.countryCode }) }
        /*
         * Suspends until either 'onCompleted'/'onApiError' from the callback is invoked
         * or flow collector is cancelled (e.g. by 'take(1)' or because a collector's coroutine was cancelled).
         * In both cases, callback will be properly unregistered.
         */
        awaitClose { subscription.cancel() }
    }

    fun String.getId() = this.toLong(36)

    fun save(countryCode: String) {
        box.put(CountryFavorite(countryCode.getId(), countryCode))
    }

    fun delete(countryCode: String) {
        box.remove(countryCode.getId())
    }
}
