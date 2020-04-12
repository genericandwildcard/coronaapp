package com.genericandwildcard.coronafinder.app.countriesapi.repo

import arrow.core.Either
import arrow.core.Right
import com.genericandwildcard.coronafinder.app.core.definitions.Cache
import com.genericandwildcard.coronafinder.app.core.doOnRight
import com.genericandwildcard.coronafinder.app.countriesapi.api.CountriesApi
import com.genericandwildcard.coronafinder.app.countriesapi.entity.CountryInfo

class CountriesRepoImpl(
    private val api: CountriesApi,
    private val flagUrlCache: Cache<String, String> = VolatileCache()
) : CountriesRepo {

    override suspend fun byCode(countryCode: String): Either<Throwable, CountryInfo> =
        Either.catch { api.byCode(countryCode) }

    override suspend fun getCountryFlagUrl(countryCode: String): Either<Throwable, String> {
        return flagUrlCache.get(countryCode)
            .fold(
                {
                    loadCountryFlagUrl(countryCode)
                        .doOnRight { flagUrl -> flagUrlCache.put(countryCode, flagUrl) }
                },
                { Right(it) }
            )
    }

    private suspend fun loadCountryFlagUrl(countryCode: String): Either<Throwable, String> =
        byCode(countryCode).map { it.flag ?: "" } // TODO: Add error handling for this

}
