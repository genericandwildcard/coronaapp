package com.genericandwildcard.coronafinder.app.application.injection

import com.genericandwildcard.coronafinder.app.application.CoronaApp
import com.genericandwildcard.coronafinder.app.database.ObjectBoxModule
import com.genericandwildcard.coronafinder.app.feature.countrydetails.injection.CountryDetailsFeatureModule
import com.genericandwildcard.coronafinder.app.feature.countrylist.injection.CountryListFeatureModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ObjectBoxModule::class,
        CountryDetailsFeatureModule::class,
        CountryListFeatureModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent : AndroidInjector<CoronaApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: CoronaApp): AppComponent
    }
}
