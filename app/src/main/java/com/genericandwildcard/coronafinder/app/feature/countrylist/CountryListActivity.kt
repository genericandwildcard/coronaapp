package com.genericandwildcard.coronafinder.app.feature.countrylist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.genericandwildcard.coronafinder.app.R
import dagger.android.AndroidInjection

class CountryListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CountryListFragment.newInstance())
                .commitNow()
        }
    }
}
