package com.genericandwildcard.coronafinder.app.countriesapi.injection

import com.genericandwildcard.coronafinder.app.countriesapi.api.CountriesApi
import com.genericandwildcard.coronafinder.app.countriesapi.repo.CountriesRepo
import com.genericandwildcard.coronafinder.app.countriesapi.repo.CountriesRepoImpl
import com.genericandwildcard.coronafinder.app.countriesapi.repo.CountryFlagUrlRepo
import com.genericandwildcard.coronafinder.app.countriesapi.usecase.GetFlagUrlUseCase
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object CountriesApiModule {

    @Provides
    @Singleton
    fun provideCountriesApi(): CountriesApi = Retrofit.Builder()
        .baseUrl(CountriesApi.baseUrl)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().build()
            )
        )
        .build()
        .create(CountriesApi::class.java)

    @Singleton
    @Provides
    fun provideGetFlagUrlUseCase(countryFlagUrlRepo: CountryFlagUrlRepo): GetFlagUrlUseCase =
        GetFlagUrlUseCase(countryFlagUrlRepo)

    @Singleton
    @Provides
    fun provideCountryFlagUrlRepo(): CountryFlagUrlRepo = CountryFlagUrlRepo()

    @Singleton
    @Provides
    fun provideCountriesRepo(countriesApi: CountriesApi): CountriesRepo =
        CountriesRepoImpl(countriesApi)
}
