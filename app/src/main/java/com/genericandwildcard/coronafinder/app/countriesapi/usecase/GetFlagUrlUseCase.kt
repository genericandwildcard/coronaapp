package com.genericandwildcard.coronafinder.app.countriesapi.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.rightIfNotNull
import com.genericandwildcard.coronafinder.app.countriesapi.repo.CountryFlagUrlRepo
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase.Error.Iso2CountryCodeFormatError
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase.Error.Iso2CountryCodeNotRecognized

class GetFlagUrlUseCase(
    private val flagUrlRepo: CountryFlagUrlRepo
) {

    sealed class Error {
        data class Iso2CountryCodeFormatError(val code: String) : Error()
        data class Iso2CountryCodeNotRecognized(val code: String) : Error()
    }

    fun execute(countryIso2: String): Either<Error, String> {
        if (countryIso2.length != 2) return Iso2CountryCodeFormatError(countryIso2).left()
        return flagUrlRepo.getFlagUrl(countryIso2)
            .rightIfNotNull { Iso2CountryCodeNotRecognized(countryIso2) }
    }
}
