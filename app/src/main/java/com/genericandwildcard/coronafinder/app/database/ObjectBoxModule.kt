package com.genericandwildcard.coronafinder.app.database

import android.content.Context
import com.genericandwildcard.coronafinder.app.application.CoronaApp
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
object ObjectBoxModule {

    @Singleton
    @Provides
    fun provideBoxStore(context: Context): BoxStore {
        return CoronaApp.instance.boxStore
    }
}
