package com.genericandwildcard.coronafinder.app.feature.countrydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.genericandwildcard.coronafinder.app.R
import com.genericandwildcard.coronafinder.app.databinding.FragmentCountryDetailBinding
import com.genericandwildcard.coronafinder.app.databinding.MainFragmentBinding
import com.genericandwildcard.coronafinder.app.feature.countrylist.Navigator
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CountryDetailsFragment : Fragment() {
    companion object {
        fun newInstance(countryCode: String) = CountryDetailsFragment().apply {
            arguments = Bundle().apply { putString(Navigator.COUNTRY_CODE, countryCode) }
        }
    }

    @Inject
    lateinit var viewModel: CountryDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel.setCountryCode(arguments!!.getString(Navigator.COUNTRY_CODE)!!)
    }


    private var binding: FragmentCountryDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.countryInfo.observe(viewLifecycleOwner) { country ->
            binding?.run {
                countryName.text = country.name
                countryFlag.load(country.flagUrl)
            }
        }
    }
}
