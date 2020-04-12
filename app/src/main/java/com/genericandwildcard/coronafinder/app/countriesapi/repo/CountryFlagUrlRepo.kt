package com.genericandwildcard.coronafinder.app.countriesapi.repo

import com.genericandwildcard.coronafinder.app.countriesapi.api.CountryCodesMap
import java.util.*


class CountryFlagUrlRepo {

    private fun mapFlagUrl(iso3Code: String): String =
        "https://restcountries.eu/data/${iso3Code.toLowerCase(Locale.US)}.svg"

    fun getFlagUrl(iso2Code: String): String? =
        CountryCodesMap.iso2ToIso3[iso2Code]?.let { mapFlagUrl(it) }

}
