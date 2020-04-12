package com.genericandwildcard.coronafinder.app.feature.countrylist.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genericandwildcard.coronafinder.app.core.injection.ViewModelKey
import com.genericandwildcard.coronafinder.app.coronadata.usecase.ObserveCoronaCountryStatsUseCase
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListFragment
import com.genericandwildcard.coronafinder.app.feature.countrylist.injection.MainFragmentModule.ProvideViewModel
import com.genericandwildcard.coronafinder.app.feature.countrylist.viewmodel.CountryListViewModel
import com.genericandwildcard.coronafinder.app.feature.countrylist.viewmodel.CountryListViewModelImpl
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [ProvideViewModel::class])
abstract class MainFragmentModule {

    /* Install module into subcomponent to have access to bound fragment instance */
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun bind(): CountryListFragment

    @Module
    object InjectViewModel {
        @Provides
        fun provideFeatureViewModel(
            factory: ViewModelProvider.Factory,
            target: CountryListFragment
        ): CountryListViewModel = ViewModelProvider(target, factory).get(
            CountryListViewModelImpl::class.java
        )
    }

    @Module
    object ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(CountryListViewModelImpl::class)
        fun provideFeatureViewModel(
            countryStatsUseCase: ObserveCoronaCountryStatsUseCase,
            countryFlagUseCase: GetFlagUrlUseCase
        ): ViewModel =
            CountryListViewModelImpl(
                countryStatsUseCase = countryStatsUseCase,
                countryFlagUseCase = countryFlagUseCase
            )
    }

}
