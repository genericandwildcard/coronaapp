package com.genericandwildcard.coronafinder.app.feature.countrydetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.genericandwildcard.coronafinder.app.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CountryDetailsFragment : Fragment(R.layout.fragment_country_detail) {
    companion object {
        fun newInstance() = CountryDetailsFragment()
    }

    @Inject
    lateinit var viewModel: CountryDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }
}
