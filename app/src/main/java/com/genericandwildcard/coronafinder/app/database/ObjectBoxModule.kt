package com.genericandwildcard.coronafinder.app.database

import android.content.Context
import com.genericandwildcard.coronafinder.app.coronadata.storage.entity.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
object ObjectBoxModule {

    @Singleton
    @Provides
    fun provideBoxStore(context: Context): BoxStore {
        return MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}
