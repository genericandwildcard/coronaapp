package com.genericandwildcard.coronafinder.app.feature.countrydetails.injection

import com.genericandwildcard.coronafinder.app.coronadata.injection.CoronaDataFeatureModule
import com.genericandwildcard.coronafinder.app.countriesapi.injection.CountriesApiModule
import com.genericandwildcard.coronafinder.app.feature.countrydetails.CountryDetailsActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(
    includes = [
        CountryDetailsFragmentModule::class,
        CoronaDataFeatureModule::class,
        CountriesApiModule::class
    ],
    subcomponents = [CountryDetailsActivitySubComponent::class]
)
abstract class CountryDetailsFeatureModule {

    @Binds
    @IntoMap
    @ClassKey(CountryDetailsActivity::class)
    abstract fun bindCountryDetailsActivityAndroidInjector(
        factory: CountryDetailsActivitySubComponent.Factory
    ): AndroidInjector.Factory<*>
}
