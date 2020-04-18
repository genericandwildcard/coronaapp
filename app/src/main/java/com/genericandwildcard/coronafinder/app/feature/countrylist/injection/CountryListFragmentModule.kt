package com.genericandwildcard.coronafinder.app.feature.countrylist.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genericandwildcard.coronafinder.app.core.Dialogs
import com.genericandwildcard.coronafinder.app.core.injection.ViewModelKey
import com.genericandwildcard.coronafinder.app.coronadata.repo.CoronaRepo
import com.genericandwildcard.coronafinder.app.coronadata.storage.FavoritesRepo
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListFragment
import com.genericandwildcard.coronafinder.app.feature.countrydetails.injection.CountryDetailsFragmentModule.ProvideViewModel
import com.genericandwildcard.coronafinder.app.feature.countrylist.viewmodel.CountryListViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(includes = [ProvideViewModel::class])
abstract class CountryListFragmentModule {

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
            CountryListViewModel::class.java
        )
    }

    @Module
    object ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(CountryListViewModel::class)
        fun provideFeatureViewModel(
            coronaRepo: CoronaRepo,
            countryFlagUseCase: GetFlagUrlUseCase,
            favoritesRepo: FavoritesRepo,
            dialogs: Dialogs
        ): ViewModel =
            CountryListViewModel(
                coronaRepo = coronaRepo,
                countryFlagUseCase = countryFlagUseCase,
                favoritesRepo = favoritesRepo,
                dialogs = dialogs
            )
    }

}
