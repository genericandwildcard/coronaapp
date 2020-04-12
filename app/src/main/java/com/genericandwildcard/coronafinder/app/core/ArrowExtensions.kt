package com.genericandwildcard.coronafinder.app.core

import arrow.core.*

/**
 * Non suspending version of Either.catch(...)
 */
inline fun <R> Either.Companion.catchBlocking(f: () -> R): Either<Throwable, R> =
    catchBlocking(::identity, f)

/**
 * Non suspending version of Either.catch(...)
 */
inline fun <L, R> Either.Companion.catchBlocking(
    fe: (Throwable) -> L,
    f: () -> R
): Either<L, R> =
    try {
        f().right()
    } catch (t: Throwable) {
        fe(t.nonFatalOrThrow()).left()
    }
