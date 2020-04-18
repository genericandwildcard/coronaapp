package com.genericandwildcard.coronafinder.app.feature.countrylist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genericandwildcard.coronafinder.app.core.Dialogs
import com.genericandwildcard.coronafinder.app.core.mutableLiveData
import com.genericandwildcard.coronafinder.app.coronadata.repo.CoronaRepo
import com.genericandwildcard.coronafinder.app.coronadata.storage.FavoritesRepo
import com.genericandwildcard.coronafinder.app.countriesapi.repo.CountryRepo
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListFragment.State
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListFragment.State.Loading
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListViewEntity
import com.genericandwildcard.coronafinder.app.feature.countrylist.Navigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(InternalCoroutinesApi::class)
class CountryListViewModel(
    private val coronaRepo: CoronaRepo,
    private val countryFlagUseCase: GetFlagUrlUseCase,
    private val favoritesRepo: FavoritesRepo,
    private val dialogs: Dialogs
) : ViewModel() {

    val viewState: MutableLiveData<State> = mutableLiveData(Loading)
    val isPercentage = ConflatedBroadcastChannel<Boolean>()

    init {
        viewModelScope.launch {
            isPercentage.send(false)
        }
        viewModelScope.launch(Dispatchers.IO) {
            coronaRepo.observeCountryStats()
                .combine(isPercentage.asFlow()) { countryStatsList, isPercentage -> countryStatsList to isPercentage }
                .combine(CountryRepo.observeAll()) { (countryStatsList, isPercentage), countryInfoList ->
                    Triple(
                        countryStatsList,
                        isPercentage,
                        countryInfoList
                    )
                }
                .combine(favoritesRepo.observe()) { (countryStatsList, isPercentage, countryInfoList), favorites ->
                    countryStatsList
                        .sortedByDescending { it.totalConfirmed - it.totalDeaths - it.totalRecovered }
                        .map {
                            val population =
                                countryInfoList.find { country -> country.countryCode == it.countryCode }?.population
                                    ?: 0
                            val confirmed = it.totalConfirmed - it.totalDeaths - it.totalRecovered
                            val newConfirmed = it.newConfirmed - it.newDeaths - it.newRecovered
                            CountryListViewEntity(
                                isFavorite = favorites.contains(it.countryCode),
                                countryCode = it.countryCode,
                                name = it.name,
                                flagUrl = countryFlagUseCase.execute(it.countryCode).fold(
                                    { "" },
                                    { url -> url }
                                ),
                                totalConfirmed = "${if (isPercentage && population != 0) String.format(
                                    "%,f",
                                    (confirmed.toFloat() / population) * 100f
                                ) else String.format("%,d", confirmed)} (${String.format(
                                    "%+,d",
                                    newConfirmed
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
                        if (countryList.isNotEmpty()) {
                            viewState.value = State.Success(countryList)
                        }
                    }
                }
        }
    }

    fun reload() {
        viewState.value = State.Loading
        coronaRepo.reload()
//        dialogs.showRetryBottomSheet("") {}
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

    fun onShowPercentageToggled(checked: Boolean) {
        viewModelScope.launch {
            isPercentage.send(checked)
        }
    }
}

