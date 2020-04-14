package com.genericandwildcard.coronafinder.app.feature.countrydetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genericandwildcard.coronafinder.app.coronadata.repo.CoronaRepo
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

@OptIn(InternalCoroutinesApi::class)
class CountryDetailsViewModel(
    private val countryRepo: CoronaRepo,
    private val countryFlagUseCase: GetFlagUrlUseCase
) : ViewModel() {

    private lateinit var countryCode: String
    val countryInfo = MutableLiveData<CountryDetailsViewEntity>()

    fun setCountryCode(cc: String) {
        countryCode = cc
        viewModelScope.launch {


//            countryRepo.observe().collect(object :
//                FlowCollector<CoronaCountryStatsList> {
//                override suspend fun emit(value: CoronaCountryStatsList) {
//
            countryRepo.observeCountryDetails(countryCode).collect {

                countryInfo.value = CountryDetailsViewEntity(
                    countryFlagUseCase.execute(countryCode).toOption().orNull() ?: "",
                    it.name
                )
            }
        }
    }

}

@InternalCoroutinesApi
suspend inline fun <T> Flow<T>.collect(crossinline collector: (T) -> Unit) = collect(
    object : FlowCollector<T> {
        override suspend fun emit(value: T) {
            collector(value)
        }
    })


data class CountryDetailsViewEntity(
    val flagUrl: String,
    val name: String
)