package com.genericandwildcard.coronafinder.app.feature.countrydetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.genericandwildcard.coronafinder.app.R
import com.genericandwildcard.coronafinder.app.feature.countrylist.Navigator
import dagger.android.AndroidInjection

class CountryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_details_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    CountryDetailsFragment.newInstance(
                        intent.extras!!.getString(
                            Navigator.COUNTRY_CODE,
                            ""
                        )
                    )
                )
                .commitNow()
        }
    }
}
