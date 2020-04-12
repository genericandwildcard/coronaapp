package com.genericandwildcard.coronafinder.app.core.definitions

import arrow.core.Option

interface Cache<K, V> {
    suspend fun get(key: K): Option<V>
    suspend fun put(key: K, value: V)
}
