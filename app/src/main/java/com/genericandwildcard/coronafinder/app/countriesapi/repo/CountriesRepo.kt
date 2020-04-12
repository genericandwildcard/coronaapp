package com.genericandwildcard.coronafinder.app.countriesapi.repo

import arrow.core.Either
import com.genericandwildcard.coronafinder.app.countriesapi.entity.CountryInfo

interface CountriesRepo {
    suspend fun byCode(countryCode: String): Either<Throwable, CountryInfo>
    suspend fun getCountryFlagUrl(countryCode: String): Either<Throwable, String>
}
