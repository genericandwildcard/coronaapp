package com.genericandwildcard.coronafinder.app.application.injection

import android.content.Context
import com.genericandwildcard.coronafinder.app.core.definitions.ActivityProvider
import com.genericandwildcard.coronafinder.app.core.definitions.FragmentManagerProvider
import com.genericandwildcard.coronafinder.app.application.CoronaApp
import com.genericandwildcard.coronafinder.app.core.Dialogs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideFragmentManagerProvider(application: CoronaApp): FragmentManagerProvider =
        application

    @Provides
    fun providesContext(application: CoronaApp): Context = application

    @Provides
    fun providesActivityProvider(application: CoronaApp): ActivityProvider = application

    @Singleton
    @Provides
    fun provideDialogsModule(activityProvider: ActivityProvider): Dialogs =
        Dialogs(activityProvider)
}
