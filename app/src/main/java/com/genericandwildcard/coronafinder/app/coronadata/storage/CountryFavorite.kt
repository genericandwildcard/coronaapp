package com.genericandwildcard.coronafinder.app.coronadata.storage

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Index

@Entity
data class CountryFavorite(
    @Id(assignable = true)
    var id: Long = 0L,

    @Index
    var countryCode: String
)