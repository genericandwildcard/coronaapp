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
        MainFragmentModule::class,
        CoronaDataFeatureModule::class,
        CountriesApiModule::class
    ],
    subcomponents = [MainActivitySubComponent::class]
)
abstract class MainFeatureModule {

    @Binds
    @IntoMap
    @ClassKey(CountryListActivity::class)
    abstract fun bindMainActivityAndroidInjector(
        factory: MainActivitySubComponent.Factory
    ): AndroidInjector.Factory<*>
}
