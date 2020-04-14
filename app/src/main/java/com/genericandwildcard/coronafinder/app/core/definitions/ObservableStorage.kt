package com.genericandwildcard.coronafinder.app.core.definitions

import kotlinx.coroutines.flow.Flow

interface ObservableStorage<T> {
    val isEmpty: Boolean
    fun observe(): Flow<T>
    fun observe(countryCode: String): Flow<T>
    fun put(value: T)
    fun replaceAll(value: T)
}
