package com.genericandwildcard.coronafinder.app.coronadata.usecase

import com.genericandwildcard.coronafinder.app.coronadata.entity.CoronaCountryStatsList
import com.genericandwildcard.coronafinder.app.coronadata.repo.CoronaRepo
import kotlinx.coroutines.flow.Flow

class ObserveCoronaCountryStatsUseCase(
    private val repo: CoronaRepo
) {
    fun observe(): Flow<CoronaCountryStatsList> = repo.observeCountryStats()
    fun reload() = repo.reload()
}
