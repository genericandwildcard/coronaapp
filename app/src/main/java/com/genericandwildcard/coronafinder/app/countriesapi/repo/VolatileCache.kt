package com.genericandwildcard.coronafinder.app.countriesapi.repo

import arrow.core.Option
import com.genericandwildcard.coronafinder.app.core.definitions.Cache

class VolatileCache<K, V> : Cache<K, V> {
    private val cache: MutableMap<K, V> = mutableMapOf()

    override suspend fun get(key: K): Option<V> =
        Option.fromNullable(cache[key])

    override suspend fun put(key: K, value: V) {
        cache[key] = value
    }
}
