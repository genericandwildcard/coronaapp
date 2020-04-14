package com.genericandwildcard.coronafinder.app.application.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genericandwildcard.coronafinder.app.feature.countrydetails.injection.CountryDetailsFragmentModule
import com.genericandwildcard.coronafinder.app.feature.countrylist.injection.CountryListFeatureModule
import com.genericandwildcard.coronafinder.app.feature.countrylist.injection.CountryListFragmentModule
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module(
    includes = [
        CountryListFragmentModule.ProvideViewModel::class,
        CountryDetailsFragmentModule.ProvideViewModel::class
    ]
)
object ViewModelFactoryModule {

    /* Singleton factory that searches generated map for specific provider and uses it to get a ViewModel instance */
    @Provides
    @Singleton
    @Suppress("UNCHECKED_CAST")
    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return requireNotNull(providers[modelClass as Class<out ViewModel>]).get() as T
        }
    }
}
