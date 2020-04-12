package com.genericandwildcard.coronafinder.app.feature.countrylist.injection

import com.genericandwildcard.coronafinder.app.core.injection.ActivityScope
import com.genericandwildcard.coronafinder.app.feature.countrylist.CountryListActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [MainActivityModule::class])
interface MainActivitySubComponent : AndroidInjector<CountryListActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<CountryListActivity>

}
