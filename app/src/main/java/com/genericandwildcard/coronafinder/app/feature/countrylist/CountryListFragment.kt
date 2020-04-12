package com.genericandwildcard.coronafinder.app.feature.countrylist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.api.load
import com.genericandwildcard.coronafinder.app.R
import com.genericandwildcard.coronafinder.app.core.Dialogs
import com.genericandwildcard.coronafinder.app.core.Error
import com.genericandwildcard.coronafinder.app.databinding.MainFragmentBinding
import com.genericandwildcard.coronafinder.app.databinding.RowCountryBinding
import com.genericandwildcard.coronafinder.app.feature.countrylist.viewmodel.CountryListViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.viewbinding.BindableItem
import com.xwray.groupie.viewbinding.GroupieViewHolder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CountryListFragment : Fragment() {

    sealed class State {
        object Loading : State()
        data class Success(val countries: List<CountryListViewEntity>) : State()
        data class Failure(val cause: Error) : State()
    }

    companion object {
        fun newInstance() = CountryListFragment()
    }

    private var reloadItemId: Int = 0

    @Inject
    lateinit var viewModel: CountryListViewModel

    @Inject
    lateinit var dialogs: Dialogs

    private val countriesSection: Section = Section()
    private val countriesAdapter: GroupAdapter<GroupieViewHolder<RowCountryBinding>> =
        GroupAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val reload = menu.add("Reload")
        reloadItemId = reload.itemId
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == reloadItemId) {
            countriesSection.clear()
            viewModel.reload()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private var binding: MainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("ViewModelInstance", "ViewModel instance is ${viewModel.hashCode()}")
    }

    class CountryLayoutItem(
        private val viewEntity: CountryListViewEntity,
        private val onItemClick: (index: Int, entity: CountryListViewEntity) -> Unit
    ) : BindableItem<RowCountryBinding>(viewEntity.name.hashCode().toLong()) {

        override fun initializeViewBinding(view: View): RowCountryBinding {
            return RowCountryBinding.bind(view)
        }

        override fun getLayout(): Int = R.layout.row_country

        override fun bind(viewBinding: RowCountryBinding, position: Int) {
            viewBinding.countryName.text = viewEntity.name
            viewBinding.countryTotalConfirmed.text = viewEntity.totalConfirmed
            viewBinding.countryTotalDeaths.text = viewEntity.totalDeaths
            viewBinding.countryTotalRecovered.text = viewEntity.totalRecovered
            viewBinding.countryFlag.load(viewEntity.flagUrl)

            viewBinding.btnFavorite.setOnClickListener {
                val imageView = it as AppCompatImageView
                val isChecked = imageView.tag as? Boolean ?: false
                if (isChecked) {
                    imageView.setImageResource(R.drawable.ic_baseline_star_border_24)
                } else {
                    imageView.setImageResource(R.drawable.ic_baseline_star_24)
                }
                imageView.tag = !isChecked
            }
            viewBinding.countryTotalConfirmed.setOnClickListener {
                onItemClick.invoke(position, viewEntity)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            countriesRecycler.adapter = countriesAdapter
            countriesAdapter.add(countriesSection)
        }

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Loading -> showLoadingState(state)
                is State.Success -> showSuccess(state)
                is State.Failure -> showError(state)
            }
        }
    }

    private fun showLoadingState(state: State.Loading) {
        binding?.run {
            loadingSpinner.show()
            countriesRecycler.isVisible = false
        }
    }

    private fun showError(state: State.Failure) {
        binding?.run {
            loadingSpinner.hide()
            countriesRecycler.isVisible = false
        }
    }

    private fun showSuccess(state: State.Success) {
        binding?.run {
            loadingSpinner.hide()
            countriesRecycler.isVisible = true
            countriesSection.update(state.countries.map {
                CountryLayoutItem(
                    it,
                    viewModel::onItemTotalsClick
                )
            })
        }
    }
}

