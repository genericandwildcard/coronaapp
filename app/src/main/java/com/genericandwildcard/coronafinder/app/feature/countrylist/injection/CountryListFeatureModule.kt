package com.genericandwildcard.coronafinder.app.feature.countrylist.injection

import com.genericandwildcard.coronafinder.app.coronadata.injection.CoronaDataFeatureModule
import com.genericandwildcard.coronafinder.app.countriesapi.injection.CountriesApiModule
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    includes = [
        CountryListFragmentModule::class,
        CoronaDataFeatureModule::class,
        CountriesApiModule::class
    ],
    subcomponents = [CountryListActivitySubComponent::class]
)
abstract class CountryListFeatureModule {

    @Binds
    @IntoMap
    @ClassKey(CountryListActivity::class)
    abstract fun bindCountryListActivityAndroidInjector(
        factory: CountryListActivitySubComponent.Factory
    ): AndroidInjector.Factory<*>
}
