package com.genericandwildcard.coronafinder.app.coronadata.injection

import com.genericandwildcard.coronafinder.app.coronadata.api.CoronaApi
import com.genericandwildcard.coronafinder.app.coronadata.api.injection.CoronaDataApiModule
import com.genericandwildcard.coronafinder.app.coronadata.repo.CoronaRepo
import com.genericandwildcard.coronafinder.app.coronadata.storage.FavoritesRepo
import com.genericandwildcard.coronafinder.app.coronadata.storage.ObjectBoxCoronaCountryStatsStorage
import com.genericandwildcard.coronafinder.app.coronadata.storage.injection.CoronaDataStorageModule
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module(includes = [CoronaDataApiModule::class, CoronaDataStorageModule::class])
object CoronaDataFeatureModule {

    @Singleton
    @Provides
    fun provideCoronaRepo(
        coronaApi: CoronaApi,
        countryStatsStorage: ObjectBoxCoronaCountryStatsStorage
    ): CoronaRepo =
        CoronaRepo(coronaApi, countryStatsStorage, Dispatchers.IO)

    @Singleton
    @Provides
    fun provideFavoritesRepo(
        boxStore: BoxStore
    ): FavoritesRepo =
        FavoritesRepo(boxStore = boxStore)
}
