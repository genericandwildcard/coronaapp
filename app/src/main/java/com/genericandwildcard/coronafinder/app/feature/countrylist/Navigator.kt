package com.genericandwildcard.coronafinder.app.feature.countrylist

import android.content.Intent
import com.genericandwildcard.coronafinder.app.application.CoronaApp
import com.genericandwildcard.coronafinder.app.feature.countrydetails.CountryDetailsActivity

object Navigator {
    val COUNTRY_CODE = "COUNTRY_CODE"

    fun navigateToCountryDetails(countryCode: String) {
        val intent = Intent(CoronaApp.instance, CountryDetailsActivity::class.java)
            .apply { putExtra(COUNTRY_CODE, countryCode) }

        CoronaApp.instance.activity?.startActivity(intent)
    }

}