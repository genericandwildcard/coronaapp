package com.genericandwildcard.coronafinder.app.countriesapi.database.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class ObjectBoxCountryInfo(
    @Id(assignable = true) var id: Long = 0,
    var countryCode: String = "",
    var population: Int = 0
)