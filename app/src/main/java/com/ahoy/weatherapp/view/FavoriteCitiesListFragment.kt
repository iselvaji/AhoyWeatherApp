package com.ahoy.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.comman.Constants.Companion.KEY_ID
import com.ahoy.weatherapp.comman.Resource
import com.ahoy.weatherapp.databinding.FragmentFavCitiesListBinding
import com.ahoy.weatherapp.view.adapter.FavoriteLocationsAdapter
import com.ahoy.weatherapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteCitiesListFragment : BaseFragment() {

    @Inject
    lateinit var adapter: FavoriteLocationsAdapter

    private var binding: FragmentFavCitiesListBinding? = null
    private val viewModel by viewModels<FavoriteViewModel>()
    private val logTag = "FavoriteCitiesFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavCitiesListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setUpObservers()
    }

    private fun setupUI() {
        viewModel.getFavCities()

        binding?.recyclerviewFavorite?.adapter = adapter

        adapter.onItemClick = { selectedItem, _ ->
            Log.d(logTag, "Fav city click : " + selectedItem.cityName)
            val bundle = Bundle()
            bundle.putString(KEY_ID, selectedItem.cityName)
            findNavController(this).navigate(R.id.list_to_details_view, bundle)
        }
    }

    private fun setUpObservers() {

        viewModel.apply {
            response.observe(viewLifecycleOwner, {
                Log.d("favorite list : ", it.size.toString())
                it?.let { list ->
                    adapter.updateResults(list)
                }
            })
        }
    }

}