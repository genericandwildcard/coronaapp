package com.genericandwildcard.coronafinder.app.coronadata.api.injection

import com.genericandwildcard.coronafinder.app.coronadata.api.CoronaApi
import com.genericandwildcard.coronafinder.app.coronadata.api.typeadapter.OffsetDateTimeAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object CoronaDataApiModule {

    @Provides
    @Singleton
    fun provideCoronaApi(): CoronaApi = Retrofit.Builder()
        .baseUrl(CoronaApi.baseUrl)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(OffsetDateTimeAdapter())
                    .build()
            )
        )
        .build()
        .create(CoronaApi::class.java)
}
