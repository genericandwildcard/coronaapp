package com.genericandwildcard.coronafinder.app.feature.countrylist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genericandwildcard.coronafinder.app.core.mutableLiveData
import com.genericandwildcard.coronafinder.app.coronadata.entity.CoronaCountryStatsList
import com.genericandwildcard.coronafinder.app.coronadata.usecase.ObserveCoronaCountryStatsUseCase
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListFragment.State
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListFragment.State.Loading
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListViewEntity
import com.genericandwildcard.coronafinder.app.feature.countrylist.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(InternalCoroutinesApi::class)
class CountryListViewModelImpl(
    private val countryStatsUseCase: ObserveCoronaCountryStatsUseCase,
    private val countryFlagUseCase: GetFlagUrlUseCase
) : CountryListViewModel, ViewModel() {

    override val viewState: MutableLiveData<State> = mutableLiveData(Loading)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            countryStatsUseCase.observe().collect(object : FlowCollector<CoronaCountryStatsList> {
                override suspend fun emit(value: CoronaCountryStatsList) {
                    withContext(Dispatchers.Main) {
                        viewState.value =
                            State.Success(value.map {
                                CountryListViewEntity(
                                    countryCode = it.countryCode,
                                    name = it.name,
                                    flagUrl = countryFlagUseCase.execute(it.countryCode).fold(
                                        { "" },
                                        { url -> url }
                                    ),
                                    totalConfirmed = "${String.format(
                                        "%,d",
                                        it.totalConfirmed
                                    )} (${String.format("%+,d", it.newConfirmed)})",
                                    totalConfirmedRaw = it.totalConfirmed,
                                    totalDeaths = "${String.format(
                                        "%,d",
                                        it.totalDeaths
                                    )} (${String.format("%+,d", it.newDeaths)})",
                                    totalDeathsRaw = it.totalDeaths,
                                    totalRecovered = "${String.format(
                                        "%,d",
                                        it.totalRecovered
                                    )} (${String.format("%+,d", it.newRecovered)})",
                                    totalRecoveredRaw = it.totalRecovered
                                )
                            })
                    }
                }
            })
        }
    }

    override fun reload() {
        viewState.value = State.Loading
        countryStatsUseCase.reload()
    }

    override fun onItemClick(index: Int, item: CountryListViewEntity) {
        Navigator.navigateToCountryDetails(item.countryCode)
    }
}

