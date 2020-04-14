package com.genericandwildcard.coronafinder.app.feature.countrydetails.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genericandwildcard.coronafinder.app.core.injection.ViewModelKey
import com.genericandwildcard.coronafinder.app.coronadata.repo.CoronaRepo
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase
import com.genericandwildcard.coronafinder.app.feature.countrydetails.CountryDetailsFragment
import com.genericandwildcard.coronafinder.app.feature.countrydetails.CountryDetailsViewModel
import com.genericandwildcard.coronafinder.app.feature.countrydetails.injection.CountryDetailsFragmentModule.ProvideViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [ProvideViewModel::class])
abstract class CountryDetailsFragmentModule {

    /* Install module into subcomponent to have access to bound fragment instance */
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun bind(): CountryDetailsFragment

    @Module
    object InjectViewModel {
        @Provides
        fun provideFeatureViewModel(
            factory: ViewModelProvider.Factory,
            target: CountryDetailsFragment
        ): CountryDetailsViewModel = ViewModelProvider(target, factory).get(
            CountryDetailsViewModel::class.java
        )
    }

    @Module
    object ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(CountryDetailsViewModel::class)
        fun provideFeatureViewModel(
            countryRepo: CoronaRepo,
            countryFlagUseCase: GetFlagUrlUseCase
        ): ViewModel = CountryDetailsViewModel(
            countryRepo = countryRepo,
            countryFlagUseCase = countryFlagUseCase
        )
    }

}
