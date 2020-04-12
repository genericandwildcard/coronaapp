package com.genericandwildcard.coronafinder.app.coronadata.repo

import com.genericandwildcard.coronafinder.app.coronadata.entity.CoronaCountryStatsList
import kotlinx.coroutines.flow.Flow

interface CoronaRepo {
    fun reload()

    fun observeCountryStats(): Flow<CoronaCountryStatsList>
}
