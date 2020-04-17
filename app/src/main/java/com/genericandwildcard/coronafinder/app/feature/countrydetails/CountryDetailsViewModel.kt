package com.genericandwildcard.coronafinder.app.feature.countrydetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genericandwildcard.coronafinder.app.R
import com.genericandwildcard.coronafinder.app.application.CoronaApp.Companion.instance
import com.genericandwildcard.coronafinder.app.coronadata.repo.CoronaRepo
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter

@OptIn(InternalCoroutinesApi::class)
class CountryDetailsViewModel(
    private val countryRepo: CoronaRepo,
    private val countryFlagUseCase: GetFlagUrlUseCase
) : ViewModel() {

    private lateinit var countryCode: String
    val countryInfo = MutableLiveData<CountryDetailsViewEntity>()
    val countryHistory = MutableLiveData<CountryHistoryViewEntity>()

    fun setCountryCode(cc: String) {
        countryCode = cc
        viewModelScope.launch {

            countryRepo.observeCountryDetails(countryCode).collect {
                countryInfo.value = CountryDetailsViewEntity(
                    countryFlagUseCase.execute(countryCode).toOption().orNull() ?: "",
                    it.name
                )
                viewModelScope.launch {
                    val countryHistoryData = countryRepo.getCountryHistory(it.slug)
                    val barEntries = countryHistoryData.mapIndexed { i, item ->
                        BarEntry(
                            i.toFloat(),
                            floatArrayOf(
                                item.deaths!!.toFloat(),
                                item.recovered!!.toFloat(),
                                (item.confirmed!! - item.deaths - item.recovered).toFloat()
                            )
                        )
                    }
                    val dates = countryHistoryData.map { item ->
                        item.date!!.format(DateTimeFormatter.ofPattern("dd.MM"))
                    }
                    val barDataSet = BarDataSet(barEntries, null).apply {
                        setColors(
                            instance.getColor(R.color.colorDeaths),
                            instance.getColor(R.color.colorRecovered),
                            instance.getColor(R.color.colorConfirmed)
                        )
                        stackLabels = arrayOf(
                            "Deaths",
                            "Recovered",
                            "Confirmed"
                        )
                    }
                    val barData = BarData(barDataSet).apply {
                    }
                    countryHistory.value = CountryHistoryViewEntity(dates, barData)
                }
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


data class CountryHistoryViewEntity(
    val xAxisDates: List<String>,
    val barData: BarData
)