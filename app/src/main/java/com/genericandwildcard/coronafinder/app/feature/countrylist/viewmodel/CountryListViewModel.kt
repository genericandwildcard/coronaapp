package com.genericandwildcard.coronafinder.app.feature.countrylist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genericandwildcard.coronafinder.app.core.mutableLiveData
import com.genericandwildcard.coronafinder.app.coronadata.repo.CoronaRepo
import com.genericandwildcard.coronafinder.app.coronadata.storage.FavoritesRepo
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListFragment.State
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListFragment.State.Loading
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListViewEntity
import com.genericandwildcard.coronafinder.app.feature.countrylist.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(InternalCoroutinesApi::class)
class CountryListViewModel(
    private val coronaRepo: CoronaRepo,
    private val countryFlagUseCase: GetFlagUrlUseCase,
    private val favoritesRepo: FavoritesRepo
) : ViewModel() {

    val viewState: MutableLiveData<State> = mutableLiveData(Loading)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            coronaRepo.observeCountryStats()
                .combine(favoritesRepo.observe()) { countryStatsList, favorites ->
                    countryStatsList
                        .sortedByDescending { it.totalConfirmed - it.totalDeaths - it.totalRecovered }
                        .map {
                            CountryListViewEntity(
                                isFavorite = favorites.contains(it.countryCode),
                                countryCode = it.countryCode,
                                name = it.name,
                                flagUrl = countryFlagUseCase.execute(it.countryCode).fold(
                                    { "" },
                                    { url -> url }
                                ),
                                totalConfirmed = "${String.format(
                                    "%,d",
                                    it.totalConfirmed - it.totalDeaths - it.totalRecovered
                                )} (${String.format(
                                    "%+,d",
                                    (it.newConfirmed - it.newDeaths - it.newRecovered)
                                )})",
                                totalDeaths = "${String.format(
                                    "%,d",
                                    it.totalDeaths
                                )} (${String.format("%+,d", it.newDeaths)})",
                                totalRecovered = "${String.format(
                                    "%,d",
                                    it.totalRecovered
                                )} (${String.format("%+,d", it.newRecovered)})",
                            )
                        }
                        .sortedByDescending { it.isFavorite }

                }
                .collect { countryList ->
                    withContext(Dispatchers.Main) {
                        viewState.value =
                            State.Success(countryList)
                    }
                }
        }
    }

    fun reload() {
        viewState.value = State.Loading
        coronaRepo.reload()
    }

    fun onItemClick(index: Int, item: CountryListViewEntity) {
        Navigator.navigateToCountryDetails(item.countryCode)
    }

    fun onFavoriteClick(
        country: CountryListViewEntity,
        checked: Boolean
    ) {
        if (!checked) favoritesRepo.save(country.countryCode)
        else favoritesRepo.delete(country.countryCode)
    }
}

