package com.genericandwildcard.coronafinder.app.coronadata.storage.entity

import com.genericandwildcard.coronafinder.app.coronadata.storage.converter.OffsetDateTimeConverter
import io.objectbox.annotation.*
import org.threeten.bp.OffsetDateTime

@Entity
data class ObjectBoxCoronaCountryStats(
    @Id(assignable = true) var id: Long = 0,
    @NameInDb("name")
    var name: String? = null,
    @Index
    @NameInDb("countryCode")
    var countryCode: String? = null,
    @NameInDb("date")
    @Convert(converter = OffsetDateTimeConverter::class, dbType = String::class)
    var date: OffsetDateTime? = null,
    @NameInDb("newConfirmed")
    var newConfirmed: Int? = null,
    @NameInDb("newDeaths")
    var newDeaths: Int? = null,
    @NameInDb("newRecovered")
    var newRecovered: Int? = null,
    @NameInDb("slug")
    var slug: String? = null,
    @NameInDb("totalConfirmed")
    var totalConfirmed: Int? = null,
    @NameInDb("totalDeaths")
    var totalDeaths: Int? = null,
    @NameInDb("totalRecovered")
    var totalRecovered: Int? = null
)
