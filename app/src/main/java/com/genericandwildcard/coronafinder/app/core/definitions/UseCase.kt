package com.genericandwildcard.coronafinder.app.core.definitions

import arrow.core.Either
import com.genericandwildcard.coronafinder.app.coronadata.api.entity.Summary

interface UseCase<E, S> {
    suspend fun execute(): Either<Throwable, Summary>
}
