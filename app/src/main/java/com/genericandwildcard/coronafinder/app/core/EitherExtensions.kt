package com.genericandwildcard.coronafinder.app.core

import arrow.core.Either

inline fun <A, B> Either<A, B>.doOnRight(function: (B) -> Unit): Either<A, B> {
    fold({}, function)
    return this
}
