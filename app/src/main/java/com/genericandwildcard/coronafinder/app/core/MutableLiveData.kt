package com.genericandwildcard.coronafinder.app.core

import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveData(initialValue: T) = MutableLiveData<T>()
    .apply { value = initialValue }
