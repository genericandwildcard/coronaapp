package com.genericandwildcard.coronafinder.app.feature.countrydetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.genericandwildcard.coronafinder.app.databinding.FragmentCountryDetailBinding
import com.genericandwildcard.coronafinder.app.feature.countrylist.Navigator
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.StackedValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class CountryDetailsFragment : Fragment() {
    companion object {
        fun newInstance(countryCode: String) = CountryDetailsFragment().apply {
            arguments = Bundle().apply { putString(Navigator.COUNTRY_CODE, countryCode) }
        }
    }

    private val ZOOM_X = "ZOOMX"
    private val ZOOM_Y = "ZOOMY"
    private val TRANS_X = "TRANSX"

    @Inject
    lateinit var viewModel: CountryDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        if (savedInstanceState == null) {
            viewModel.setCountryCode(arguments!!.getString(Navigator.COUNTRY_CODE)!!)
        }
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

        val zoomX = savedInstanceState?.getFloat(ZOOM_X) ?: 6F
        val zoomY = savedInstanceState?.getFloat(ZOOM_Y) ?: 1F
        val transX = savedInstanceState?.getFloat(TRANS_X) ?: Float.MAX_VALUE

        binding?.run {
            chart1.setNoDataText("Loading...")
            chart1.axisLeft.axisMinimum = 0f
            chart1.isDoubleTapToZoomEnabled = false
            chart1.isLogEnabled = true
            chart1.description.isEnabled = false
            chart1.axisRight.isEnabled = false
            chart1.xAxis.granularity = 1F
            chart1.xAxis.textSize = 15F
            chart1.extraTopOffset = 5F
            chart1.axisLeft.textSize = 15F
            chart1.legend.textSize = 15F
            chart1.axisLeft.valueFormatter = LargeValueFormatter()
        }

        viewModel.countryInfo.observe(viewLifecycleOwner) { country ->
            binding?.run {
                countryName.text = country.name
                countryFlag.load(country.flagUrl)
            }
        }

        viewModel.countryHistory.observe(viewLifecycleOwner) { history ->
            binding?.run {
                history.barData.setValueFormatter(StackedValueFormatter(true, "", 0))
                history.barData.setDrawValues(false)
                history.barData.setValueTextSize(15F)
                chart1.data = history.barData
                chart1.zoom(zoomX, zoomY, 0f, 0F)
                chart1.moveViewToX(transX)

                chart1.xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return history.xAxisDates[value.toInt()]
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding?.run {
            outState.putFloat(TRANS_X, chart1.lowestVisibleX)
            chart1.viewPortHandler.let {
                outState.putFloat(ZOOM_X, it.scaleX)
                outState.putFloat(ZOOM_Y, it.scaleY)
                Log.i("BarScale", "ScaleX ${it.scaleX}")
                Log.i("BarScale", "ScaleY ${it.scaleY}")
                Log.i("BarScale", "TransX ${it.transX}, viewPortRight: ${it.contentRight()}")
                Log.i("BarScale", "TransY ${it.transY}")
                it.contentRight()
            }
        }
    }
}
