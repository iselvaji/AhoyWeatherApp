package com.ahoy.weatherapp.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.comman.Resource
import com.ahoy.weatherapp.data.LocationLiveData
import com.ahoy.weatherapp.data.local.database.entity.FavCitiesEntity
import com.ahoy.weatherapp.databinding.FragmentWeatherListBinding
import com.ahoy.weatherapp.databinding.LocationNameCardBinding
import com.ahoy.weatherapp.utils.*
import com.ahoy.weatherapp.utils.DataUtils.getDataForStorage
import com.ahoy.weatherapp.view.adapter.WeatherAdapter
import com.ahoy.weatherapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class WeatherListFragment : BaseFragment() {

    @Inject
    lateinit var adapter: WeatherAdapter

    private lateinit var textChangeCountDownJob: Job
    private var binding: FragmentWeatherListBinding? = null
    private val viewModel by viewModels<MainViewModel>()
    private val logTag = "WeatherListFragment"
    private lateinit var currentLocation : LocationLiveData.LocationModel
    private var locationNameCardBinding: LocationNameCardBinding? = null
    private var favCityEntity : FavCitiesEntity? = null
    private var tempratureUnit : String? = TempratureUnit.CELCIUS.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        locationNameCardBinding = binding?.locationCard
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setUpObservers()
    }

    private fun setupUI() {
        viewModel.apply {
            getSettingsFromPreference()
            getLocationDetails()
        }

        binding?.recyclerviewWeather?.adapter = adapter
        setupSearchView()

    }

    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    private fun setUpObservers() {

        viewModel.apply {
            forecastResponse.observe(viewLifecycleOwner, {
                // Log.d("forecast response : ", it.data.toString())
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        UiUtils.showProgressDialog(context, false)
                        it?.data?.let { response ->
                            response.let {
                                favCityEntity = getDataForStorage(response)
                            }
                            adapter.updateResults(response, tempratureUnit)
                        }
                    }
                    Resource.Status.ERROR -> {
                        UiUtils.showProgressDialog(context, false)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Resource.Status.LOADING ->
                        UiUtils.showProgressDialog(context, true)
                }
            })

            settingsPref.observe(viewLifecycleOwner, {
                Log.d("temprature Unit : ", it)
                tempratureUnit = it
            })
        }
    }

    private fun setupSearchView() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length > 2) {
                    if(::textChangeCountDownJob.isInitialized)
                        textChangeCountDownJob.cancel()

                    runBlocking {
                        textChangeCountDownJob = launch {
                            delay(500)
                            viewModel.getWeatherbyPlaceName(newText)
                        }
                    }
                }
                return true
            }
        })
    }

    private fun invokeLocationAction() {
        when {
            PermissionUtils.isLocationPermissionGranted(requireContext()) -> startLocationUpdate()
            PermissionUtils.shouldShowRequestPermissionRationale(requireActivity()) -> {
                requestLocationPermissions.launch(PermissionUtils.locationPermissions)
            }
            else -> activity?.let {
                Log.d(logTag, "requestLocationPermissions called..")
                requestLocationPermissions.launch(PermissionUtils.locationPermissions)
            }
        }
    }

    private val requestLocationPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                || permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                Log.d(logTag, "Location Permission granted")
                startLocationUpdate()
            } else {
                Log.d(logTag, "Location Permission not granted")
                displayLocationPermissionAlert(context)
            }
        }

    private fun startLocationUpdate() {
        if(PermissionUtils.isLocationEnabled(requireContext())) {
            Log.d(logTag, "startLocationUpdate begins..")
            viewModel.getLocationDetails().observe(this, {
                Log.d(logTag, "Location : " + it.latitude.toString() + " " + it.longitude.toString())
                currentLocation = LocationLiveData.LocationModel(it.longitude, it.latitude)

                val cityName = context?.let { it1 ->
                    LocationUtils.getCityFromLocation(it.latitude, it.longitude, it1)
                }

                if(cityName.isNullOrBlank()) {
                    binding?.locationCard?.cardViewLocation?.visibility = View.GONE
                    if(canMakeApiCall()) {
                        viewModel.getForecastWeatherList(it.latitude, it.longitude)
                    }
                } else {
                    if(canMakeApiCall()) {
                        viewModel.getWeatherbyPlaceName(cityName)
                    }

                    locationNameCardBinding?.apply {
                        cardViewLocation.visibility = View.VISIBLE
                        locationName.text = cityName

                        imageFav.setOnClickListener(View.OnClickListener {
                            context?.let {
                                UiUtils.showDialogWithOKCancelButton(it,
                                    getString(R.string.add_to_fav_message),
                                    positiveAction = {
                                        favCityEntity?.let {
                                            viewModel.saveFavCity(it)
                                        }
                                    }
                                )
                            }
                        })
                    }
                }
            })
        } else {
            Log.d(logTag, "LocationEnabled false..")
            displayLocationSettingsAlert(context)
        }
    }


    private fun displayLocationSettingsAlert(context: Context?) {

        context?.let {
            AlertDialog.Builder(context)
                .setMessage(R.string.error_closing_no_location_permission)
                .setPositiveButton(R.string.enable) { _, _ ->
                    Log.d(logTag, "GPS Enable selected")
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
                .setCancelable(false)
                .create()
                .show()
        }
    }

    private fun displayLocationPermissionAlert(context: Context?) {

        context?.let {
            AlertDialog.Builder(context)
                .setMessage(R.string.error_location_permission_denied)
                .setPositiveButton(R.string.enable) { _, _ ->
                    Log.d(logTag, "Location Permission Enable selected")
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    startActivity(intent)
                }
                .setCancelable(false)
                .create()
                .show()
        }
    }
}