package com.genericandwildcard.coronafinder.app.coronadata.storage.converter

import io.objectbox.converter.PropertyConverter
import org.threeten.bp.OffsetDateTime

class OffsetDateTimeConverter : PropertyConverter<OffsetDateTime?, String?> {

    override fun convertToDatabaseValue(entityProperty: OffsetDateTime?): String? {
        return entityProperty?.toString()
    }

    override fun convertToEntityProperty(databaseValue: String?): OffsetDateTime? {
        return databaseValue?.let { value ->
            OffsetDateTime.parse(value)
        }
    }
}
