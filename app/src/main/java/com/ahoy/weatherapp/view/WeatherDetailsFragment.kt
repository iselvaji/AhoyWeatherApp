package com.ahoy.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.comman.Constants
import com.ahoy.weatherapp.data.local.database.entity.FavCitiesEntity
import com.ahoy.weatherapp.databinding.FragmentDetailsBinding
import com.ahoy.weatherapp.view.adapter.WeatherDetailsAdapter
import com.ahoy.weatherapp.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDetailsFragment : BaseFragment() {

    @Inject
    lateinit var adapter: WeatherDetailsAdapter
    private var binding: FragmentDetailsBinding? = null
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setUpObservers()
    }

    private fun setupUI() {

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding?.recyclerviewDetails?.layoutManager = layoutManager
        binding?.recyclerviewDetails?.adapter = adapter

        if (arguments != null) {
            val cityName: String? = requireArguments().getString(Constants.KEY_ID)
            if (cityName != null) {
                viewModel.getWeatherDetails(cityName)
            }
        }
    }

    private fun setUpObservers() {
        viewModel.response.observe(viewLifecycleOwner, {
            Log.d("details response : ", it.toString())

            it?.let {
                val details: FavCitiesEntity = it[0]
                binding?.cityName?.text = getString(R.string.city) + "\n" + details.cityName
                binding?.country?.text = getString(R.string.country) + "\n" + details.country

                // Todo
                it[0].listItems?.let {
                    binding?.weatherInfo?.text = getString(R.string.date) + "\n" + it[0].dtTxt +
                            "\n\n" + getString(R.string.temprature) + "\n" + it[0].main.temp_max +
                            "\n\n" + getString(R.string.humidity) +  "\n" + it[0].main.humidity +
                            "\n\n" + getString(R.string.weather) +  "\n" + it[0].weather[0].description +
                            "\n\n" + getString(R.string.visibility) +  "\n" + it[0].visibility

                }
            }
        })
    }
}