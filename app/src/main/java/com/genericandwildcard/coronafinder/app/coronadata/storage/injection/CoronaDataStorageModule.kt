package com.genericandwildcard.coronafinder.app.coronadata.storage.injection

import com.genericandwildcard.coronafinder.app.core.definitions.ObservableStorage
import com.genericandwildcard.coronafinder.app.coronadata.entity.CoronaCountryStatsList
import com.genericandwildcard.coronafinder.app.coronadata.storage.ObjectBoxCoronaCountryStatsStorage
import com.genericandwildcard.coronafinder.app.coronadata.storage.entity.ObjectBoxCoronaCountryStats
import dagger.Module
import dagger.Provides
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

@Module
object CoronaDataStorageModule {

    @Provides
    fun provideCountryStatsBox(boxStore: BoxStore): Box<ObjectBoxCoronaCountryStats> =
        boxStore.boxFor()

    @Provides
    fun provideObjectBoxCoronaCountryStatsStorage(boxStore: BoxStore): ObservableStorage<CoronaCountryStatsList> =
        ObjectBoxCoronaCountryStatsStorage(boxStore = boxStore)
}
