package com.genericandwildcard.coronafinder.app.feature.countrydetails

import androidx.fragment.app.Fragment
import com.genericandwildcard.coronafinder.app.R
import javax.inject.Inject

class CountryDetailsFragment : Fragment(R.layout.fragment_country_detail) {

    companion object {
        fun newInstance() = CountryDetailsFragment()
    }

    @Inject
    lateinit var viewModel: CountryDetailsViewModel
}
