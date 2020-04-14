package com.genericandwildcard.coronafinder.app.feature.countrydetails.injection

import com.genericandwildcard.coronafinder.app.core.injection.ActivityKey
import com.genericandwildcard.coronafinder.app.core.injection.ActivityScope
import com.genericandwildcard.coronafinder.app.feature.countrydetails.CountryDetailsActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [CountryDetailsActivityModule::class])
interface CountryDetailsActivitySubComponent : AndroidInjector<CountryDetailsActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<CountryDetailsActivity>

}
