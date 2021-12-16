package com.ahoy.weatherapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ahoy.weatherapp.R
import com.ahoy.weatherapp.data.local.preference.DataStoreManager
import com.ahoy.weatherapp.databinding.FragmentSettingsBinding
import com.ahoy.weatherapp.utils.TempratureUnit
import com.ahoy.weatherapp.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment(), RadioGroup.OnCheckedChangeListener{

    @Inject
    lateinit var dataStorePreferences: DataStoreManager

    private var binding: FragmentSettingsBinding? = null
    private val viewModel by viewModels<SettingsViewModel>()
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setUpObservers()
    }

    private fun setupUI() {

        dataStoreManager = context?.let { DataStoreManager(it) }!!

        viewModel.getSettingsFromPreference()

        binding?.radioGroup?.setOnCheckedChangeListener(this)
    }

    private fun setUpObservers() {
        viewModel.settingsPref.observe(viewLifecycleOwner, {
            Toast.makeText(context, getString(R.string.temprature_unit_settings_change), Toast.LENGTH_SHORT).show()
            Log.d("Temprature unit : ", it)
            if(it == TempratureUnit.FAHRENHEIT.name) {
                binding?.radioGroup?.check(R.id.radioBtnFarenheit)
            } else {
                binding?.radioGroup?.check(R.id.radioBtnCelsius)
            }
        })
    }

    override fun onCheckedChanged(group: RadioGroup?, id: Int) {
        if(id == R.id.radioBtnCelsius) {
            viewModel.saveSettingsPreference(TempratureUnit.CELCIUS)
        } else {
            viewModel.saveSettingsPreference(TempratureUnit.FAHRENHEIT)
        }
    }
}