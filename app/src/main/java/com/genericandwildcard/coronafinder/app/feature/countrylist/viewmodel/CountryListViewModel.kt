package com.genericandwildcard.coronafinder.app.feature.countrylist.viewmodel

import androidx.lifecycle.LiveData
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListViewEntity
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListFragment

interface CountryListViewModel {
    fun reload()

    val viewState: LiveData<CountryListFragment.State>

    fun onItemTotalsClick(index: Int, item: CountryListViewEntity)
}
